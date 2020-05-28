package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class AllocateInst extends Instruction {
    public VirtualRegister dest;
    public Operand size;

    public AllocateInst(VirtualRegister dest, Operand size) {
        this.dest = dest;
        this.size = size;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(dest);
        return operands;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.add(size);
        return operands;
    }
}
