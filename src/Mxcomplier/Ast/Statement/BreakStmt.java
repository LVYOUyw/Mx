package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.Error.RuntimeError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.JumpInst;
import Mxcomplier.IR.Instruction.LableInst;

import java.util.ArrayList;

public class BreakStmt extends Statement {

    public LoopStmt loop;

    private BreakStmt(LoopStmt loop) {
        System.out.println("create break");
        this.loop = loop;
    }

    public static Statement getstmt() {
        if (Environment.scopeTable.loopscopes.empty())
            throw new CompileError("break or continue should in loop statement");
        return new BreakStmt((LoopStmt) Environment.scopeTable.loopscopes.peek());
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
      /*  if (loop instanceof WhileStmt)
            instructions.add(new JumpInst(((WhileStmt) loop).loopend));
        else if (loop instanceof ForStmt) {
            instructions.add(new JumpInst(((ForStmt) loop).loopend));
            if (((ForStmt) loop).loopend == null)
                System.out.println("break WA");
        }
        else
            throw new RuntimeError("Loop Error");*/
        if (loop.loopend == null)
            System.out.println("break WA");
       instructions.add(new JumpInst(loop.loopend));
    }
}
