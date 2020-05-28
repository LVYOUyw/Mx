package Mxcomplier.Ast.Type;

public class NullType extends Type{
    private static NullType nullType = new NullType();
    public static Type getType() {
        return nullType;
    }
    @Override
    public Boolean equalTo(Type Other) {
        return (Other instanceof NullType);
    }
}
