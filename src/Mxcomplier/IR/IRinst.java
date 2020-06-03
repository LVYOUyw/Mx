package Mxcomplier.IR;

import Mxcomplier.IR.Operand.Operand;

public class IRinst {
    public Operand src1, src2;
    public String op;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        IRinst t = (IRinst)obj;
        if (src1 != t.src1 || src2 != t.src2 || !op.equals(t.op))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return src1.hashCode() + src2.hashCode() + op.hashCode();
    }
}
