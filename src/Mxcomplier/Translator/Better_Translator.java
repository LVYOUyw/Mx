package Mxcomplier.Translator;

import Mxcomplier.Ast.Type.Function;
import Mxcomplier.IR.Block;
import Mxcomplier.IR.Graph;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.*;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;

public class Better_Translator extends Translator {
    Graph G;
    public RegisterAllocate allocate;
    public HashMap<Integer, Integer> pow2;

    public Better_Translator(PrintStream output) {
        super(output);
        pow2 = new HashMap<>();
        int t = 2;
        for (int i = 1; i < 31; i++) {
            pow2.put(t, i);
            t = t * 2;
        }
    }

    public PhysicalRegister getregister(VirtualRegister s, PhysicalRegister t) {
        if (s.type != 2) return t;
        PhysicalRegister p = allocate.allocate.get(s);
        if (p != null) return p;
        return t;
    }

    public PhysicalRegister loadsrc(PhysicalRegister dest, Operand src, int... opt) {
        if (src instanceof Immediate) {
            int value = ((Immediate) src).value;
            if (value == 0)
                output.printf("\tmv\t\t%s, x0\n", dest.name);
            else
                output.printf("\tli\t\t%s, %s\n", dest.name, String.valueOf(value));
        }
        else if (src instanceof StringRegister) {
            StringRegister sreg = (StringRegister)src;
            output.printf("\tla\t\t%s, %s\n", dest.name, sreg.address());
        }
        else if (src instanceof VirtualRegister) {
            int type = ((VirtualRegister) src).type;
            if (type == 1) {
                String s = "%";
                output.printf("\tlui\t\t%s, %shi(%s)\n", dest.name, s, ((VirtualRegister) src).symbol.name);
                output.printf("\tlw\t\t%s, %slo(%s)(%s)\n", dest.name, s, ((VirtualRegister) src).symbol.name, dest.name);
            }
            else {
                int offset = G.frame.getOffset(src);
                if (opt.length != 0) offset += opt[0];
                PhysicalRegister register = allocate.allocate.get(src);
                if (type == 2 && register != null) return register;
                if (offset < 2048)
                    output.printf("\tlw\t\t%s, %s\n", dest.name, String.format("%d(sp)", offset));
                else {
                    output.printf("\tli\t\t%s, %d\n", dest.name, offset);
                    output.printf("\tadd\t\t%s, %s, sp\n", dest.name, dest.name);
                    output.printf("\tlw\t\t%s, 0(%s)\n", dest.name, dest.name);
                }
            }
        }
        return dest;
    }

    public PhysicalRegister loaddest(PhysicalRegister dest, VirtualRegister src) {
        if (src.type == 2) {
            PhysicalRegister register = allocate.allocate.get(src);
            if (register != null)
                return register;
        }
        return dest;
    }

    public void store(VirtualRegister dest, PhysicalRegister src) {
        int type = dest.type;
        String t0 = src.name.equals("t0") ? "t1" : "t0";
        if (type == 1) {
            String s = "%";
            output.printf("\tlui\t\t%s, %shi(%s)\n", t0, s, dest.symbol.name);
            output.printf("\tsw\t\t%s, %slo(%s)(%s)\n", src.name, s, dest.symbol.name, t0);
        }
        else {
            if (type == 2) {
                PhysicalRegister register = allocate.allocate.get(dest);
                if (register != null && register != src) {
                    output.printf("\tmv\t\t%s, %s\n", register.name, src.name);
                    return;
                }
                if (register != null)
                    return;
            }
            int offset = G.frame.getOffset(dest);
            if (offset < 2048)
                output.printf("\tsw\t\t%s, %s\n", src.name, String.format("%d(sp)", offset));
            else {
                output.printf("\tli\t\t%s, %d\n", t0, offset);
                output.printf("\tadd\t\t%s, %s, sp\n", t0, t0);
                output.printf("\tsw\t\t%s, 0(%s)\n", src.name, t0);
            }
        }
    }

    void callersave(Function function) {
        output.printf("\taddi\t\tsp, sp, %d\n", -32 * 4);
       /* if (true) {
            for (PhysicalRegister pr : PhysicalRegister.caller)
                output.printf("\tsw\t\t%s, %d(sp)\n", pr.name, pr.id * 4);
        }
        else {*/
            for (PhysicalRegister pr : G.function.allocate.usedcaller)
                output.printf("\tsw\t\t%s, %d(sp)\n", pr.name, pr.id * 4);
        //}
    }

    void callerresume(Function function) {
        /*if (true) {
            for (PhysicalRegister pr : PhysicalRegister.caller)
                output.printf("\tlw\t\t%s, %d(sp)\n", pr.name, pr.id * 4);
        }
        else {*/
            for (PhysicalRegister pr : G.function.allocate.usedcaller)
                output.printf("\tlw\t\t%s, %d(sp)\n", pr.name, pr.id * 4);
        //}
        output.printf("\taddi\t\tsp, sp, %d\n", 32 * 4);
    }

    void calleesave() {
        output.printf("\taddi\t\tsp, sp, %d\n", -32 * 4);
        for (PhysicalRegister pr: G.function.allocate.usedcallee)
            output.printf("\tsw\t\t%s, %d(sp)\n", pr.name, pr.id * 4);
    }

    void calleeresume() {
        for (PhysicalRegister pr: G.function.allocate.usedcallee)
            output.printf("\tlw\t\t%s, %d(sp)\n", pr.name, pr.id * 4);
        output.printf("\taddi\t\tsp, sp, %d\n", 32 * 4);
    }

    @Override
    public void translate(Function f) {
        this.G = f.graph;
        this.allocate = f.allocate;
        output.printf("\n");
        output.printf("%s:\n", G.function.name);

        calleesave();

        if (G.frame.size < 2048)
            output.printf("\taddi\t\tsp, sp, %d\n", -G.frame.size);
        else {
            output.printf("\tli\t\tt0, %d\n",  G.frame.size);
            output.printf("\tsub\t\tsp, sp, t0\n");
        }
        PhysicalRegister t0, t1, t2;
        boolean flag = false;
        for (int i=0; i < G.blocks.size(); i++) {
            Block block = G.blocks.get(i);
            output.printf("%s:\n", blockname(block));
            //output.printf("%d\n",block.instructions.size());
            for (int j = 0; j < block.instructions.size(); j++) {
                Instruction inst = block.instructions.get(j);
                //System.out.printf("%s\n",inst);
                if (flag) {
                    flag = false;
                    continue;
                }
                if (inst instanceof BinaryInst) {
                    BinaryInst binst = (BinaryInst)inst;
                    String op = binst.op;
                    if ((!(binst.src2 instanceof Immediate)) || (((Immediate) binst.src2).value >= 2048) || op.equals(">") || op.equals(">=")) {
                        t0 = loadsrc(PhysicalRegister.t0, binst.src1);
                        t1 = loadsrc(PhysicalRegister.t1, binst.src2);
                        t2 = loaddest(PhysicalRegister.t2, binst.dest);
                        if (op.equals("==")) {
                            Instruction next = block.instructions.get(j + 1);
                            if (next instanceof BranchInst) {
                                flag = true;
                                output.printf("\tbeq\t\t%s, %s, %s\n", t0.name, t1.name, ((BranchInst) next).trueDest.Lablename());
                                output.printf("\tj\t\t%s\n", ((BranchInst) next).falseDest.Lablename());
                            }
                            else {
                                output.printf("\txor\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                                output.printf("\tseqz\t\t%s, %s\n", t2.name, t2.name);
                            }
                        } else if (op.equals("<")) {
                            Instruction next = block.instructions.get(j + 1);
                            if (next instanceof BranchInst) {
                                flag = true;
                                output.printf("\tblt\t\t%s, %s, %s\n", t0.name, t1.name, ((BranchInst) next).trueDest.Lablename());
                                output.printf("\tj\t\t%s\n", ((BranchInst) next).falseDest.Lablename());
                            }
                            else output.printf("\tslt\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        } else if (op.equals("<=")) {
                            Instruction next = block.instructions.get(j + 1);
                            if (next instanceof BranchInst) {
                                flag = true;
                                output.printf("\tble\t\t%s, %s, %s\n", t0.name, t1.name, ((BranchInst) next).trueDest.Lablename());
                                output.printf("\tj\t\t%s\n", ((BranchInst) next).falseDest.Lablename());
                            }
                            else {
                                output.printf("\txor\t\tt2, %s, %s\n", t0.name, t1.name);
                                output.printf("\tseqz\t\tt2, t2\n");
                                output.printf("\tslt\t\tt1, %s, %s\n", t0.name, t1.name);
                                output.printf("\tor\t\t%s, t1, t2\n", t2.name);
                            }
                        } else if (op.equals(">=")) {
                            Instruction next = block.instructions.get(j + 1);
                            if (next instanceof BranchInst) {
                                flag = true;
                                output.printf("\tbge\t\t%s, %s, %s\n", t0.name, t1.name, ((BranchInst) next).trueDest.Lablename());
                                output.printf("\tj\t\t%s\n", ((BranchInst) next).falseDest.Lablename());
                            }
                            else {
                                output.printf("\txor\t\tt2, %s, %s\n", t0.name, t1.name);
                                output.printf("\tseqz\t\tt2, t2\n");
                                output.printf("\tslt\t\tt1, %s, %s\n", t1.name, t0.name);
                                output.printf("\tor\t\t%s, t1, t2\n", t2.name);
                            }
                        }
                        else if (op.equals(">")) {
                            Instruction next = block.instructions.get(j + 1);
                            if (next instanceof BranchInst) {
                                flag = true;
                                output.printf("\tbgt\t\t%s, %s, %s\n", t0.name, t1.name, ((BranchInst) next).trueDest.Lablename());
                                output.printf("\tj\t\t%s\n", ((BranchInst) next).falseDest.Lablename());
                            }
                            else output.printf("\tslt\t\t%s, %s, %s\n", t2.name, t1.name, t0.name);
                        } else if (op.equals("!=")) {
                            Instruction next = block.instructions.get(j + 1);
                            if (next instanceof BranchInst) {
                                flag = true;
                                output.printf("\tbne\t\t%s, %s, %s\n", t0.name, t1.name, ((BranchInst) next).trueDest.Lablename());
                                output.printf("\tj\t\t%s\n", ((BranchInst) next).falseDest.Lablename());
                            }
                            else {
                                output.printf("\txor\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                                output.printf("\tsnez\t\t%s, %s\n", t2.name, t2.name);
                            }
                        } else if (op.equals("+"))
                            output.printf("\tadd\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("-"))
                            output.printf("\tsub\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("*"))
                            output.printf("\tmul\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("/"))
                            output.printf("\tdiv\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("%"))
                            output.printf("\trem\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("<<"))
                            output.printf("\tsll\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals(">>"))
                            output.printf("\tsrl\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("&") || op.equals("&&"))
                            output.printf("\tand\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("|") || op.equals("||"))
                            output.printf("\tor\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        else if (op.equals("^"))
                            output.printf("\txor\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                    }
                    else {
                        t0 = loadsrc(PhysicalRegister.t0, binst.src1);
                        int value = ((Immediate) binst.src2).value;
                        t2 = loaddest(PhysicalRegister.t2, binst.dest);
                        if (op.equals("==")) {
                            output.printf("\txori\t\t%s, %s, %d\n", t2.name, t0.name, value);
                            output.printf("\tseqz\t\t%s, %s\n", t2.name, t2.name);
                        } else if (op.equals("<")) {
                            output.printf("\tslti\t\t%s, %s, %d\n", t2.name, t0.name, value);
                        } else if (op.equals("<=")) {
                            output.printf("\txori\t\tt2, %s, %d\n", t0.name, value);
                            output.printf("\tseqz\t\tt2, t2\n");
                            output.printf("\tslti\t\tt1, %s, %d\n", t0.name, value);
                            output.printf("\tor\t\t%s, t1, t2\n", t2.name);
                        } else if (op.equals("!=")) {
                            output.printf("\txori\t\t%s, %s, %d\n", t2.name, t0.name, value);
                            output.printf("\tsnez\t\t%s, %s\n", t2.name, t2.name);
                        } else if (op.equals("+"))
                            output.printf("\taddi\t\t%s, %s, %d\n", t2.name, t0.name, value);
                        else if (op.equals("-"))
                            output.printf("\taddi\t\t%s, %s, %d\n", t2.name, t0.name, -value);
                        else if (op.equals("*")) {
                            if (pow2.containsKey(value))
                                output.printf("\tslli\t\t%s, %s, %d\n", t2.name, t0.name, pow2.get(value));
                            else {
                                t1 = loadsrc(PhysicalRegister.t1, binst.src2);
                                output.printf("\tmul\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                            }

                        }
                        else if (op.equals("/")) {
                            if (pow2.containsKey(value))
                                output.printf("\tsrli\t\t%s, %s, %d\n", t2.name, t0.name, pow2.get(value));
                            else {
                                t1 = loadsrc(PhysicalRegister.t1, binst.src2);
                                output.printf("\tdiv\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                            }
                        }
                        else if (op.equals("%")) {
                            t1 = loadsrc(PhysicalRegister.t1, binst.src2);
                            output.printf("\trem\t\t%s, %s, %s\n", t2.name, t0.name, t1.name);
                        }
                        else if (op.equals("<<"))
                            output.printf("\tslli\t\t%s, %s, %d\n", t2.name, t0.name, value);
                        else if (op.equals(">>"))
                            output.printf("\tsrli\t\t%s, %s, %d\n", t2.name, t0.name, value);
                        else if (op.equals("&") || op.equals("&&"))
                            output.printf("\tandi\t\t%s, %s, %d\n", t2.name, t0.name, value);
                        else if (op.equals("|") || op.equals("||"))
                            output.printf("\tori\t\t%s, %s, %d\n", t2.name, t0.name, value);
                        else if (op.equals("^"))
                            output.printf("\txori\t\t%s, %s, %d\n", t2.name, t0.name, value);
                    }
                    store(binst.dest, t2);
                }
                else if (inst instanceof UnaryInst) {
                    UnaryInst uinst = (UnaryInst)inst;
                    String op = uinst.op;
                    t0 = loadsrc(PhysicalRegister.t0, uinst.src);
                    PhysicalRegister t = getregister(uinst.dest, PhysicalRegister.t0);
                    if (op.equals("-"))
                        output.printf("\tneg\t\t%s, %s\n", t.name, t0.name);
                    else if (op.equals("~"))
                        output.printf("\tnot\t\t%s, %s\n", t.name, t0.name);
                    else if (op.equals("++"))
                        output.printf("\taddi\t\t%s, %s, 1\n", t.name, t0.name);
                    else if (op.equals("--"))
                        output.printf("\taddi\t\t%s, %s, -1\n", t.name, t0.name);
                    else if (op.equals("!"))
                        output.printf("\txori\t\t%s, %s, 1\n", t.name, t0.name);
                    store(uinst.dest, t);
                }
                else if (inst instanceof JumpInst) {
                    JumpInst jinst = (JumpInst)inst;
                    output.printf("\tj\t\t%s\n", jinst.dest.Lablename());
                }
                else if (inst instanceof BranchInst) {
                    BranchInst binst = (BranchInst)inst;
                    t0 = loadsrc(PhysicalRegister.t0, binst.condition);
                    output.printf("\tbeqz\t\t%s, %s\n", t0.name, binst.falseDest.Lablename());
                    output.printf("\tbnez\t\t%s, %s\n", t0.name, binst.trueDest.Lablename());
                }
                else if (inst instanceof ReturnInst) {
                    ReturnInst rinst = (ReturnInst)inst;
                    t0 = loadsrc(PhysicalRegister.a0, rinst.src);
                    if (t0 != PhysicalRegister.a0)
                        output.printf("\tmv\t\ta0, %s\n", t0.name);

                    if (G.frame.size < 2048)
                        output.printf("\taddi\t\tsp, sp, %d\n", G.frame.size);
                    else {
                        output.printf("\tli\t\tt0, %d\n", G.frame.size);
                        output.printf("\tadd\t\tsp, sp, t0\n");
                    }
                    calleeresume();
                    output.printf("\tjr\t\tra\n");
                }
                else if (inst instanceof CallInst) {
                    CallInst cinst = (CallInst)inst;
                    callersave(cinst.function);
                    if (cinst.function.name.startsWith("__")) {
                        if (cinst.paras.size() >= 1) {
                            t0 = loadsrc(PhysicalRegister.a0, cinst.paras.get(0), 128);
                            if (t0 != PhysicalRegister.a0)
                                output.printf("\tmv\t\ta0, %s\n", t0.name);
                        }
                        if (cinst.paras.size() >= 2) {
                            t0 = loadsrc(PhysicalRegister.a1, cinst.paras.get(1), 128);
                            if (t0 != PhysicalRegister.a1)
                                output.printf("\tmv\t\ta1, %s\n", t0.name);
                        }
                        if (cinst.paras.size() >= 3) {
                            t0 = loadsrc(PhysicalRegister.a2, cinst.paras.get(2), 128);
                            if (t0 != PhysicalRegister.a2)
                                output.printf("\tmv\t\ta2, %s\n", t0.name);
                        }
                        if (cinst.paras.size() >= 4) {
                            t0 = loadsrc(PhysicalRegister.a3, cinst.paras.get(3), 128);
                            if (t0 != PhysicalRegister.a3)
                                output.printf("\tmv\t\ta3, %s\n", t0.name);
                        }
                    }
                    else {
                        int psize = cinst.function.graph.frame.size + 128;
                        if (cinst.paras.size() > 0)
                            if (psize < 2048)
                                output.printf("\taddi\t\tsp, sp, %d\n", -psize);
                            else {
                                output.printf("\tli\t\tt0, %d\n", psize);
                                output.printf("\tsub\t\tsp, sp, t0\n");
                            }
                        for (int p = cinst.paras.size() - 1; p >= 0; p--) {
                            t0 = loadsrc(PhysicalRegister.t0, cinst.paras.get(p), psize + 128);
                            if (p * 4  < 2048)
                                output.printf("\tsw\t\t%s, %d(sp)\n", t0.name, p * 4);
                            else {
                                output.printf("\tli\t\tt1, %d\n",  p * 4);
                                output.printf("\tadd\t\tt1, t1, sp\n");
                                output.printf("\tsw\t\t%s, 0(t1)\n", t0.name);
                            }
                        }
                        if (cinst.paras.size() > 0)
                            if (psize < 2048)
                                output.printf("\taddi\t\tsp, sp, %d\n", psize);
                            else {
                                output.printf("\tli\t\tt0, %d\n", psize);
                                output.printf("\tadd\t\tsp, sp, t0\n");
                            }
                    }
                    output.printf("\tcall\t%s\n", cinst.function.name);
                    callerresume(cinst.function);
                    if (cinst.dest != null)
                        store(cinst.dest, PhysicalRegister.a0);
                }
                else if (inst instanceof MoveInst) {
                    MoveInst minst = (MoveInst)inst;
                    PhysicalRegister t = getregister(minst.dest, PhysicalRegister.t0);
                    t0 = loadsrc(t, minst.src);
                    store(minst.dest, t0);
                }
                else if (inst instanceof LoadInst) {
                    LoadInst linst = (LoadInst)inst;
                    t0 = loadsrc(PhysicalRegister.t0, linst.src.base);
                    PhysicalRegister t = getregister(linst.dest, PhysicalRegister.t1);
                    if (linst.src.index.value < 2048) {
                        output.printf("\tlw\t\t%s, %d(%s)\n", t.name, linst.src.index.value, t0.name);
                    }
                    else {
                        t1 = loadsrc(PhysicalRegister.t1, linst.src.index);
                        output.printf("\tadd\t\tt0, %s, %s\n", t0.name, t1.name);
                        output.printf("\tlw\t\t%s, 0(t0)\n", t.name);
                    }
                    store(linst.dest, t);
                }
                else if (inst instanceof StoreInst) {
                    StoreInst sinst = (StoreInst)inst;
                    t0 = loadsrc(PhysicalRegister.t0, sinst.dest.base);
                    t2 = loadsrc(PhysicalRegister.t2, sinst.src);
                    if (sinst.dest.index.value >= 2048) {
                        t1 = loadsrc(PhysicalRegister.t1, sinst.dest.index);
                        output.printf("\tadd\t\tt0, %s, %s\n", t0.name, t1.name);
                        output.printf("\tsw\t\t%s, 0(t0)\n", t2.name);
                    }
                    else {
                        output.printf("\tsw\t\t%s, %d(%s)\n", t2.name, sinst.dest.index.value, t0.name);
                    }
                }
                else if (inst instanceof AllocateInst) {
                    AllocateInst ainst = (AllocateInst)inst;
                    callersave(null);
                    t0 = loadsrc(PhysicalRegister.a0, ainst.size, 128); //a0
                    if (t0 != PhysicalRegister.a0)
                        output.printf("\tmv\t\ta0, %s\n", t0.name);
                    output.printf("\tcall\t__malloc\n");
                    callerresume(null);
                    store(ainst.dest,PhysicalRegister.a0);
                }
            }
        }
        if (G.frame.size < 2048)
            output.printf("\taddi\t\tsp, sp, %d\n", G.frame.size);
        else {
            output.printf("\tli\t\tt0, %d\n", G.frame.size);
            output.printf("\tadd\t\tsp, sp, t0\n");
        }
        calleeresume();
        if (G.function.name.equals("main")) output.printf("\tlui\t\ta0, 0\n");
        output.printf("\tjr\t\tra\n");
    }
}