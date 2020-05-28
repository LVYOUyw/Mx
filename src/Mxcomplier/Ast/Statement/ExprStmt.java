package Mxcomplier.Ast.Statement;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.IR.Instruction.Instruction;

import java.util.ArrayList;

public class ExprStmt extends Statement {
    public Expression expr;

    private ExprStmt(Expression expr) {
        this.expr = expr;
    }

    public static Statement getstmt(Expression expr) {
        return new ExprStmt(expr);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        if (expr != null) {
            expr.emit(instructions);
            expr.load(instructions);
        }
    }
}
