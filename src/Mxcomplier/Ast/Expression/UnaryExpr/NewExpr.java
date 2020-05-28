package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.ArrayType;
import Mxcomplier.Ast.Type.ClassType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Error.CompileError;
import Mxcomplier.Error.RuntimeError;
import Mxcomplier.IR.Instruction.*;
import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.Immediate;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.List;

public class NewExpr extends Expression {
    public List<Expression> expressions;

    private NewExpr(Type type, boolean isLeftValue, List<Expression> exprs) {
        super(type, isLeftValue);
        this.expressions = exprs;
    }

    public static Expression getExpression(Type type, List<Expression> exprs) {
        if (exprs.size() == 0) {
            if (type instanceof ClassType)
                return new NewExpr(type, false, exprs);
            throw new CompileError("new Error");
        }
        else {
            Type array = ArrayType.getType(type, exprs.size());
            ArrayType tt = (ArrayType)array;
            if (type == null) System.out.println("Array null type");
            return new NewExpr(array, true, exprs);
        }
    }

    public void Allocate(Type type, Operand operand, int cur, int dimi, ArrayList<Instruction> instructions)
    {
        ArrayType arrayType = (ArrayType)type;
        VirtualRegister size = Environment.registerTable.add(null, 2);
        VirtualRegister tmp = Environment.registerTable.add(null, 2);

        instructions.add(new BinaryInst(size, expressions.get(cur).operand, new Immediate(4), "*"));
        instructions.add(new BinaryInst(size, size, new Immediate(4), "+"));
        instructions.add(new AllocateInst(tmp, size));

        instructions.add(new StoreInst(expressions.get(cur).operand, new Address(tmp, new Immediate(0), 4)));
        instructions.add(new BinaryInst(tmp, tmp, new Immediate(4), "+"));
        if (operand instanceof VirtualRegister)
            instructions.add(new MoveInst((VirtualRegister) operand, tmp));
        else if (operand instanceof Address)
            instructions.add(new StoreInst(tmp, (Address) operand));

        if (cur == expressions.size() - 1 && !(arrayType.reduce() instanceof ClassType)) return;

        VirtualRegister dest = Environment.registerTable.add(null, 2);
        instructions.add(new BinaryInst(dest, tmp, size, "+"));
        VirtualRegister pd = Environment.registerTable.add(null, 2);

        LableInst body = new LableInst("allocatebody");
        LableInst begin = new LableInst("allocatebegin");
        LableInst end = new LableInst("allocateend");
        instructions.add(new JumpInst(begin));

        instructions.add(begin);
        instructions.add(new BinaryInst(pd, tmp, dest, "!="));
        instructions.add(new BranchInst(pd, body, end));
        instructions.add(body);
        if (cur != dimi - 1)
            Allocate(arrayType.reduce(), new Address(tmp, new Immediate(0), 4), cur + 1, dimi, instructions);
        else if (cur == expressions.size() - 1){
            ClassType classType = (ClassType) arrayType.reduce();
            VirtualRegister vir = Environment.registerTable.add(null, 2);
            instructions.add(new AllocateInst(vir, new Immediate(classType.size)));
            instructions.add(new StoreInst(vir, new Address(tmp, new Immediate(0), 4)));
            if (classType.constructor != null) {
                ArrayList<Operand> paras = new ArrayList<>();
                paras.add(vir);
                instructions.add(new CallInst(null, classType.constructor, paras));
            }
        }
        instructions.add(new BinaryInst(tmp, tmp, new Immediate(4), "+"));
        instructions.add(new JumpInst(begin));
        instructions.add(end);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        int cnt = 0;
        for (Expression expression : expressions)
            if (expression != null) {
                expression.emit(instructions);
                expression.load(instructions);
                cnt++;
            }
        if (cnt == 0)
            throw new RuntimeError("New Error");
        operand = Environment.registerTable.add(null, 2);
        Allocate(type, operand, 0, cnt, instructions);
    }
}
