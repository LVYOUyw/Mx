package Mxcomplier.Error;

import Mxcomplier.Ast.BaseListener;

public class RuntimeError extends Error {
    public RuntimeError(String errorMessage) {
        super("RuntimeError:" + BaseListener.row + ":" + BaseListener.column + ":" + errorMessage);
    }
}
