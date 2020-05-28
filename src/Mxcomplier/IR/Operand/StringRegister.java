package Mxcomplier.IR.Operand;

public class StringRegister extends VirtualRegister {

    public String s;

    public StringRegister(String string) {
        super(null, 0);
        s = string;
    }

    public String address() {
        return String.format("__string_%d", this.id);
    }
}
