package Mxcomplier.Ast.Type;

import Mxcomplier.Error.CompileError;

public class ArrayType extends Type {

    public Type type;
    public int cnt;

    public ArrayType(Type type, int cnt) {
        this.type = type;
        this.cnt = cnt;
    }

    public static Type getType(Type type, int cnt) {
        if (type instanceof VoidType)
            throw new CompileError("void-array");
        return new ArrayType(type, cnt);
    }

    public static Type getType(Type type) {
        if (type instanceof  VoidType)
            throw new CompileError("void-array");
        if (type instanceof ArrayType) {
            ArrayType array = (ArrayType) type;
            return new ArrayType(array.type, array.cnt + 1);
        }
        else
            return new ArrayType(type, 1);
    }

    public Type reduce() {
        if (cnt == 1) return type;
        return ArrayType.getType(type, cnt - 1);
    }

    @Override
    public Boolean equalTo(Type other) {
        if (other instanceof NullType) return true;
        if (other instanceof ArrayType) {
            ArrayType array = (ArrayType) other;
           // if (array.type == null) System.out.println("Null");
            return (array.type.equalTo(type) && array.cnt == cnt);
        }
        return false;
    }
}
