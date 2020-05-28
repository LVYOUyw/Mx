package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class BinaryInst extends Instruction {
    public VirtualRegister dest;
    public Operand src1, src2;
    public String op;

    public BinaryInst(VirtualRegister dest, Operand src1, Operand src2, String op) {
        this.dest = dest;
        this.src1 = src1;
        this.src2 = src2;
        this.op = op;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(src1);
        operands.add(src2);
        return operands;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(dest);
        return operands;
    }
}
