package Mxcomplier.Ast.Statement;

import Mxcomplier.Environment.Scope;
import Mxcomplier.IR.Instruction.Instruction;

import java.util.ArrayList;

public class BlockStmt extends Statement implements Scope {
    public ArrayList<Statement> statements;

    private BlockStmt() {
        statements = new ArrayList<>();
    }

    public static Statement getstmt() {
        return new BlockStmt();
    }

    public void addstmt(Statement stmt) {
        statements.add(stmt);
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        for (Statement statement : statements) {
            if (statement != null)
                statement.emit(instructions);
        }
    }
}
