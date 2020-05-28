package Mxcomplier.Ast.Type;

public class IntType extends Type {
    private static IntType intType = new IntType();
    public static Type getType() {
        return intType;
    }
    @Override
    public Boolean equalTo(Type Other) {
        return Other instanceof IntType;
    }
}
