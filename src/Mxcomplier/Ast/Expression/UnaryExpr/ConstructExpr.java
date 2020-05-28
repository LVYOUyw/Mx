package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.ClassType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.RuntimeError;
import Mxcomplier.IR.Instruction.AllocateInst;
import Mxcomplier.IR.Instruction.CallInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Immediate;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class ConstructExpr extends Expression {

    private ConstructExpr(Type type, boolean isLeftValue) {
        super(type, isLeftValue);
    }

    public static Expression getExpression(Type type, boolean isLeftValue) {
        return new ConstructExpr(type, isLeftValue);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        if (!(type instanceof ClassType))
            throw new RuntimeError("New Error about class-array");
        ClassType classType = (ClassType)type;
        operand = Environment.registerTable.add(null, 2);
        instructions.add(new AllocateInst((VirtualRegister) operand, new Immediate(classType.size)));
        if (classType.constructor != null) {
            ArrayList<Operand> para = new ArrayList<>();
            para.add(operand);
            instructions.add(new CallInst(null, classType.constructor, para));
        }
    }
}
