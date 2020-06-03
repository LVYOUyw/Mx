package Mxcomplier.IR;

import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashMap;

public class Optimize {
    public HashMap<IRinst, VirtualRegister> F;
    public HashMap<VirtualRegister, ArrayList<IRinst>> V;
    public HashMap<Address, VirtualRegister> M;

    public Optimize() {
        F = new HashMap<>();
        V = new HashMap<>();
        M = new HashMap<>();
    }

    public void addBinary(IRinst iRinst, VirtualRegister virtualRegister) {
        F.put(iRinst, virtualRegister);
        if (iRinst.src1 instanceof VirtualRegister) {
            VirtualRegister tmp = (VirtualRegister)iRinst.src1;
            if (!V.containsKey(tmp))
                V.put(tmp, new ArrayList<>());
            V.get(tmp).add(iRinst);
        }
        if (iRinst.src2 instanceof VirtualRegister) {
            VirtualRegister tmp = (VirtualRegister)iRinst.src2;
            if (!V.containsKey(tmp))
                V.put(tmp, new ArrayList<>());
            V.get(tmp).add(iRinst);
        }
    }

    public void addMem(Address address, VirtualRegister virtualRegister) {
        M.put(address, virtualRegister);
    }

    public VirtualRegister getBinary(IRinst iRinst) {
        if (F.containsKey(iRinst))
            return F.get(iRinst);
        return null;
    }

    public VirtualRegister getMem(Address address) {
        if (M.containsKey(address))
            return M.get(address);
        return null;
    }

    public void delete(VirtualRegister virtualRegister) {
        ArrayList<IRinst> t = V.get(virtualRegister);
        for (IRinst iRinst: t)
            F.remove(iRinst);
    }
}
