package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.Immediate;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class SelfExpr extends UnaryExpr {
    public boolean oper; //0 for ++ 1 for --
    public boolean pos; //0 for front 1 for end

    private SelfExpr(Type type, Boolean isLeftValue, Expression expr, boolean op, boolean p) {
        super(type, isLeftValue, expr);
        this.oper = op;
        this.pos = p;
    }

    public static Expression getExpression(Expression expr, boolean op, boolean p) {
        if (expr.LeftValue == false)
            throw new CompileError("lvalue required");
        if (!(expr.type instanceof IntType))
            throw new CompileError("Int type required");
        return new SelfExpr(IntType.getType(), !p, expr, op, p);
    }

    public void emit(ArrayList<Instruction> instructions) {
        Expr.emit(instructions);
        if (!pos) {
            if (Expr.operand instanceof VirtualRegister) {
                operand = Expr.operand;
                instructions.add(new BinaryInst((VirtualRegister) operand, operand, new Immediate(1), oper ? "-" : "+"));

            }
            else if (Expr.operand instanceof Address) {
                Address address = (Address)Expr.operand;
                address = new Address(address.base, address.index, address.scale);
                Expr.load(instructions);
                operand = Expr.operand;
                instructions.add(new BinaryInst((VirtualRegister)operand, operand, new Immediate(1), oper ? "-" : "+"));
                instructions.add(new StoreInst(operand, address));
            }
        }
        else {
            operand = Environment.registerTable.add(null, 2);
            if (Expr.operand instanceof VirtualRegister) {
                Expr.load(instructions);
                instructions.add(new MoveInst((VirtualRegister) operand, Expr.operand));
                instructions.add(new BinaryInst((VirtualRegister) Expr.operand, Expr.operand, new Immediate(1), oper ? "-" : "+"));
            }
            else if (Expr.operand instanceof Address) {
                Address address = (Address)Expr.operand;
                address = new Address(address.base, address.index, address.scale);
                Expr.load(instructions);
                instructions.add(new MoveInst((VirtualRegister) operand, Expr.operand));
                instructions.add(new BinaryInst((VirtualRegister) Expr.operand, Expr.operand, new Immediate(1), oper ? "-" : "+"));
                instructions.add(new StoreInst(Expr.operand, address));
            }
        }
    }
}
