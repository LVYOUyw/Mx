package Mxcomplier.Ast.Statement;

import Mxcomplier.Ast.Node;
import Mxcomplier.IR.Instruction.Instruction;

import java.util.ArrayList;

public abstract class Statement implements Node {
    public abstract void emit(ArrayList<Instruction> instructions);
}
