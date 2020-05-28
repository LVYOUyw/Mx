package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public abstract class Instruction {
    public abstract ArrayList<Operand> getDestOps();
    public abstract ArrayList<Operand> getSrcOps();

    public ArrayList<VirtualRegister> getDestRegisters() {
        ArrayList<VirtualRegister> registers = new ArrayList<>();
        for (Operand operand: getDestOps()) {
            if (operand instanceof VirtualRegister)
                registers.add((VirtualRegister) operand);
        }
        return registers;
    }

    public ArrayList<VirtualRegister> getSrcRegisters() {
        ArrayList<VirtualRegister> registers = new ArrayList<>();
        for (Operand operand: getSrcOps()) {
            if (operand instanceof VirtualRegister)
                registers.add((VirtualRegister) operand);
        }
        return registers;
    }
}
