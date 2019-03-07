package ast.Passes;

import ast.def.*;
import ast.visitors.AstVisitor;

import java.util.HashMap;

public class RemoveHolesVisitor  implements AstVisitor<Ast>{

    public static HashMap<Hole, Ast> instantiatedHoles = new HashMap<>();

    @Override
    public Ast visit(IntConst numExp) {
        return new IntConst(numExp.value);
    }

    @Override
    public Ast visit(Operator operation) {
        return new Operator(operation.opName);
    }

    @Override
    public Ast visit(IntVar intVar) {
        return new IntVar(intVar.name);
    }

    @Override
    public Ast visit(Hole hole) {
        return instantiatedHoles.get(hole);
    }

    @Override
    public Ast visit(BoolConst boolConst) {
        return boolConst;
    }

    @Override
    public Ast visit(BoolVar boolVar) {
        return new BoolVar(boolVar.name);
    }

    @Override
    public Ast visit(Var var) throws DiscoveryException {
        System.out.println("visiting a var super type, something went wrong.");
        assert false; // I shouldn't visit the super type
        return null;
    }

    @Override
    public Ast visit(NExp nExp) {
        return new NExp(nExp.operator,nExp.operands);
    }
}
