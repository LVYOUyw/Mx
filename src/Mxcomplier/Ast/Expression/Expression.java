package Mxcomplier.Ast.Expression;

import Mxcomplier.Ast.Node;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Operand;

import java.util.ArrayList;

public abstract class Expression implements Node {
    public Type type;
    public Boolean LeftValue;
    public Operand operand;
    public int id;

    public Expression(Type type, Boolean isLeftValue) {
        this.type = type;
        this.LeftValue = isLeftValue;
        this.id = Environment.getExprid();
    }

    public abstract void emit(ArrayList<Instruction> instructions);
    public void load(ArrayList<Instruction> instructions) {}
}
