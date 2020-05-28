package Mxcomplier.Environment;

import Mxcomplier.Ast.Type.Type;
import Mxcomplier.IR.Operand.VirtualRegister;

public class Symbol {
    public String name;
    public Type type;
    public Scope scope;
    public VirtualRegister register;

    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
        this.scope = Environment.scopeTable.top();
        this.register = null;
    }
}
