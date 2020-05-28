package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.ClassType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.Instruction;

import java.util.ArrayList;

public class ThisExpr extends Expression {

    private ThisExpr(Type type, boolean isLeftValue) {
        super(type, isLeftValue);
    }

    public static Expression getExpression(Type type, boolean isLeftValue) {
        if (!(type instanceof ClassType))
            throw new CompileError("this should be used in a class");
        return IdentityExpr.getExpression("this");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {

    }
}
