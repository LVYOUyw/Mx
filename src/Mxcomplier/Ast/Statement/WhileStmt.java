package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Scope;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BranchInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.JumpInst;
import Mxcomplier.IR.Instruction.LableInst;

import java.awt.*;
import java.util.ArrayList;

public class WhileStmt extends LoopStmt  {
    public Expression condition;
    public Statement stmt;

    WhileStmt(Expression expr, Statement stmt) {
        this.condition = expr;
        this.stmt = stmt;
    }

    public static Statement getstmt() {
        return new WhileStmt(null, null);
    }

    public static Statement getstmt(Expression expr, Statement stmt) {
        if (expr.type instanceof BoolType || expr.type == null)
            return new WhileStmt(expr, stmt);
        throw new CompileError("condition should be bool");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        loopbegin = new LableInst("whilebegin");
        loopend = new LableInst("whileend");
        LableInst whilebody = new LableInst("whilebody");
        instructions.add(new JumpInst(loopbegin));
        instructions.add(loopbegin);
        if (condition != null) {
            condition.emit(instructions);
            instructions.add(new BranchInst(condition.operand, whilebody, loopend));
        }
        instructions.add(whilebody);
        if (stmt != null)
            stmt.emit(instructions);
        instructions.add(new JumpInst(loopbegin));
        instructions.add(loopend);
    }
}
