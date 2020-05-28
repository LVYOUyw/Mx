package Mxcomplier.Ast;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.Ast.Expression.BinaryExpr.*;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Expression.UnaryExpr.*;
import Mxcomplier.Ast.Statement.*;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Error.CompileError;
import Mxcomplier.Paser.MxParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends BaseListener {

    @Override
    public void enterFunctiondec(MxParser.FunctiondecContext ctx) {
        Function function = (Function) Tree.get(ctx);
        Environment.enterScope(function);
       // System.out.println(function.name);
        for (int i = 0; i < function.parameters.size(); i++) {
            Environment.symbolTable.add(function.parameters.get(i).name, function.parameters.get(i).type, 3);
           // System.out.println("Add " + function.parameters.get(i).name);
        }
    }

    @Override
    public void exitFunctiondec(MxParser.FunctiondecContext ctx) {
        Function function = (Function) Tree.get(ctx);
        function.addblock((BlockStmt)Tree.get(ctx.block()));
        Environment.exitScope();
        if (function.name.equals("main"))
            Environment.main = true;
        Environment.functions.add(function);
    }

    @Override
    public void enterClassdec(MxParser.ClassdecContext ctx) {
        ClassType classType = (ClassType) Tree.get(ctx);
        Environment.enterScope(classType);
        //Environment.symbolTable.add("this", classType);
        classType.vartable.forEach((key, value) -> Environment.symbolTable.add(key, value.type, 0));
        classType.functable.forEach((key,value) ->
        {
            Environment.symbolTable.add(key.substring(classType.name.length() + 1), value, 0);
           // System.out.println(classType.name.length() + 1);
        });
        Tree.put(ctx, classType);
    }

    @Override
    public void exitClassdec(MxParser.ClassdecContext ctx) {
        Environment.exitScope();
    }


    @Override
    public void exitIfstmt(MxParser.IfstmtContext ctx) {
        Tree.put(ctx, IfStmt.getstmt(
                (Expression)Tree.get(ctx.expression()),
                (Statement)Tree.get(ctx.statement(0)),
                (Statement)Tree.get(ctx.statement(1)))
                );
    }

    @Override
    public void enterForstmt(MxParser.ForstmtContext ctx) {
        ForStmt forStmt = (ForStmt) ForStmt.getstmt();
        Environment.enterScope(forStmt);
        Tree.put(ctx, forStmt);
    }

    @Override
    public void exitForstmt(MxParser.ForstmtContext ctx) {
        Expression init = null;
        Expression condition = null;
        Expression inc = null;
        int cnt = 0;
        for (ParseTree parseTree : ctx.children) {
            if (parseTree.getText().equals(";"))
                cnt++;
            if (parseTree instanceof MxParser.ExpressionContext) {
                if (cnt == 0) init = (Expression) Tree.get(parseTree);
                else if (cnt == 1) condition = (Expression) Tree.get(parseTree);
                else if (cnt == 2) inc = (Expression) Tree.get(parseTree);
                else throw new CompileError("too many ';'");
            }
        }

        Statement st = (Statement) Tree.get(ctx.statement());
        Statement check = ForStmt.getstmt(init, condition, inc, st);
        //ystem.out.printf("exitfor %s\n", st);
        ForStmt forStmt =  (ForStmt)Tree.get(ctx);
        forStmt.init = init;
        forStmt.condition = condition;
        forStmt.inc = inc;
        forStmt.stmt = st;
        Tree.put(ctx, forStmt);
        Environment.exitScope();
    }

    @Override
    public void enterWhilestmt(MxParser.WhilestmtContext ctx) {
        WhileStmt whileStmt = (WhileStmt) WhileStmt.getstmt();
        Tree.put(ctx, whileStmt);
        Environment.enterScope(whileStmt);
    }

    @Override
    public void exitWhilestmt(MxParser.WhilestmtContext ctx) {
        WhileStmt whileStmt = (WhileStmt) Tree.get(ctx);
        whileStmt.condition = (Expression)Tree.get(ctx.expression());
        whileStmt.stmt = (Statement)Tree.get(ctx.statement());
        Environment.exitScope();
    }

    @Override
    public void exitContinuestmt(MxParser.ContinuestmtContext ctx) {
        Tree.put(ctx, ContinueStmt.getstmt());
    }

    @Override
    public void exitBreakstmt(MxParser.BreakstmtContext ctx) {
        Tree.put(ctx, BreakStmt.getstmt());
    }

    @Override
    public void exitRetstmt(MxParser.RetstmtContext ctx) {
        Expression expr;
        if (ctx.expression() == null)
            expr = NullExpr.getnull();
        else
            expr = (Expression) Tree.get(ctx.expression());
        Tree.put(ctx, ReturnStmt.getstmt(expr));
    }

    @Override
    public void exitExprstmt(MxParser.ExprstmtContext ctx) {
        Tree.put(ctx, ExprStmt.getstmt((Expression) Tree.get(ctx.expression())));
    }

    @Override
    public void enterVarstmt(MxParser.VarstmtContext ctx) {
        if (ctx.parent instanceof MxParser.IfstmtContext)
            Environment.enterScope(null);
    }

    @Override
    public void exitVarstmt(MxParser.VarstmtContext ctx) {
        if (ctx.parent instanceof MxParser.IfstmtContext)
            Environment.exitScope();
        Tree.put(ctx, (Statement)Tree.get(ctx.variabledec()));
    }

    @Override
    public void exitVardec1(MxParser.Vardec1Context ctx) {
        if (!(ctx.parent instanceof MxParser.ClassdecContext)) {
            Type type = (Type) Tree.get(ctx.type());
            if (type instanceof VoidType)
                throw new CompileError("Void-type variable");
            for (int i = 0; i < ctx.Identifier().size(); i++) {
                String name = ctx.Identifier(i).getText();
                Symbol symbol = Environment.symbolTable.add(name, type,2);
                if (ctx.parent instanceof MxParser.ProgramContext) {
                    Statement statement = VardecStmt.getstmt(symbol, NullExpr.getnull());
                    Environment.globalvars.add((VardecStmt) statement);
                    symbol.register.type = 1;
                }
            }
        }
    }

    @Override
    public void exitVardec2(MxParser.Vardec2Context ctx) {
        if (!(ctx.parent instanceof MxParser.ClassdecContext)) {
            Type type = (Type) Tree.get(ctx.type());
            Expression expression = (Expression) Tree.get(ctx.expression());
            if (type instanceof VoidType)
                throw new CompileError("Void-type variable");
            if (!type.equalTo(expression.type) && !(expression.type instanceof NullType))
                throw new CompileError("Wrong vardection");
            String name = ctx.Identifier().getText();
            Symbol symbol = Environment.symbolTable.add(name, type, 2);
            Statement statement = VardecStmt.getstmt(symbol, (Expression) Tree.get(ctx.expression()));
            Tree.put(ctx, statement);
            if (ctx.parent instanceof MxParser.ProgramContext) {
                Environment.globalvars.add((VardecStmt) statement);
                symbol.register.type = 1;
            }
        }
        else {
            ClassType classType = (ClassType) Tree.get(ctx.parent);
            Type type = (Type) Tree.get(ctx.type());
            Expression expression = (Expression) Tree.get(ctx.expression());
            if (type instanceof VoidType)
                throw new CompileError("Void-type variable");
            if (!type.equalTo(expression.type))
                throw new CompileError("Wrong vardection");
            String name = ctx.Identifier().getText();
            Symbol symbol = new Symbol(name, type);
            Tree.put(ctx, VardecStmt.getstmt(symbol, (Expression) Tree.get(ctx.expression())));
        }
    }

    @Override
    public void exitBlockstmt(MxParser.BlockstmtContext ctx) {
        Tree.put(ctx, Tree.get(ctx.block()));
    }

    @Override
    public void enterBlock(MxParser.BlockContext ctx) {
        BlockStmt blockStmt = (BlockStmt) BlockStmt.getstmt();
        Environment.enterScope(blockStmt);
        if ((Tree.get(ctx.parent) instanceof Function)) {
           Function function = (Function) Tree.get(ctx.parent);
           for (int i = 0; i < function.parameters.size(); i++) {
               Symbol tmp = function.parameters.get(i);
               function.parameters.set(i, Environment.symbolTable.add(tmp.name, tmp.type, 3));
           }
        }
    }

    @Override
    public void exitBlock(MxParser.BlockContext ctx) {
        BlockStmt blockStmt = (BlockStmt) BlockStmt.getstmt();
        for (int i = 0; i < ctx.statement().size(); i++)
            blockStmt.addstmt((Statement)Tree.get(ctx.statement(i)));
        Tree.put(ctx, blockStmt);
        Environment.exitScope();
    }

    @Override
    public void exitConstructexpr(MxParser.ConstructexprContext ctx) {
        String name = ctx.Identifier().getText();
        if (Environment.classTable.containsKey(name)) {
            Symbol symbol = Environment.classTable.get(name);
            if (symbol.type instanceof ClassType && symbol.name.equals(((ClassType) symbol.type).name))
                Tree.put(ctx, ConstructExpr.getExpression(symbol.type, true));
            else
                throw new CompileError("it is not a class type");
        }
        else throw new CompileError("you don't have "+ name + "class");
    }

    @Override
    public void exitFunctionexpr(MxParser.FunctionexprContext ctx) {
        Expression expr = (Expression) Tree.get(ctx.expression(0));
        List<Expression> para = new ArrayList<>();
        for (int i = 1; i < ctx.expression().size(); i++)
            para.add((Expression) Tree.get(ctx.expression(i)));
        Tree.put(ctx, FunctionExpr.getExpression(expr, para));
    }

    public void exitPrefixexpr(MxParser.PrefixexprContext ctx) {
        Expression expr = (Expression) Tree.get(ctx.expression());
        String op = ctx.op.getText();
        switch (op) {
            case "+": Tree.put(ctx, SignExpr.getExpression(expr, false));break;
            case "-": Tree.put(ctx, SignExpr.getExpression(expr, true));break;
            case "++": Tree.put(ctx, SelfExpr.getExpression(expr, false, false));break;
            case "--": Tree.put(ctx, SelfExpr.getExpression(expr, true, false));break;
            case "!": Tree.put(ctx, NotExpr.getExpression(expr, false));break;
            case "~": Tree.put(ctx, NotExpr.getExpression(expr, true));break;
        }
    }

    @Override
    public void exitPostfixexpr(MxParser.PostfixexprContext ctx) {
        Expression expr = (Expression) Tree.get(ctx.expression());
        String op = ctx.op.getText();
        if (op.equals("++"))
            Tree.put(ctx, SelfExpr.getExpression(expr, false, true));
        else
            Tree.put(ctx, SelfExpr.getExpression(expr, true, true));
    }

    @Override
    public void exitDotexpr(MxParser.DotexprContext ctx) {
        Tree.put(ctx, dotExpr.getExpression((Expression)Tree.get(ctx.expression()), ctx.Identifier().getText()));
    }

    @Override
    public void exitNewexpr(MxParser.NewexprContext ctx) {
        List<Expression> dimension = new ArrayList<>();
        String last = "";
        boolean finish = false;
        for (ParseTree parseTree: ctx.children) {
            if (parseTree instanceof MxParser.ExpressionContext) {
                if (finish) throw new CompileError("wrong new array");
                Expression expression = (Expression) Tree.get(parseTree);
                if (!(expression.type instanceof IntType))
                    throw new CompileError("index must be int");
                dimension.add(expression);
            }
            else if (parseTree.getText().equals("]") && last.equals("[")) {
                finish = true;
                dimension.add(null);
            }
            last = parseTree.getText();
        }
        Type type = (Type)Tree.get(ctx.type());
        if (type == null) {
            String name = ctx.Identifier().getText();
            if (!Environment.classTable.containsKey(name))
                throw new CompileError("You don't have this class");
            type = Environment.classTable.get(name).type;
        }
        Tree.put(ctx, NewExpr.getExpression(type, dimension));
    }

    @Override
    public void exitIndexexpr(MxParser.IndexexprContext ctx) {
        Tree.put(ctx, IndexExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression) Tree.get(ctx.expression(1))));
    }

    @Override
    public void exitIdenexpr(MxParser.IdenexprContext ctx) {
        //if (ctx.parent instanceof MxParser.DotexprContext) return;
        Tree.put(ctx, IdentityExpr.getExpression(ctx.Identifier().getText()));
    }

    @Override
    public void exitMulexpr(MxParser.MulexprContext ctx) {
        Tree.put(ctx, ArithExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitAddexpr(MxParser.AddexprContext ctx) {
        Tree.put(ctx, ArithExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitShiftexpr(MxParser.ShiftexprContext ctx) {
        Tree.put(ctx, ArithExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitBitexpr(MxParser.BitexprContext ctx) {
        Tree.put(ctx, BitExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitCompexpr(MxParser.CompexprContext ctx) {
        Tree.put(ctx, CompareExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitLogicexpr(MxParser.LogicexprContext ctx) {
        Tree.put(ctx, LogicExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitEqualexpr(MxParser.EqualexprContext ctx) {
        Tree.put(ctx, EqualExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1)),ctx.op.getText()));
    }

    @Override
    public void exitAssignexpr(MxParser.AssignexprContext ctx) {
        Tree.put(ctx, AssignExpr.getExpression((Expression)Tree.get(ctx.expression(0)),(Expression)Tree.get(ctx.expression(1))));
    }

    @Override
    public void exitSubexpr(MxParser.SubexprContext ctx) {
        Tree.put(ctx, Tree.get(ctx.expression()));
    }

    @Override
    public void exitThisexpr(MxParser.ThisexprContext ctx) {
        Symbol symbol = Environment.symbolTable.get("this");
        if (symbol == null)
            throw new CompileError("this should be used in the class");
        Type type = symbol.type;
        Tree.put(ctx, ThisExpr.getExpression(type, true));
    }

    @Override
    public void exitIntexpr(MxParser.IntexprContext ctx) {
        int v = Integer.parseInt(ctx.getText());
        Tree.put(ctx, IntExpr.getint(v));
    }

    @Override
    public void exitStringexpr(MxParser.StringexprContext ctx) {
        String v = ctx.getText().substring(1, ctx.getText().length() - 1);
        Tree.put(ctx, StringExpr.getstring(v));
    }

    @Override
    public void exitBoolexpr(MxParser.BoolexprContext ctx) {
        boolean v = ctx.getText().equals("true");
        Tree.put(ctx, BoolExpr.getBool(v));
    }

    @Override
    public void exitNullexpr(MxParser.NullexprContext ctx) {
        Tree.put(ctx, NullExpr.getnull());
    }

}
