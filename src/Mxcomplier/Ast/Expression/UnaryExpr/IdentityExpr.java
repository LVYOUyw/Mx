package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.BinaryExpr.dotExpr;
import Mxcomplier.Ast.Type.ClassType;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.UnaryInst;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class IdentityExpr extends Expression {
    public Symbol symbol;

    private IdentityExpr(Type type, boolean isLeftValue, Symbol symbol) {
        super(type, isLeftValue);
        this.symbol = symbol;
    }

    public static Expression getExpression(String name) {
        if (!Environment.symbolTable.containsName(name))
            throw new CompileError("undefined identity " + name);
        Symbol symbol = Environment.symbolTable.get(name);
        if (symbol.scope instanceof Function)
            return new IdentityExpr(symbol.type, false, symbol);
        else if (symbol.scope instanceof ClassType && !name.equals("this")) {

            return dotExpr.getExpression(IdentityExpr.getExpression("this"), name);
        }
        else return new IdentityExpr(symbol.type, true, symbol);
    }

    public void emit(ArrayList<Instruction> instructions) {
        operand = symbol.register;
        Environment.debug.put(symbol.name, (VirtualRegister) operand);
    }
}
