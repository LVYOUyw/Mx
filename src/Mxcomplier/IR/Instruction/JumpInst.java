package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;

import java.util.ArrayList;

public class JumpInst extends Instruction {
    public LableInst dest;

    public JumpInst(LableInst dest) {
        this.dest = dest;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        return operands;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        return operands;
    }
}
