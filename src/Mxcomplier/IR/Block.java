package Mxcomplier.IR;

import Mxcomplier.Ast.Type.Function;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.LableInst;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;

public class Block {
    public Function function;
    public String name;
    public int id;
    public LableInst lableInst;
    public ArrayList<Instruction> instructions;
    public ArrayList<Block> pred, succ;
    public Liveness liveness;

    public class Liveness {
        public HashSet<VirtualRegister> livein;
        public HashSet<VirtualRegister> liveout;
        public ArrayList<VirtualRegister> usedRegisters;
        public ArrayList<VirtualRegister> definedRegisters;

        public Liveness() {
            livein = new HashSet<>();
            liveout = new HashSet<>();
            usedRegisters = new ArrayList<>();
            definedRegisters = new ArrayList<>();
        }
    }

    public Block(Function function, String name, int id, LableInst lableInst) {
        this.function = function;
        this.name = name;
        this.id = id;
        this.lableInst = lableInst;
        this.instructions = new ArrayList<>();
        this.pred = new ArrayList<>();
        this.succ = new ArrayList<>();
        this.liveness = new Liveness();
    }
}
