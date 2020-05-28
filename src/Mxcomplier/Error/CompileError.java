package Mxcomplier.Error;

import Mxcomplier.Ast.BaseListener;

public class CompileError extends Error{
    public CompileError(String errorMessage) {
        super("CompileError:" + BaseListener.row + ":" + BaseListener.column + ":" + errorMessage);
    }
}
