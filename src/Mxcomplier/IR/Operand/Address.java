package Mxcomplier.IR.Operand;

import Mxcomplier.IR.IRinst;

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

    @Override
    public int hashCode() {
        return base.hashCode() + ((Integer)index.value).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Address t = (Address)obj;
        if (base != t.base || index.value != t.index.value)
            return false;
        return true;
    }
}
