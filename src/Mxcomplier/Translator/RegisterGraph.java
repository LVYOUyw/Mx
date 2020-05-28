package Mxcomplier.Translator;

import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.HashMap;
import java.util.HashSet;

public class RegisterGraph {
    public HashSet<VirtualRegister> nodes;
    public HashMap<VirtualRegister, HashSet<VirtualRegister>> edge;

    public RegisterGraph() {
        nodes = new HashSet<>();
        edge = new HashMap<>();
    }

    public void addNode(VirtualRegister register) {
        nodes.add(register);
        edge.put(register, new HashSet<>());
    }

    public void connect(VirtualRegister x, VirtualRegister y) {
        if (x == y) return;
        if (x.type == 2 && y.type == 2) {
            edge.get(x).add(y);
            edge.get(y).add(x);
        }
    }
}
