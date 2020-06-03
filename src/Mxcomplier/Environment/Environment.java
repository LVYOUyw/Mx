package Mxcomplier.Environment;

import Mxcomplier.Ast.Statement.VardecStmt;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Operand.PhysicalRegister;
import Mxcomplier.IR.Operand.VirtualRegister;
import Mxcomplier.IR.Optimize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Environment {
    public static ScopeTable scopeTable;
    public static SymbolTable symbolTable;
    public static HashMap<String, Symbol> classTable;
    public static RegisterTable registerTable;
    public static boolean main;
    public static HashMap<String, VirtualRegister> debug;
    public static List<Function> functions;
    public static List<VardecStmt> globalvars;
    public static int exprid;
    public static ArrayList<PhysicalRegister> unused;

    public static void init() {
        scopeTable = new ScopeTable();
        symbolTable = new SymbolTable();
        classTable = new HashMap<>();
        main = false;
        symbolTable.enterScope();
        debug = new HashMap<>();
        functions = new ArrayList<>();
        globalvars = new ArrayList<>();
        registerTable = new RegisterTable();
        exprid = 0;
        unused = new ArrayList<>();
        unused.addAll(PhysicalRegister.registers);
        Environment.buildfunction();
    }

    public static int getExprid() {
        exprid = exprid + 1;
        return exprid;
    }

    public static void addclass(String name, Type type) {
        if (classTable.containsKey(name))
            throw new CompileError("repeat class name");
        classTable.put(name, new Symbol(name, type));
    }

    public static void buildfunction() {
        symbolTable.add("getString", Function.getfunction("__getString", StringType.getType(),
                        new ArrayList<Symbol>(){
                        }),0);
        symbolTable.add("getInt", Function.getfunction("__getInt", IntType.getType(),
                        new ArrayList<Symbol>(){
                        }),0);
        symbolTable.add("printInt", Function.getfunction("__printInt", VoidType.getType(),
                        new ArrayList<Symbol>(){
                        {
                            add(new Symbol("1", IntType.getType()));
                        }
                        }),0);
        symbolTable.add("printlnInt", Function.getfunction("__printlnInt", VoidType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", IntType.getType()));
                            }
                        }),0);
        symbolTable.add("toString", Function.getfunction("__toString", StringType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", IntType.getType()));
                            }
                        }),0);
        symbolTable.add("print", Function.getfunction("__print", VoidType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("println", Function.getfunction("__println", VoidType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string_ord", Function.getfunction("__stringOrd", IntType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                                add(new Symbol("2", IntType.getType()));
                            }
                        }),0);
        symbolTable.add("string_len", Function.getfunction("__stringLength", IntType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string_parse", Function.getfunction("__stringParseInt",IntType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string+", Function.getfunction("__stringAdd", StringType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                                add(new Symbol("2", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string<", Function.getfunction("__stringLess", BoolType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                                add(new Symbol("2", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string<=", Function.getfunction("__stringLessEqual", BoolType.getType(),
                new ArrayList<Symbol>(){
                    {
                        add(new Symbol("1", StringType.getType()));
                        add(new Symbol("2", StringType.getType()));
                    }
                }),0);
        symbolTable.add("string>=", Function.getfunction("__stringGreaterEqual", BoolType.getType(),
                new ArrayList<Symbol>(){
                    {
                        add(new Symbol("1", StringType.getType()));
                        add(new Symbol("2", StringType.getType()));
                    }
                }),0);
        symbolTable.add("string>", Function.getfunction("__stringGreater", BoolType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                                add(new Symbol("2", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string=", Function.getfunction("__stringEqual", BoolType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                                add(new Symbol("2", StringType.getType()));
                            }
                        }),0);
        symbolTable.add("string!=", Function.getfunction("__stringNotEqual", BoolType.getType(),
                new ArrayList<Symbol>(){
                    {
                        add(new Symbol("1", StringType.getType()));
                        add(new Symbol("2", StringType.getType()));
                    }
                }),0);
        symbolTable.add("string[]", Function.getfunction("__stringSubstring", StringType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", StringType.getType()));
                                add(new Symbol("2", IntType.getType()));
                                add(new Symbol("3", IntType.getType()));
                            }
                        }),0);
        symbolTable.add("array_size", Function.getfunction("__arraySize", IntType.getType(),
                        new ArrayList<Symbol>(){
                            {
                                add(new Symbol("1", NullType.getType()));
                            }
                        }),0);
    }

    public static void enterScope(Scope scope) {
        scopeTable.enterScope(scope);
        symbolTable.enterScope();
    }

    public static void exitScope() {
        scopeTable.exitScope();
        symbolTable.exitScope();
    }
}
