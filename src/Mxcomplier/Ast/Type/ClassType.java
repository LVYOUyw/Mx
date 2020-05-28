package Mxcomplier.Ast.Type;
import Mxcomplier.Environment.Scope;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.Error.CompileError;

import java.util.HashMap;
import java.util.Map;

public class ClassType extends Type implements Scope {
    public String name;
    public Map<String, Symbol> vartable;
    public Map<String, Integer> memoffset;
    public Map<String, Function> functable;
    public Function constructor;
    public int size;

    private ClassType(String name) {
        this.name = name;
        vartable = new HashMap<>();
        functable = new HashMap<>();
        memoffset = new HashMap<>();
        constructor = null;
        this.size = 0;
    }

    public static Type getType(String name) {
        return new ClassType(name);
    }

    public void add(String name, Type type) {
        if (vartable.containsKey(name) || functable.containsKey(name))
            throw new CompileError("repeat name in one class");
        if (type instanceof Function) {
            Function function = (Function) type;
            function.name = this.name + '.' + function.name;
            function.parameters.add(0, new Symbol("this", this));
            functable.put(function.name, function);
            if (!(function.type instanceof VoidType))
                function.isleftvalue = true;
            //System.out.println("class " + this.name + " add function " + name);
        }
        else {
         //   System.out.println("class " + this.name + " add " + name);
            Symbol symbol = new Symbol(name, type);
           // System.out.println(type instanceof ClassType);
            vartable.put(name, symbol);
            memoffset.put(name, size);
            this.size += 4;
        }
    }

    public void addconstructor(Function function) {
        if (constructor != null)
            throw new CompileError("two constructors in one class");
        if (!function.name.equals(this.name))
            throw new CompileError("constructor name should be same to class name");
        constructor = function;
    }

    public Function getfunc(String name) {
        if (functable.containsKey(name))
            return functable.get(name);
        return null;
    }

    public Symbol getvar(String name) {
        if (vartable.containsKey(name))
            return vartable.get(name);
        return null;
    }

    public int getoffset(String name) {
        return memoffset.get(name);
    }

    @Override
    public Boolean equalTo(Type other) {
        if (other instanceof ClassType)
            return ((ClassType) other).name.equals(this.name);
        return false;
    }
}
