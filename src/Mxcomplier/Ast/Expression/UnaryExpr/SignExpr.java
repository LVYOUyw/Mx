package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BinaryInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.UnaryInst;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class SignExpr extends UnaryExpr {
    public boolean sign; //0 for +    1 for -
    private SignExpr(Type type, Boolean isLeftValue, Expression expr, boolean sig) {
        super(type, isLeftValue, expr);
        this.sign = sig;
    }
    public static Expression getExpression(Expression expr, boolean sig) {
        if (expr.type instanceof IntType) {
            if (expr instanceof IntExpr)
                return IntExpr.getint(-((IntExpr) expr).value);
            return new SignExpr(IntType.getType(), false, expr, sig);
        }
        throw new CompileError("Int type required");
    }

    public void emit(ArrayList<Instruction> instructions) {
        if (!sign) return;
        Expr.emit(instructions);
        Expr.load(instructions);
        operand = Environment.registerTable.add(null, 2);
        instructions.add(new UnaryInst((VirtualRegister) operand, Expr.operand, "-"));
    }
}
