package Mxcomplier.Ast.Type;

public class VoidType extends Type {
    private static VoidType voidType = new VoidType();
    public static Type getType() {
        return voidType;
    }
    @Override
    public Boolean equalTo(Type Other) {
        return false;
    }
}
