package Mxcomplier.Ast.Type;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.Ast.Node;
import Mxcomplier.Ast.Statement.BlockStmt;
import Mxcomplier.Environment.Scope;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Graph;
import Mxcomplier.IR.Instruction.LableInst;
import Mxcomplier.Translator.RegisterAllocate;
import Mxcomplier.Translator.RegisterGraph;

import java.util.List;

public class Function extends Type implements Node, Scope {
    public String name;
    public Type type;
    public List<Symbol> parameters;
    public BlockStmt stmts;
    public boolean isleftvalue;
    public LableInst begin, end, body;
    public Graph graph;
    public RegisterAllocate allocate;

    private Function(String name, Type type, List<Symbol> parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
        this.isleftvalue = false;
    }

    public static Function getfunction(String name, Type type, List<Symbol> parameters) {
        if (Environment.scopeTable.curclass == null) {
            if (Environment.symbolTable.containsName(name))
                throw new CompileError("repeating function name");
        }
        if (name.equals("main")) {
            if (!(type instanceof IntType))
                throw new CompileError("main should return inttype");
            if (parameters.size() > 0)
                throw new CompileError("main should not have parameters");
        }
        for (int i = 0; i < parameters.size(); i++)
            for (int j = i + 1; j < parameters.size(); j++)
                if (parameters.get(i).name.equals(parameters.get(j).name))
                    throw new CompileError("same name in parameters");
        return new Function(name, type, parameters);
    }

    public void addblock(BlockStmt b) {
        this.stmts = b;
    }

   @Override
    public Boolean equalTo(Type other) {
        return false;
    }
}
