package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class UnaryInst extends Instruction {
    public VirtualRegister dest;
    public Operand src;
    public String op;

    public UnaryInst(VirtualRegister dest, Operand src, String op) {
        this.dest = dest;
        this.src = src;
        this.op = op;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(src);
        return operands;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(dest);
        return operands;
    }
}
