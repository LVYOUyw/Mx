package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Immediate;

import java.util.ArrayList;

public class IntExpr extends Expression {
    public int value;

    private IntExpr(int value) {
        super(IntType.getType(), false);
        this.value = value;
    }

    public static IntExpr getint(int value) {
        return new IntExpr(value);
    }

    public void emit(ArrayList<Instruction> instructions) {
        operand = new Immediate(value);
    }
}
