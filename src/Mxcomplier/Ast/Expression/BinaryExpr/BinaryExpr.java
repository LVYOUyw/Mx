package Mxcomplier.Ast.Expression.BinaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.IR.Instruction.Instruction;

import java.util.ArrayList;

public abstract class BinaryExpr extends Expression {
    public Expression LeftExpression;
    public Expression RightExpression;

    protected BinaryExpr(Type type, Boolean isLeftValue, Expression Left, Expression Right) {
        super(type, isLeftValue);
        this.LeftExpression = Left;
        this.RightExpression = Right;
    }

    public abstract void emit(ArrayList<Instruction> instructions);
    public void load(ArrayList<Instruction> instructions) {}
}
