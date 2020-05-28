package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.Error.RuntimeError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.JumpInst;

import java.util.ArrayList;

public class ContinueStmt extends Statement {
    public LoopStmt loop;

    private ContinueStmt(LoopStmt loop) {
        this.loop = loop;
    }

    public static Statement getstmt() {
        if (Environment.scopeTable.loopscopes.empty())
            throw new CompileError("continue should in loop statement");
        return new ContinueStmt((LoopStmt) Environment.scopeTable.loopscopes.peek());
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
       /* if (loop instanceof WhileStmt)
            instructions.add(new JumpInst(((WhileStmt) loop).loopbegin));
        else if (loop instanceof ForStmt) {
            instructions.add(new JumpInst(((ForStmt) loop).loopbegin));
            if (((ForStmt) loop).loopbegin == null)
                System.out.println("continue WA");
        }
        else
            throw new RuntimeError("LoopError");*/
       instructions.add(new JumpInst(loop.loopbegin));
    }
}
