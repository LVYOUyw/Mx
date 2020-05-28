package Mxcomplier.IR.Operand;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;

public class VirtualRegister extends Operand {
    public int id;
    public Symbol symbol;
    public int type; // 1 for global 2 for local 3 for parameter
    public VirtualRegister(Symbol symbol, int type) {
        this.id = Environment.registerTable.registers.size();
        this.symbol = symbol;
        this.type = type;
    }
}
