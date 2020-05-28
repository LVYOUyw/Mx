package Mxcomplier;
import Mxcomplier.Ast.ASTBuilder;
import Mxcomplier.Ast.ClassBuilder;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Ast.TypeBuilder;
import Mxcomplier.Error.CompileError;
import Mxcomplier.Error.SemanticError;
import Mxcomplier.IR.Graph;
import Mxcomplier.Paser.MxLexer;
import Mxcomplier.Paser.MxParser;
import Mxcomplier.Translator.Better_Translator;
import Mxcomplier.Translator.Naive_Translator;
import Mxcomplier.Translator.RegisterAllocate;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //InputStream is = new FileInputStream("D:\\Complie homework\\Compiler-2020-local-judge\\testcase\\Compiler-2020-testcases\\sema\\loop-package\\loop-1.mx");
        InputStream is = new FileInputStream("C:\\Users\\18617\\IdeaProjects\\Mx\\src\\1.txt");
        //InputStream is = System.in;
        Environment.init();
        CharStream input = CharStreams.fromStream(is);
        MxLexer lexer = new MxLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new SemanticError());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxParser parser = new MxParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new SemanticError());
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ClassBuilder(), tree);
        walker.walk(new TypeBuilder(), tree);
        walker.walk(new ASTBuilder(), tree);
        if (!Environment.main)
            throw new CompileError("No main");
        for (Function function: Environment.functions) {
            function.graph = new Graph(function);
            function.allocate = new RegisterAllocate(function);
        }
        OutputStream os = new FileOutputStream("test.s");
        new Better_Translator(new PrintStream(os)).translate();
    }
}