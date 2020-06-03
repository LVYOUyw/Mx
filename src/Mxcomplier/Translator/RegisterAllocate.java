package Mxcomplier.Translator;

import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Environment.Environment;
import Mxcomplier.IR.Block;
import Mxcomplier.IR.Instruction.BinaryInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Operand.PhysicalRegister;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegisterAllocate {
    public HashMap<VirtualRegister, PhysicalRegister> allocate;
    public HashSet<PhysicalRegister> usedcallee;
    public HashSet<PhysicalRegister> usedcaller;
    public HashMap<PhysicalRegister, Integer> cntcall;
    public Function function;
    public RegisterGraph G;
    public HashSet<VirtualRegister> visit;

    public RegisterAllocate(Function function) {
        this.function = function;
        allocate = new HashMap<>();
        G = new RegisterGraph();
        this.visit = new HashSet<>();
        usedcallee = new HashSet<>();
        usedcaller = new HashSet<>();
        cntcall = new HashMap<>();

        for (Block block: function.graph.blocks)
            for (Instruction inst: block.instructions) {
                for (VirtualRegister vir: inst.getDestRegisters())
                    G.addNode(vir);
                for (VirtualRegister vir: inst.getSrcRegisters())
                    G.addNode(vir);
            }

        for (Block block: function.graph.blocks) {
            HashSet<VirtualRegister> liveout = new HashSet<>();
            for (VirtualRegister vir: block.liveness.liveout)
                liveout.add(vir);

            for (int i = block.instructions.size() - 1; i >= 0; i--) {
                Instruction inst = block.instructions.get(i);
                if (inst instanceof BinaryInst) {
                    for (VirtualRegister out: liveout)
                        G.connect(((BinaryInst) inst).dest, out);
                    liveout.remove(((BinaryInst) inst).dest);
                    if (((BinaryInst) inst).src2 instanceof VirtualRegister)
                        liveout.add((VirtualRegister) ((BinaryInst) inst).src2);

                    for (VirtualRegister out: liveout)
                        G.connect(((BinaryInst) inst).dest, out);
                    liveout.remove(((BinaryInst) inst).dest);

                    if (((BinaryInst) inst).src1 instanceof VirtualRegister)
                        liveout.add((VirtualRegister) ((BinaryInst) inst).src1);
                }
                else {
                    for (VirtualRegister defined: inst.getDestRegisters())
                        for (VirtualRegister out: liveout)
                            G.connect(defined, out);
                    for (VirtualRegister defined: inst.getDestRegisters())
                        liveout.remove(defined);
                    for (VirtualRegister used: inst.getSrcRegisters())
                        liveout.add(used);
                }
            }
        }

        for (VirtualRegister vir: G.nodes) {
            if (!visit.contains(vir))
                dfs(vir);
        }
        if (!usedcaller.contains(PhysicalRegister.ra))
            usedcaller.add(PhysicalRegister.ra);
        for (PhysicalRegister py: usedcallee)
            cntcall.put(py, 1);
    }

    private void dfs(VirtualRegister x) {
        visit.add(x);
        coloring(x);
        for (VirtualRegister y: G.edge.get(x)) {
            if (!visit.contains(y)) {
                visit.add(y);
                dfs(y);
            }
        }
    }

    public void coloring(VirtualRegister x) {
        HashSet<PhysicalRegister> S = new HashSet<>();
        for (VirtualRegister y: G.edge.get(x))
            if (allocate.containsKey(y))
                S.add(allocate.get(y));
        S.add(null);
        for (PhysicalRegister phy: PhysicalRegister.registers) {
            if (!allocate.containsKey(x) && !S.contains(phy)) {
                allocate.put(x, phy);
                Environment.unused.remove(phy);
                if (PhysicalRegister.callee.contains(phy))
                    usedcallee.add(phy);
                if (PhysicalRegister.caller.contains(phy))
                    usedcaller.add(phy);
                break;
            }
        }
        if (!allocate.containsKey(x))
            allocate.put(x, null);
    }
}
