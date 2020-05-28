package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.BinaryExpr.CompareExpr;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.Ast.Type.StringType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.UnaryInst;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class NotExpr extends UnaryExpr {
    public boolean oper; // 0 for !          1 for ~

    private NotExpr(Type type, Boolean isLeftValue, Expression expr, boolean op) {
        super(type, isLeftValue, expr);
        this.oper = op;
    }

    public static Expression getExpression(Expression expr, boolean op) {
        if (expr.type instanceof IntType && op)
            return new NotExpr(IntType.getType(), false, expr, op);
        if (expr.type instanceof BoolType && !op)
            return new NotExpr(BoolType.getType(), false, expr, op);
        throw new CompileError("Not operation should act on bool or int");
    }

    public void emit(ArrayList<Instruction> instructions) {
        Expr.emit(instructions);
        Expr.load(instructions);
        operand = Environment.registerTable.add(null, 2);
        instructions.add(new UnaryInst((VirtualRegister) operand, Expr.operand, oper ? "~" : "!"));
    }
}
