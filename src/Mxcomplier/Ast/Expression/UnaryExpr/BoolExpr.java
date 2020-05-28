package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Immediate;

import java.util.ArrayList;

public class BoolExpr extends Expression {
    public boolean value;

    private BoolExpr(boolean value) {
        super(BoolType.getType(), false);
        this.value = value;
    }

    public static BoolExpr getBool(boolean value) {
        return new BoolExpr(value);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        operand = new Immediate(value ? 1 : 0);
    }
}
