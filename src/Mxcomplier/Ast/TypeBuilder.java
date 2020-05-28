package Mxcomplier.Ast;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Scope;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Error.CompileError;
import Mxcomplier.Paser.MxParser;

import java.util.ArrayList;
import java.util.List;

public class TypeBuilder extends BaseListener {

    @Override
    public void enterClassdec(MxParser.ClassdecContext ctx) {
        ClassType classType = (ClassType) Tree.get(ctx);
        Environment.enterScope(classType);
    }

    @Override
    public void exitClassdec(MxParser.ClassdecContext ctx) {
        Environment.exitScope();
    }

    @Override
    public void exitFunctiondec(MxParser.FunctiondecContext ctx) {

        ClassType classtype = (ClassType) Environment.scopeTable.curclass;
        Type returntype = (Type) Tree.get(ctx.type());
        List<Symbol> paras = new ArrayList<>();
        String name;
        if (ctx.Identifier() == null) {
            if (!(classtype instanceof ClassType))
                throw new CompileError("don't have a return type");
            if (!ctx.type().getText().equals(((ClassType) classtype).name))
                throw new CompileError("constructor name is different from class name");
            name = ((ClassType) classtype).name;
        }
        else name = ctx.Identifier().getText();
     //   System.out.println(name);
        MxParser.ParameterlistContext para = ctx.parameterlist();
        if (para != null) {
            Type type = (Type) Tree.get(para.type());
            String iden = para.Identifier().getText();
            paras.add(new Symbol(iden, type));
            //System.out.println("Add " + iden);
            while (para.parameterlist().size() > 0) {
                para = para.parameterlist(0);
                type = (Type) Tree.get(para.type());
                iden = para.Identifier().getText();
                paras.add(new Symbol(iden, type));
                //System.out.println("Add " + iden);
            }
        }
        Function function = Function.getfunction(name, returntype, paras);
        if (ctx.Identifier() == null) {
            function.type = VoidType.getType();
            function.parameters.add(0, new Symbol("this", classtype));
            classtype.addconstructor(function);
        }
        else if (classtype instanceof ClassType) {
            if (name.equals(((ClassType) classtype).name))
                throw new CompileError("wrong construct return type");
            ((ClassType) classtype).add(name, function);
        }

        Tree.put(ctx, function);
        Environment.symbolTable.add(function.name, function, 0);
    }

    @Override
    public void exitVardec1(MxParser.Vardec1Context ctx) {
        if (ctx.parent instanceof MxParser.ClassdecContext) {
            Type type = (Type) Tree.get(ctx.type());
            if (type instanceof VoidType)
                throw new CompileError("Void-type variable");
            ClassType classType = (ClassType) Tree.get(ctx.parent);
            for (int i = 0; i < ctx.Identifier().size(); i++) {
                String name = ctx.Identifier(i).getText();
                Symbol symbol = Environment.symbolTable.add(name, type, 0);
                classType.add(name, type);
            }
        }
    }

    @Override
    public void exitVardec2(MxParser.Vardec2Context ctx) {
        if (ctx.parent instanceof MxParser.ClassdecContext) {
            ClassType classType = (ClassType) Tree.get(ctx.parent);
            Type type = (Type) Tree.get(ctx.type());
            if (type instanceof VoidType)
                throw new CompileError("Void-type variable");
            String name = ctx.Identifier().getText();
            Symbol symbol = Environment.symbolTable.add(name, type, 0);
            classType.add(name, type);
        }
    }


    @Override
    public void exitInttype(MxParser.InttypeContext ctx) {
        Tree.put(ctx, IntType.getType());
    }

    @Override
    public void exitBooltype(MxParser.BooltypeContext ctx) {
        Tree.put(ctx, BoolType.getType());
    }

    @Override
    public void exitStringtype(MxParser.StringtypeContext ctx) {
        Tree.put(ctx, StringType.getType());
    }

    @Override
    public void exitVoidtype(MxParser.VoidtypeContext ctx) {
        Tree.put(ctx, VoidType.getType());
    }

    @Override
    public void exitArraytype(MxParser.ArraytypeContext ctx) {
        Type type = (Type)Tree.get(ctx.type());
        Tree.put(ctx, ArrayType.getType(type));
    }

    @Override
    public void exitClasstype(MxParser.ClasstypeContext ctx) {
        String name = ctx.Identifier().getText();
        if (!Environment.classTable.containsKey(name))
            throw new CompileError("don't have class " + name);
        ClassType classType = (ClassType) Environment.classTable.get(name).type;
        Tree.put(ctx, classType);
    }
}
