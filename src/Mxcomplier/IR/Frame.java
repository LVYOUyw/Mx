package Mxcomplier.IR;

import Mxcomplier.Error.RuntimeError;
import Mxcomplier.IR.Operand.Operand;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.HashMap;

public class Frame {
    public int size;
    public HashMap<VirtualRegister, Integer> paras, temp;

    public Frame() {
        size = 0;
        temp = new HashMap<>();
        paras = new HashMap<>();
    }

    public Integer getOffset(Operand operand) {
        if (temp.containsKey(operand))
            return temp.get(operand);
        if (paras.containsKey(operand))
            return paras.get(operand);
     //   throw new RuntimeError("RE");
        return -1;
    }
}
