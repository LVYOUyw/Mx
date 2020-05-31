package Mxcomplier.Ast.Expression.BinaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Expression.UnaryExpr.BoolExpr;
import Mxcomplier.Ast.Expression.UnaryExpr.IntExpr;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.Ast.Type.StringType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BinaryInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.LableInst;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class CompareExpr extends BinaryExpr {
    public String op;

    private CompareExpr(Type type, Boolean isLeftValue, Expression Left, Expression Right, String oper) {
        super(type, isLeftValue, Left, Right);
        this.op = oper;
    }

    public static Expression getExpression(Expression Left, Expression Right, String oper) {
        if (Left.type instanceof IntType && Right.type instanceof IntType) {
            if (Left instanceof IntExpr && Right instanceof IntExpr) {
                int a1 = ((IntExpr) Left).value;
                int a2 = ((IntExpr) Right).value;
                boolean a3 = false;
                if (oper.equals("<")) a3 = a1 < a2;
                else if (oper.equals(">")) a3 = a1 > a2;
                else if (oper.equals("<=")) a3 = a1 <= a2;
                else if (oper.equals(">=")) a3 = a1 >= a2;
                return BoolExpr.getBool(a3);
            }
            return new CompareExpr(BoolType.getType(), false, Left, Right, oper);
        }
        if (Left.type instanceof StringType && Right.type instanceof StringType)
            return new CompareExpr(BoolType.getType(), false, Left, Right, oper);
        throw new CompileError("Compare should between integer or string");
    }

    public void emit(ArrayList<Instruction> instructions) {
        LeftExpression.emit(instructions);
        LeftExpression.load(instructions);
        RightExpression.emit(instructions);
        RightExpression.load(instructions);
        operand = Environment.registerTable.add(null, 2);
      //  if (!op.equals("<=") && !op.equals(">="))
            instructions.add(new BinaryInst((VirtualRegister)operand, LeftExpression.operand, RightExpression.operand, op));
      /*  else {
            VirtualRegister tmp = Environment.registerTable.add(null, 2);
            instructions.add(new BinaryInst((VirtualRegister)tmp, LeftExpression.operand, RightExpression.operand, "=="));
            instructions.add(new BinaryInst((VirtualRegister)operand, LeftExpression.operand, RightExpression.operand, op.equals("<=")?"<":">"));
            instructions.add(new BinaryInst((VirtualRegister)operand, operand, tmp, "|"));
        }*/
    }
}
