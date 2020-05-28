package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;

import java.util.ArrayList;

public class ReturnInst extends Instruction {
    public Operand src;

    public ReturnInst(Operand src) {
        this.src = src;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        return operands;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        if (src != null)
            operands.add(src);
        return operands;
    }
}
