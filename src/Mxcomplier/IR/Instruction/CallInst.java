package Mxcomplier.IR.Instruction;

import Mxcomplier.Ast.Statement.VardecStmt;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Environment.Environment;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.List;

public class CallInst extends Instruction {
    public VirtualRegister dest;
    public Function function;
    public List<Operand> paras;

    public CallInst(VirtualRegister dest, Function function, List<Operand> paras) {
        this.dest = dest;
        this.function = function;
        this.paras = paras;
    }

    @Override
    public ArrayList<Operand> getDestOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        if (dest != null) operands.add(dest);
        if (!function.name.startsWith("__")) {
            for (VardecStmt vardecStmt: Environment.globalvars) {
                if (vardecStmt.symbol.register != null)
                    operands.add(vardecStmt.symbol.register);
            }
        }
        return operands;
    }

    @Override
    public ArrayList<Operand> getSrcOps() {
        ArrayList<Operand> operands = new ArrayList<>();
        operands.addAll(paras);
        if (!function.name.startsWith("__")) {
            for (VardecStmt vardecStmt: Environment.globalvars) {
                if (vardecStmt.symbol.register != null)
                    operands.add(vardecStmt.symbol.register);
            }
        }
        return operands;
    }
}
