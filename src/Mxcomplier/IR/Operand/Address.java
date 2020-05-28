package Mxcomplier.IR.Operand;

public class Address extends Operand {
    public VirtualRegister base;
    public Immediate index;
    public int scale;

    public Address(VirtualRegister base, Immediate index, int scale) {
        this.base = base;
        this.index = index;
        this.scale = scale;
    }

    public Address(VirtualRegister base, int scale) {
        this.base = base;
        this.index = new Immediate(0);
        this.scale = scale;
    }
}
