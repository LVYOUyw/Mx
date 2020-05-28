package Mxcomplier.Ast.Statement;

import Mxcomplier.Ast.Expression.BinaryExpr.CompareExpr;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BranchInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.JumpInst;
import Mxcomplier.IR.Instruction.LableInst;

import java.util.ArrayList;

public class IfStmt extends Statement {
    public Expression condition;
    public Statement ifstmt, elsestmt;

    private IfStmt(Expression expr, Statement ifstmt, Statement elsestmt) {
        this.condition = expr;
        this.ifstmt = ifstmt;
        this.elsestmt = elsestmt;
    }

    public static Statement getstmt(Expression expr, Statement ifstmt, Statement elsestmt) {
        if (!(expr.type instanceof BoolType))
            throw new CompileError("condition must bool type");
        return new IfStmt(expr, ifstmt, elsestmt);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        LableInst turedir = new LableInst("truedir");
        LableInst falsedir = new LableInst("falsedir");
        LableInst ifend = new LableInst("ifend");
        condition.emit(instructions);
        condition.load(instructions);
        instructions.add(new BranchInst(condition.operand, turedir, falsedir));

        instructions.add(turedir);
        if (ifstmt != null)
            ifstmt.emit(instructions);
        instructions.add(new JumpInst(ifend));
        instructions.add(falsedir);
        if (elsestmt != null)
            elsestmt.emit(instructions);
        instructions.add(new JumpInst(ifend));
        instructions.add(ifend);
    }
}
