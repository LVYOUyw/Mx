package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Expression.UnaryExpr.ConstructExpr;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.JumpInst;
import Mxcomplier.IR.Instruction.ReturnInst;

import java.util.ArrayList;

public class ReturnStmt extends Statement {
    public Expression expr = null;
    public Function function;

    private ReturnStmt(Expression expr, Function function) {
        this.expr = expr;
        this.function = function;
    }

    public static Statement getstmt(Expression expr) {
        if (Environment.scopeTable.curfunction == null)
            throw new CompileError("return should in functions");
        Function function = (Function)Environment.scopeTable.curfunction;
        if (expr instanceof ConstructExpr)
            function.isleftvalue = true;
        if (expr.type.equalTo(function.type) ||  expr.type instanceof NullType)
            return new ReturnStmt(expr, function);
        throw new CompileError("false return type");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        if (expr != null) {
            expr.emit(instructions);
            expr.load(instructions);
            instructions.add(new ReturnInst(expr.operand));
        }
        instructions.add(new JumpInst(function.end));
    }
}
