package Mxcomplier.Ast.Expression.BinaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Expression.UnaryExpr.NullExpr;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class AssignExpr extends BinaryExpr {
    private AssignExpr(Type type, Boolean isLeftValue, Expression Left, Expression Right) {
        super(type, isLeftValue, Left, Right);
    }
    public static Expression getExpression(Expression Left, Expression Right) {
        //if (Right instanceof NullExpr) System.out.println("Null");
        //System.out.println(Right.type instanceof Mxcomplier.Ast.Type.NullType);
        if (!Left.type.equalTo(Right.type) && !(Right.type instanceof NullType && Left.type instanceof ClassType)) {
            throw new CompileError("Assign to different type");
        }
        if (!Left.LeftValue) {
            throw new CompileError("it is not a left value");
        }
        return new AssignExpr(Left.type, true, Left, Right);
    }

    public void emit(ArrayList<Instruction> instructions) {
        LeftExpression.emit(instructions);
        RightExpression.emit(instructions);
        RightExpression.load(instructions);
        operand = LeftExpression.operand;
        if (operand instanceof Address)
            instructions.add(new StoreInst(RightExpression.operand, (Address) operand));
        else
            instructions.add(new MoveInst((VirtualRegister) LeftExpression.operand, RightExpression.operand));
    }

    @Override
    public void load(ArrayList<Instruction> instructions) {
        if (operand instanceof Address) {
            Address address = (Address)operand;
            operand = Environment.registerTable.add(null, 2);
            instructions.add(new LoadInst((VirtualRegister)operand, address));
        }
    }
}
