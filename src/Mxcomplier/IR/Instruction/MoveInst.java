package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class MoveInst extends Instruction {
    public VirtualRegister dest;
    public Operand src;

    public MoveInst(VirtualRegister dest, Operand src) {
        this.dest = dest;
        this.src = src;
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
