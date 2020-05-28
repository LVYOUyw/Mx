package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.StringType;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Immediate;

import java.util.ArrayList;

public class StringExpr extends Expression {
    public String value;

    private StringExpr(String value) {
        super(StringType.getType(), false);
        this.value = value;
    }

    public static StringExpr getstring(String value) {
        return new StringExpr(value);
    }

    public void emit(ArrayList<Instruction> instructions) {
        operand = Environment.registerTable.add(value);
    }
}
