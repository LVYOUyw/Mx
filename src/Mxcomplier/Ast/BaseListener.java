package Mxcomplier.Ast;

import Mxcomplier.Paser.MxBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class BaseListener extends MxBaseListener {
    public static int row, column;
    static ParseTreeProperty<Node> Tree = new ParseTreeProperty<>();

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        row = ctx.getStart().getLine();
        column = ctx.getStart().getCharPositionInLine();
    }
}
