package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.NullType;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Immediate;

import java.util.ArrayList;

public class NullExpr extends Expression {
    private NullExpr() {
        super(NullType.getType(),false);
    }

    public static NullExpr getnull() {
        return new NullExpr();
    }

    public void emit(ArrayList<Instruction> instructions) {
        operand = new Immediate(0);
    }
}
