package Mxcomplier.Translator;

import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Environment.Environment;
import Mxcomplier.IR.Block;
import Mxcomplier.IR.Graph;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.*;

import java.io.PrintStream;

public class Naive_Translator extends Translator {

    Graph G;

    public Naive_Translator(PrintStream output) {
        super(output);
    }

    public void load(PhysicalRegister dest, Operand src, int... opt) {
        if (src instanceof Address) {
            Address addr = (Address)src;
            load(PhysicalRegister.registers.get(16), addr.base, opt);
            load(PhysicalRegister.registers.get(17), addr.index, opt);
            output.printf("\tadd\t\t%s, %s, %s\n", PhysicalRegister.registers.get(16).name, PhysicalRegister.registers.get(16).name, PhysicalRegister.registers.get(17).name);
            output.printf("\tlw\t\t%s, 0(%s)\n", dest.name, PhysicalRegister.registers.get(16).name);
        }
        if (src instanceof Immediate)
            output.printf("\tli\t\t%s, %s\n", dest.name, String.valueOf(((Immediate) src).value));
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
                if (offset < 2048)
                    output.printf("\tlw\t\t%s, %s\n", dest.name, String.format("%d(sp)", offset));
                else {
                    output.printf("\tli\t\t%s, %d\n", dest.name, offset);
                    output.printf("\tadd\t\t%s, %s, sp\n", PhysicalRegister.registers.get(18).name, dest.name);
                    output.printf("\tlw\t\t%s, 0(%s)\n", dest.name, PhysicalRegister.registers.get(18).name);
                }
            }
        }
    }

    public void store(VirtualRegister dest, PhysicalRegister src) {
        int type = dest.type;
        if (type == 1) {
            String s = "%";
            output.printf("\tlui\t\t%s, %shi(%s)\n", PhysicalRegister.registers.get(10).name, s, dest.symbol.name);
            output.printf("\tsw\t\t%s, %slo(%s)(%s)\n", src.name, s, dest.symbol.name, PhysicalRegister.registers.get(10).name);
        }
        else {
            int offset = G.frame.getOffset(dest);
            if (offset < 2048)
                output.printf("\tsw\t\t%s, %s\n", src.name, String.format("%d(sp)", offset));
            else {
                output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, offset);
                output.printf("\tadd\t\t%s, %s, sp\n", PhysicalRegister.registers.get(18).name, PhysicalRegister.registers.get(18).name);
                output.printf("\tsw\t\t%s, 0(%s)\n", src.name, PhysicalRegister.registers.get(18).name);
            }
        }
    }

    public void init(Function f) {

    }

    @Override
    public void translate(Function f) {
        this.G = f.graph;
        output.printf("\n");
        output.printf("%s:\n", G.function.name);

        if (G.function.name.equals("main")) {
            if (G.frame.size < 2048)
                output.printf("\taddi\t\tsp, sp, %d\n", -G.frame.size);
            else {
                output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, G.frame.size);
                output.printf("\tsub\t\tsp, sp, %s\n", PhysicalRegister.registers.get(18).name);
            }
        }
        if (G.frame.size - 4 < 2048)
            output.printf("\tsw\t\tra, %d(sp)\n", G.frame.size - 4);
        else {
            output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, G.frame.size - 4);
            output.printf("\tadd\t\t%s, %s, sp\n", PhysicalRegister.registers.get(18).name, PhysicalRegister.registers.get(18).name);
            output.printf("\tsw\t\tra, 0(%s)\n", PhysicalRegister.registers.get(18).name);
        }

        for (int i=0; i < G.blocks.size(); i++) {
            Block block = G.blocks.get(i);
            output.printf("%s:\n", blockname(block));
            for (Instruction inst: block.instructions) {
                if (inst instanceof BinaryInst) {
                    BinaryInst binst = (BinaryInst)inst;
                    String op = binst.op;
                    load(PhysicalRegister.registers.get(0), binst.src1);
                    load(PhysicalRegister.registers.get(1), binst.src2);
                    if (op.equals("==")) {
                        output.printf("\txor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                        output.printf("\tseqz\t\t%s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    }
                    else if (op.equals("<")) {
                        output.printf("\tslt\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    }
                    else if (op.equals("<=")) {
                        output.printf("\txor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                        output.printf("\tseqz\t\t%s, %s\n", PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(2).name);
                        output.printf("\tslt\t\t%s, %s, %s\n", PhysicalRegister.registers.get(3).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                        output.printf("\tor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(3).name);
                    }
                    else if (op.equals(">")) {
                        output.printf("\tslt\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name, PhysicalRegister.registers.get(0).name);
                    }
                    else if (op.equals(">=")) {
                        output.printf("\txor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                        output.printf("\tseqz\t\t%s, %s\n", PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(2).name);
                        output.printf("\tslt\t\t%s, %s, %s\n", PhysicalRegister.registers.get(3).name, PhysicalRegister.registers.get(1).name, PhysicalRegister.registers.get(0).name);
                        output.printf("\tor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(3).name);
                    }
                    else if (op.equals("!=")) {
                        output.printf("\txor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                        output.printf("\tsnez\t\t%s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    }
                    else if (op.equals("+"))
                        output.printf("\tadd\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("-"))
                        output.printf("\tsub\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("*"))
                        output.printf("\tmul\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("/"))
                        output.printf("\tdiv\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("%"))
                        output.printf("\trem\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("<<"))
                        output.printf("\tsll\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals(">>"))
                        output.printf("\tsrl\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("&") || op.equals("&&"))
                        output.printf("\tand\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("|") || op.equals("||"))
                        output.printf("\tor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    else if (op.equals("^"))
                        output.printf("\txor\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    store(binst.dest, PhysicalRegister.registers.get(0));
                }
                else if (inst instanceof UnaryInst) {
                    UnaryInst uinst = (UnaryInst)inst;
                    String op = uinst.op;
                    load(PhysicalRegister.registers.get(0), uinst.src);
                    if (op.equals("-"))
                        output.printf("\tneg\t\t%s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    else if (op.equals("~"))
                        output.printf("\tnot\t\t%s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    else if (op.equals("++"))
                        output.printf("\taddi\t\t%s, %s, 1\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    else if (op.equals("--"))
                        output.printf("\taddi\t\t%s, %s, -1\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    else if (op.equals("!"))
                        output.printf("\txori\t\t%s, %s, 1\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name);
                    store(uinst.dest, PhysicalRegister.registers.get(0));
                }
                else if (inst instanceof JumpInst) {
                    JumpInst jinst = (JumpInst)inst;
                    output.printf("\tj\t\t%s\n", jinst.dest.Lablename());
                }
                else if (inst instanceof BranchInst) {
                    BranchInst binst = (BranchInst)inst;
                    load(PhysicalRegister.registers.get(0), binst.condition);
                    output.printf("\tbeqz\t\t%s, %s\n", PhysicalRegister.registers.get(0).name, binst.falseDest.Lablename());
                    output.printf("\tbnez\t\t%s, %s\n", PhysicalRegister.registers.get(0).name, binst.trueDest.Lablename());
                }
                else if (inst instanceof ReturnInst) {
                    ReturnInst rinst = (ReturnInst)inst;
                    load(PhysicalRegister.registers.get(1), rinst.src);
                    if (G.frame.size - 4 < 2048)
                        output.printf("\tlw\t\tra, %d(sp)\n", G.frame.size - 4);
                    else {
                        output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, G.frame.size - 4);
                        output.printf("\tadd\t\t%s, %s, sp\n", PhysicalRegister.registers.get(18).name, PhysicalRegister.registers.get(18).name);
                        output.printf("\tlw\t\tra, 0(%s)\n", PhysicalRegister.registers.get(18).name);
                    }

                    output.printf("\tjr\t\tra\n");
                }
                else if (inst instanceof CallInst) {
                    CallInst cinst = (CallInst)inst;
                    //         System.out.printf("call : %s\n", cinst.function.name);
                    int psize = cinst.function.name.startsWith("__") ? 32  : cinst.function.graph.frame.size;
                    if (psize < 2048)
                        output.printf("\taddi\t\tsp, sp,  %d\n", -psize);
                    else {
                        output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, psize);
                        output.printf("\tsub\t\tsp, sp, %s\n", PhysicalRegister.registers.get(18).name);
                    }
                    if (cinst.function.name.startsWith("__")) {
                        if (cinst.paras.size() >= 1)
                            load(PhysicalRegister.registers.get(1), cinst.paras.get(0), psize); //a0
                        if (cinst.paras.size() >= 2)
                            load(PhysicalRegister.registers.get(6), cinst.paras.get(1), psize); //a1
                        if (cinst.paras.size() >= 3)
                            load(PhysicalRegister.registers.get(7), cinst.paras.get(2), psize); //a2
                        if (cinst.paras.size() >= 4)
                            load(PhysicalRegister.registers.get(8), cinst.paras.get(3), psize); //a3
                    }
                    else
                        for (int p = cinst.paras.size() - 1; p >= 0; p--) {
                            load(PhysicalRegister.registers.get(0), cinst.paras.get(p), psize);
                            if (p * 4 < 2048)
                                output.printf("\tsw\t\t%s, %d(sp)\n", PhysicalRegister.registers.get(0).name, p * 4);
                            else {
                                output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, p * 4);
                                output.printf("\tadd\t\t%s, %s, sp\n", PhysicalRegister.registers.get(18).name, PhysicalRegister.registers.get(18).name);
                                output.printf("\tsw\t\t%s, 0(%s)\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(18).name);
                            }
                        }
                    output.printf("\tcall\t%s\n", cinst.function.name);
                    if (psize < 2048)
                        output.printf("\taddi\t\tsp, sp, %d\n", psize);
                    else {
                        output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, psize);
                        output.printf("\tadd\t\tsp, sp, %s\n", PhysicalRegister.registers.get(18).name);
                    }
                    if (cinst.dest != null)
                        store(cinst.dest, PhysicalRegister.registers.get(1));
                }
                else if (inst instanceof MoveInst) {
                    MoveInst minst = (MoveInst)inst;
                    load(PhysicalRegister.registers.get(0), minst.src);
                    store(minst.dest, PhysicalRegister.registers.get(0));
                }
                else if (inst instanceof LoadInst) {
                    LoadInst linst = (LoadInst)inst;
                    load(PhysicalRegister.registers.get(0), linst.src.base);
                    load(PhysicalRegister.registers.get(1), linst.src.index);
                    output.printf("\tadd\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    output.printf("\tlw\t\t%s, 0(%s)\n", PhysicalRegister.registers.get(1).name, PhysicalRegister.registers.get(0).name);
                    store(linst.dest, PhysicalRegister.registers.get(1));
                }
                else if (inst instanceof StoreInst) {
                    StoreInst sinst = (StoreInst)inst;
                    load(PhysicalRegister.registers.get(0), sinst.dest.base);
                    load(PhysicalRegister.registers.get(1), sinst.dest.index);
                    load(PhysicalRegister.registers.get(2), sinst.src);
                    output.printf("\tadd\t\t%s, %s, %s\n", PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(0).name, PhysicalRegister.registers.get(1).name);
                    output.printf("\tsw\t\t%s, 0(%s)\n", PhysicalRegister.registers.get(2).name, PhysicalRegister.registers.get(0).name);
                }
                else if (inst instanceof AllocateInst) {
                    AllocateInst ainst = (AllocateInst)inst;
                    load(PhysicalRegister.registers.get(1), ainst.size); //a0
                    output.printf("\tcall\t__malloc\n");
                    store(ainst.dest,PhysicalRegister.registers.get(1));
                }
            }
        }
        if (G.frame.size - 4 < 2048)
            output.printf("\tlw\t\tra, %d(sp)\n", G.frame.size - 4);
        else {
            output.printf("\tli\t\t%s, %d\n", PhysicalRegister.registers.get(18).name, G.frame.size - 4);
            output.printf("\tadd\t\t%s, %s, sp\n", PhysicalRegister.registers.get(18).name, PhysicalRegister.registers.get(18).name);
            output.printf("\tlw\t\tra, 0(%s)\n", PhysicalRegister.registers.get(18).name);
        }
        output.printf("\tli\t\ta0, 0\n");
        output.printf("\tjr\t\tra\n");
    }
}