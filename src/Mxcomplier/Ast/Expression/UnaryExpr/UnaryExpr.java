package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.IR.Instruction.Instruction;

import java.util.ArrayList;

public abstract class UnaryExpr extends Expression {
    public Expression Expr;
    protected UnaryExpr(Type type, Boolean isLeftValue, Expression expr) {
        super(type, isLeftValue);
        this.Expr = expr;
    }
    public abstract void emit(ArrayList<Instruction> instructions);
}
