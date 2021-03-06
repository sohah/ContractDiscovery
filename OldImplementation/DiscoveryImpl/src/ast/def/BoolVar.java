package ast.def;

import ast.visitors.AstVisitor;

public class BoolVar extends Var {

    public BoolVar(String name) {
        super(name, VarType.BOOL);
    }

    @Override
    public Var clone() {
        return new BoolVar(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoolVar))
            return false;
        else
            return (this.name.equals(((BoolVar) obj).name) && (this.type.toString().equals(((BoolVar) obj).type.toString())));
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) throws DiscoveryException {
        return visitor.visit(this);
    }

    @Override
    public String toString(){
        return " "+ name ;
    }
}
