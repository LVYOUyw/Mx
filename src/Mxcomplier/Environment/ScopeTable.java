package Mxcomplier.Environment;

import Mxcomplier.Ast.Statement.ForStmt;
import Mxcomplier.Ast.Statement.LoopStmt;
import Mxcomplier.Ast.Statement.WhileStmt;
import Mxcomplier.Ast.Type.ClassType;
import Mxcomplier.Ast.Type.Function;
import Mxcomplier.Error.RuntimeError;

import java.util.Stack;

public class ScopeTable {
    private Stack<Scope> scopes;
    //private Stack<Integer> flag; //1 for class; 2 for function; 3 for blockstatement
    public Scope curfunction, curclass;
    public Stack<Scope> loopscopes;

    public ScopeTable() {
        scopes = new Stack<>();
        curfunction = null;
        curclass = null;
        loopscopes = new Stack<>();
    }

    public Scope top() {
        if (scopes.empty()) return null;
        return scopes.peek();
    }

    public void enterScope(Scope scope) {
        scopes.push(scope);
        if (scope instanceof ClassType)
            curclass = scope;
        else if (scope instanceof Function)
            curfunction = scope;
        else if (scope instanceof LoopStmt) {
            loopscopes.push(scope);

        }

    }

    public void exitScope() {
        if (scopes.empty()) throw new RuntimeError("wrong scope");
        Scope scope = scopes.pop();
        if (scope instanceof ClassType)
            curclass = null;
        else if (scope instanceof Function)
            curfunction = null;
        else if (scope instanceof LoopStmt) {
            loopscopes.pop();
        }
    }
}
