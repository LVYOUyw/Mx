package Mxcomplier.Ast.Expression.BinaryExpr;

import Mxcomplier.Ast.Expression.UnaryExpr.IdentityExpr;
import Mxcomplier.Ast.Expression.UnaryExpr.ThisExpr;
import Mxcomplier.Environment.Environment;
import Mxcomplier.Environment.Symbol;
import Mxcomplier.Ast.Expression.Expression;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Ast.Type.*;
import Mxcomplier.Error.CompileError;
import Mxcomplier.IR.Instruction.Instruction;
import Mxcomplier.IR.Instruction.LoadInst;
import Mxcomplier.IR.Instruction.MoveInst;
import Mxcomplier.IR.Instruction.StoreInst;
import Mxcomplier.IR.Operand.Address;
import Mxcomplier.IR.Operand.Immediate;
import Mxcomplier.IR.Operand.VirtualRegister;

import java.lang.reflect.Member;
import java.util.ArrayList;

public class dotExpr extends Expression {
    public Expression Class;
    public String member;

    private dotExpr(Type type, boolean isLeftValue, Expression Class, String member) {
        super(type, isLeftValue);
        this.member = member;
        this.Class = Class;
    }

    public static Expression getExpression(Expression Class, String member) {
        if (Class.type instanceof ClassType) {
            ClassType tmp = (ClassType)Class.type;
            Symbol var = tmp.getvar(member);
            Function func = tmp.getfunc(tmp.name + "." + member);
            if (var == null && func == null)
                throw new CompileError("this class don't have member " + member);
            if (var == null)
                return new dotExpr(func, Class.LeftValue, Class, member);
            return new dotExpr(var.type, Class.LeftValue, Class, member);
        }
        if (Class.type instanceof ArrayType) {
            if (member.equals("size"))
                return new dotExpr(Environment.symbolTable.get("array_size").type, Class.LeftValue, Class, member);
            throw new CompileError("no this array method");
        }
        if (Class.type instanceof StringType) {
            if (member.equals("length"))
                return new dotExpr(Environment.symbolTable.get("string_len").type, Class.LeftValue, Class, member);
            else if (member.equals("substring"))
                return new dotExpr(Environment.symbolTable.get("string[]").type, Class.LeftValue, Class, member);
            else if (member.equals("parseInt"))
                return new dotExpr(Environment.symbolTable.get("string_parse").type, Class.LeftValue, Class, member);
            else if (member.equals("ord"))
                return new dotExpr(Environment.symbolTable.get("string_ord").type, Class.LeftValue, Class, member);
            throw new CompileError("no this string method");
        }
        throw new CompileError("it is not a Class");
    }

    @Override
    public void emit(ArrayList<Instruction> instructions) {
        ClassType classType = (ClassType) Class.type;
        Symbol var = classType.getvar(member);
        if (var == null) return;
        int offset = classType.getoffset(member);
        Class.emit(instructions);
        Class.load(instructions);
        System.out.printf("%s\n", Class.operand);
        if (Class.operand instanceof Address) {
            VirtualRegister base = Environment.registerTable.add(null, 2);
            instructions.add(new LoadInst(base, (Address) Class.operand));
            operand = new Address(base, new Immediate(offset), 4);;
        }
        else if (Class.operand instanceof VirtualRegister){
            VirtualRegister base = (VirtualRegister) Class.operand;
            operand = new Address(base, new Immediate(offset), 4);;
        }
        else System.out.printf("%s\n", Class.operand);
    }

    @Override
    public void load(ArrayList<Instruction> instructions) {
        if (operand instanceof Address) {
            Address address = (Address)operand;
            operand = Environment.registerTable.add(null, 2);
            instructions.add(new LoadInst((VirtualRegister)operand, address));
        }
    }
}
