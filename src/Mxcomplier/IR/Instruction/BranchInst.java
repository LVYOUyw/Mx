package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;

import java.util.ArrayList;

public class BranchInst extends Instruction {
    public LableInst trueDest, falseDest;
    public Operand condition;

    public BranchInst(Operand condition, LableInst trueDest, LableInst falseDest) {
        this.condition = condition;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        return operands;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(condition);
        return operands;
    }
}
