package Mxcomplier.IR.Instruction;

import Mxcomplier.IR.Block;
import Mxcomplier.IR.Operand.Operand;

import java.util.ArrayList;

public class LableInst extends Instruction {
    public String name;
    public Block block;

    public LableInst(String name) {
        this.name = name;
    }

    public String Lablename() {
        if (block == null) System.out.println("Block p");
        if (block.function == null) System.out.println("function p");
        return String.format("%s_%d_%s", block.function.name, block.id, name);
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
