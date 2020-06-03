package Mxcomplier.Translator;

import Mxcomplier.Ast.Statement.VardecStmt;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Environment.Environment;
import Mxcomplier.IR.Block;
import Mxcomplier.IR.Graph;
import Mxcomplier.IR.Operand.StringRegister;

import java.io.PrintStream;

public abstract class Translator {
    public static PrintStream output;

    public Translator(PrintStream o) {
        output = o;
    }


    public String blockname(Block block) {
        return String.format("%s_%d_%s", block.function.name, block.id, block.name);
    }

    public void translate() throws Exception {
        for (Function function: Environment.functions)
            init(function);

        output.printf(".data\n");
        for (VardecStmt var: Environment.globalvars) {
            output.printf(".globl %s\n",var.symbol.name);
            output.printf(".p2align\t2\n");
            output.printf("%s:\n", var.symbol.name);
            output.printf("\t.word\t0\n");
        }
        for (StringRegister sreg: Environment.registerTable.sregs) {
            output.printf("%s:\n",sreg.address());
            output.printf("\t.string \"%s\"\n", sreg.s);
        }

        output.printf(".text\n");
        for (Function function: Environment.functions) {
            output.printf(".globl %s\n", function.name);
            translate(function);
            output.printf("\n");
        }
    }

    public abstract void translate(Function f);

    public abstract void init(Function f);

}
