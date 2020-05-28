package Mxcomplier.Environment;

import Mxcomplier.IR.Operand.StringRegister;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RegisterTable {
    public Set<VirtualRegister> registers;
    public ArrayList<StringRegister> sregs;

    public RegisterTable() {
        registers = new HashSet<>();
        sregs = new ArrayList<>();
    }

    public VirtualRegister add(Symbol symbol, int type) {
        VirtualRegister virtualRegister = new VirtualRegister(symbol, type);
        registers.add(virtualRegister);
        return virtualRegister;
    }

    public VirtualRegister add(String s) {
        StringRegister virtualRegister = new StringRegister(s);
        registers.add(virtualRegister);
        sregs.add(virtualRegister);
        return virtualRegister;
    }
}
