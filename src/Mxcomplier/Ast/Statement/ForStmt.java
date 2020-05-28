package Mxcomplier.Ast.Statement;

import Mxcomplier.Ast.Expression.UnaryExpr.BoolExpr;
import Mxcomplier.Environment.Scope;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.BoolType;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BranchInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.JumpInst;
import Mxcomplier.IR.Instruction.LableInst;

import java.util.ArrayList;

public class ForStmt extends LoopStmt {
    public Expression init, condition, inc;
    public Statement stmt;

    private ForStmt(Expression init, Expression condition, Expression inc, Statement stmt) {
        this.init = init;
        this.condition = condition;
        this.inc = inc;
        this.stmt = stmt;
    }

    public static Statement getstmt(Expression init, Expression condition, Expression inc, Statement stmt) {
        if ( condition == null || condition.type instanceof BoolType)
            return new ForStmt(init, condition, inc, stmt);
        throw new CompileError("condition should be bool");
    }

    public static Statement getstmt() {
        return new ForStmt(null,null,null,null);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        LableInst forcondition = new LableInst("forcondition");
        LableInst forbody = new LableInst("forbody");
        loopbegin = new LableInst("forbegin");
        loopend = new LableInst("forend");
      //  System.out.println("add loopend");
        if (init != null) {
            init.emit(instructions);
            init.load(instructions);
        }
        instructions.add(new JumpInst(forcondition));
        instructions.add(forcondition);
        if (condition == null)
            condition = BoolExpr.getBool(true);
        condition.emit(instructions);
        instructions.add(new BranchInst(condition.operand, forbody, loopend));
        instructions.add(forbody);
        if (stmt != null) {
            stmt.emit(instructions);
        }
        instructions.add(new JumpInst(loopbegin));
        instructions.add(loopbegin);
        if (inc != null)
            inc.emit(instructions);
        instructions.add(new JumpInst(forcondition));
        instructions.add(loopend);
    }
}
