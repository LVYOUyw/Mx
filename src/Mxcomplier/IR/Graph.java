package Mxcomplier.IR;

import Mxcomplier.Ast.Statement.VardecStmt;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;

public class Graph {
    public Function function;
    public ArrayList<Block> blocks;
    public Frame frame;
    public boolean hascall;

    public Graph(Function function) {
        this.function = function;
        hascall = false;
        this.init();

    }

    public void livenessAnalysis() {
        for (Block block: blocks) {
            block.liveness.usedRegisters = new ArrayList<>();
            block.liveness.definedRegisters = new ArrayList<>();
            for (Instruction inst : block.instructions) {
                for (VirtualRegister vir : inst.getSrcRegisters())
                    if (!block.liveness.definedRegisters.contains(vir))
                        block.liveness.usedRegisters.add(vir);
                //for (VirtualRegister vir : inst.getDestRegisters())
                for (VirtualRegister vir: inst.getDestRegisters())
                    block.liveness.definedRegisters.add(vir);
            }
        }

        for (Block block: blocks) {
            block.liveness.livein = new HashSet<>();
            block.liveness.liveout = new HashSet<>();
        }

        while (true) {
            for (Block block: blocks) {
                block.liveness.livein = new HashSet<>();
                for (VirtualRegister vir: block.liveness.liveout)
                    block.liveness.livein.add(vir);
                for (VirtualRegister vir: block.liveness.definedRegisters)
                    block.liveness.livein.remove(vir);
                for (VirtualRegister vir: block.liveness.usedRegisters)
                    block.liveness.livein.add(vir);
            }
            boolean pd = false;
            for (Block block: blocks) {
                HashSet<VirtualRegister> last = block.liveness.liveout;
                block.liveness.liveout = new HashSet<>();
                for (Block succ: block.succ) {
                    for (VirtualRegister vir: succ.liveness.livein)
                        block.liveness.liveout.add(vir);
                }
                if (!block.liveness.liveout.equals(last))
                    pd = true;
            }
            if (!pd) break;
        }
    }


    public void init() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        function.begin = new LableInst("funcbegin");
        function.end = new LableInst("funcend");
        function.body = new LableInst("funcbody");

        instructions.add(function.begin);
        if (function.name.equals("main")) {
            for (VardecStmt vardecStmt: Environment.globalvars) {
                vardecStmt.emit(instructions);
            }
        }
        instructions.add(new JumpInst(function.body));
        instructions.add(function.body);
        function.stmts.emit(instructions);
        instructions.add(function.end);

        blocks = new ArrayList<>();
        int i;
        int next = 0;
        for (i = 0; i < instructions.size(); i = next) {
            if (instructions.get(i) instanceof LableInst) {
                LableInst lableInst = (LableInst)instructions.get(i);
                Block block = new Block(function, lableInst.name, blocks.size(), lableInst);
                blocks.add(block);
                lableInst.block = block;
                for (next = i + 1; next < instructions.size(); next++) {
                    if (instructions.get(next) instanceof LableInst)
                        break;
                    block.instructions.add(instructions.get(next));
                    if (instructions.get(next) instanceof JumpInst || instructions.get(next) instanceof BranchInst)
                        break;
                }
            }
            else
                next = i + 1;
        }


        for (Block block: blocks) {
            if (block.instructions.size() == 0) continue;
            Instruction endinst = block.instructions.get(block.instructions.size() - 1);
            if (endinst instanceof JumpInst) {
                Block dest = ((JumpInst) endinst).dest.block;
                block.succ.add(dest);
                dest.pred.add(block);
            }
            else if (endinst instanceof BranchInst) {
                Block truedest = ((BranchInst) endinst).trueDest.block;
                Block falsedest = ((BranchInst) endinst).falseDest.block;
                block.succ.add(truedest);
                block.succ.add(falsedest);
                truedest.pred.add(block);
                falsedest.pred.add(block);
            }
        }

        HashSet<VirtualRegister> registers = new HashSet<>();
        for (Block block: blocks)
            for (Instruction inst: block.instructions) {
                //System.out.printf("%s\n", inst);
                if (inst instanceof CallInst || inst instanceof AllocateInst) {
                  //  System.out.printf("true\n");
                    hascall = true;
                }
                for (VirtualRegister vir: inst.getDestRegisters())
                    if (vir.type == 2)
                        registers.add(vir);

                for (VirtualRegister vir: inst.getSrcRegisters())
                    if (vir.type == 2)
                        registers.add(vir);
            }

        frame = new Frame();
        for (Symbol para: function.parameters) {
            frame.paras.put(para.register, frame.size);
            frame.size += 4;
        }
        for (VirtualRegister vir: registers) {
            frame.temp.put(vir, frame.size);
            frame.size += 4;
        }

        frame.size += 4 * 4;
        livenessAnalysis();
    }
}