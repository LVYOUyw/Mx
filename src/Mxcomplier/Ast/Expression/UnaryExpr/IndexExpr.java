package Mxcomplier.Ast.Expression.UnaryExpr;

import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.ArrayType;
import Mxcomplier.Ast.Type.IntType;
import Mxcomplier.Ast.Type.Type;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.BinaryInst;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.LoadInst;
import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.Immediate;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.util.ArrayList;

public class IndexExpr extends Expression {
    public Expression array, index;
    private IndexExpr(Type type, boolean isLeftValue, Expression array, Expression index) {
        super(type, isLeftValue);
        this.array = array;
        this.index = index;
    }

    public static Expression getExpression(Expression array, Expression index) {
        if (!(array.type instanceof ArrayType))
            throw new CompileError("you need a array type");
        if (!(index.type instanceof IntType))
            throw new CompileError("index should be int");
        ArrayType tmp = (ArrayType) array.type;
        return new IndexExpr(tmp.reduce(), array.LeftValue, array, index);
    }

    public void emit(ArrayList<Instruction> instructions) {
        array.emit(instructions);
        array.load(instructions);
        index.emit(instructions);
        index.load(instructions);
        VirtualRegister address = Environment.registerTable.add(null, 2);
        VirtualRegister offset = Environment.registerTable.add(null, 2);
        instructions.add(new BinaryInst(offset, index.operand, new Immediate(4), "*"));
        instructions.add(new BinaryInst(address, array.operand, offset, "+"));
        operand = new Address(address, 4);
    }

    @Override
    public void load(ArrayList<Instruction> instructions) {
        if (operand instanceof Address) {
            Address address = (Address)operand;
            operand = Environment.registerTable.add(null, 2);
            instructions.add(new LoadInst((VirtualRegister) operand, address));
        }
    }
}
