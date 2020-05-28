package Mxcomplier.Ast;

import Mxcomplier.Environment.Environment;
import Mxcomplier.Ast.Type.ClassType;
import Mxcomplier.Paser.MxParser;

public class ClassBuilder extends BaseListener {
    @Override
    public void enterClassdec(MxParser.ClassdecContext ctx) {
        ClassType classType = (ClassType) ClassType.getType(ctx.Identifier().getText());
        Environment.enterScope(classType);
        classType.add("this", classType);
        Tree.put(ctx, classType);
    }

    @Override
    public void exitClassdec(MxParser.ClassdecContext ctx) {
        ClassType classType = (ClassType) Tree.get(ctx);
        Environment.exitScope();
        Environment.addclass(classType.name, classType);
    }

}
