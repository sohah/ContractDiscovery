package ast.Passes;

import ast.def.*;
import ast.parser.SMTLIBv2BaseVisitor;
import ast.parser.SMTLIBv2Parser;

import java.util.HashMap;
import java.util.HashSet;

import static ast.def.Operator.OperatorKind.EQ;


public class ContextRecoveryVisitor extends SMTLIBv2BaseVisitor {
    public static HashSet<Var> functionContext = new HashSet<>();


    @Override
    public Object visitSorted_var(SMTLIBv2Parser.Sorted_varContext ctx) {
        assert (ctx.getChildCount() == 4);
        String varName = ctx.getChild(1).getText();
        String typeName = ctx.getChild(2).getText();

        if(typeName.equals("Bool"))
            functionContext.add(new IntVar(varName));
        else if (typeName.equals("Int"))
            functionContext.add(new IntVar(varName));
        else if (typeName.equals("Float"))
            functionContext.add(new IntVar(varName));
        return null;
    }

}
