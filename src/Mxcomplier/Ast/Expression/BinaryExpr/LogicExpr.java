package Mxcomplier.Ast.Expression.BinaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.Ast.Type.StringType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class LogicExpr extends BinaryExpr{

    String op;

    private LogicExpr(Type type, Boolean isLeftValue, Expression Left, Expression Right, String oper) {
        super(type, isLeftValue, Left, Right);
        this.op = oper;
    }

    public static Expression getExpression(Expression Left, Expression Right, String oper) {
        if (Left.type instanceof BoolType && Right.type instanceof BoolType)
            return new LogicExpr(BoolType.getType(), false, Left, Right, oper);
        throw new CompileError("Logic operation should between bool");
    }

    public void emit(ArrayList<Instruction> instructions) {
        operand = Environment.registerTable.add(null, 2);
        LeftExpression.emit(instructions);
        LeftExpression.load(instructions);
        instructions.add(new MoveInst((VirtualRegister) operand, LeftExpression.operand));
        LableInst fz1 = new LableInst(String.format("Logic_fz1_%d", id));
        LableInst fz2 = new LableInst(String.format("Logic_fz2_%d", id));
        LableInst end = new LableInst(String.format("Logic_end_%d", id));
        if (op.equals("&&"))
            instructions.add(new BranchInst(LeftExpression.operand, fz1, fz2));
        else if (op.equals("||"))
            instructions.add(new BranchInst(LeftExpression.operand, fz2, fz1));
        instructions.add(fz1);
        RightExpression.emit(instructions);
        RightExpression.load(instructions);
        instructions.add(new MoveInst((VirtualRegister)operand,  RightExpression.operand));
        instructions.add(new JumpInst(end));
        instructions.add(fz2);
        instructions.add(new JumpInst(end));
        instructions.add(end);

    }
}
