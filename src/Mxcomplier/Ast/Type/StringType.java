package Mxcomplier.Ast.Type;

public class StringType extends Type{
    private static StringType stringType = new StringType();
    public static Type getType() {
        return stringType;
    }
    @Override
    public Boolean equalTo(Type Other) {
        return Other instanceof StringType;
    }
}
