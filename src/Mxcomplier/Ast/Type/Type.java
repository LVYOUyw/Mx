package Mxcomplier.Ast.Type;

import Mxcomplier.Ast.Node;

public abstract class Type implements Node {

    public abstract Boolean equalTo(Type other);
}
