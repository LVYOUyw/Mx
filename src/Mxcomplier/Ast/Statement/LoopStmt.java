package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Scope;
import Mxcomplier.IR.Instruction.LableInst;

public abstract class LoopStmt extends Statement implements Scope {
    public LableInst loopbegin, loopend;
}
