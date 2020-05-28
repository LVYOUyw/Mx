package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.BinaryExpr.dotExpr;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Ast.Type.NullType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Ast.Type.VoidType;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.CallInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpr extends Expression {
    public Function function;
    public List<Expression> paras;

    private FunctionExpr(Type type, boolean isLeftValue, Function f, List<Expression> exprs) {
        super(type, isLeftValue);
        this.function = f;
        this.paras = exprs;
    }

    public static Expression getExpression(Expression expr, List<Expression> para) {
        if (expr instanceof ConstructExpr) {
            ConstructExpr construct = (ConstructExpr) expr;
            return construct;
        }
        if (expr.type instanceof Function) {
            Function function = (Function) expr.type;
            if (expr instanceof dotExpr) {
                para.add(0, ((dotExpr) expr).Class);
            }
            if (para.size() != function.parameters.size())
                throw new CompileError("different number of parameters");
            for (int i=0; i<para.size();i++)
                if (!para.get(i).type.equalTo(function.parameters.get(i).type) && !(para.get(i).type instanceof NullType))
                    throw new CompileError("different types of parameters");
            return new FunctionExpr(function.type, function.isleftvalue, function, para);
        }
        throw new CompileError("wrong function call");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        ArrayList<Operand> operands = new ArrayList<>();
        for (Expression para: paras) {
            para.emit(instructions);
            para.load(instructions);
            operands.add(para.operand);
        }
        if (type instanceof VoidType)
            instructions.add(new CallInst(null, function, operands));
        else {
            operand = Environment.registerTable.add(null, 2);
            instructions.add(new CallInst((VirtualRegister) operand, function, operands));
        }
    }
}
