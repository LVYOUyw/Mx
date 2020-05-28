package Mxcomplier.Ast.Type;

public class BoolType extends Type{
    private static BoolType boolType = new BoolType();
    public static Type getType() {
        return boolType;
    }
    @Override
    public Boolean equalTo(Type Other) {
        return Other instanceof BoolType;
    }
}
