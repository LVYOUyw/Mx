package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class LoadInst extends Instruction {
    public VirtualRegister dest;
    public Address src;

    public LoadInst(VirtualRegister dest, Address src) {
        this.dest = dest;
        this.src = src;
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
        operands.add(src.base);
        return operands;
    }
}
