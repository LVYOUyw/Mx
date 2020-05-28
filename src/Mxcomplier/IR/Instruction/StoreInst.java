package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class StoreInst extends Instruction {
    public Operand src;
    public Address dest;

    public StoreInst(Operand src, Address dest) {
        this.dest = dest;
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
        operands.add(src);
        operands.add(dest.base);
        return operands;
    }
}
