package Mxcomplier.Environment;

import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Error.CompileError;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {
    private HashMap<String, Stack<Symbol>> nameStack;
    private Stack<HashMap<String, Symbol>> symboltable;

    public SymbolTable() {
        nameStack = new HashMap<>();
        symboltable = new Stack<>();
    }

    public Symbol add(String name, Type type, int op) {
        if (symboltable.peek().containsKey(name))
            throw new CompileError("repeat name: " + name);
        if (!nameStack.containsKey(name)) nameStack.put(name, new Stack<>());
        Symbol symbol = new Symbol(name, type);
        nameStack.get(name).push(symbol);
        symboltable.peek().put(name,symbol);
        if (op != 0)
            symbol.register = Environment.registerTable.add(symbol, op);
        return symbol;
    }

    public boolean containsName(String name) {
        return nameStack.containsKey(name) && !nameStack.get(name).empty();
    }

    public Symbol get(String name) {
        if (nameStack.get(name) == null) return null;
        return nameStack.get(name).peek();
    }

    public void enterScope() {
        symboltable.push(new HashMap<>());
    }

    public void exitScope() {
        symboltable.pop().forEach((key, value) -> nameStack.get(key).pop());
    }

}
