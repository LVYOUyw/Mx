package Mxcomplier.Error;

import Mxcomplier.Ast.BaseListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SemanticError extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object symbol, int row, int column, String message, RecognitionException e){
        BaseListener.row = row;
        BaseListener.column = column;
        throw new CompileError(message);
    }
}
