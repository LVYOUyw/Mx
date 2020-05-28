package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Symbol;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.NullType;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.MoveInst;

import java.util.ArrayList;

public class VardecStmt extends Statement {
    public Symbol symbol;
    public Expression expr;

    VardecStmt(Symbol symbol, Expression expr) {
        this.symbol = symbol;
        this.expr = expr;
    }

    public static Statement getstmt(Symbol symbol, Expression expr) {
        if (symbol.type.equalTo(expr.type) || expr.type instanceof NullType)
            return new VardecStmt(symbol, expr);
        throw new CompileError("two different types");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        if (expr != null) {
            expr.emit(instructions);
            expr.load(instructions);
            instructions.add(new MoveInst(symbol.register, expr.operand));
        }
    }
}
