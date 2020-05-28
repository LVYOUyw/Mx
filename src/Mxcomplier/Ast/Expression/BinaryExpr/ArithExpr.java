package Mxcomplier.Ast.Expression.BinaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BinaryInst;
import Mxcomplier.IR.Instruction.CallInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class ArithExpr extends BinaryExpr {
    public String op;

    private ArithExpr(Type type, Boolean isLeftValue, Expression Left, Expression Right, String oper) {
        super(type, isLeftValue, Left, Right);
        this.op = oper;
    }
    public static Expression getExpression(Expression Left, Expression Right, String oper) {
        if (Left.type instanceof IntType && Right.type instanceof IntType)
            return new ArithExpr(IntType.getType(), false, Left, Right, oper);
        else if (Left.type instanceof StringType && Right.type instanceof StringType && oper.equals("+"))
            return new ArithExpr(StringType.getType(), false, Left, Right, oper);
        throw new CompileError("ArithExpr should between integer or string");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        LeftExpression.emit(instructions);
        LeftExpression.load(instructions);
        RightExpression.emit(instructions);
        RightExpression.load(instructions);
        operand = Environment.registerTable.add(null, 2);
        if (LeftExpression.type instanceof StringType) {
            ArrayList<Operand> operands = new ArrayList<>();
            operands.add(LeftExpression.operand);
            operands.add(RightExpression.operand);
            instructions.add(new CallInst((VirtualRegister) operand, (Function)Environment.symbolTable.get("string+").type, operands));
        }
        else
        instructions.add(new BinaryInst((VirtualRegister)operand, LeftExpression.operand, RightExpression.operand, op));
    }
}
