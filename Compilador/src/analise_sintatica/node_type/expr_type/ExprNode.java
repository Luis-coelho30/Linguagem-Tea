package analise_sintatica.node_type.expr_type;

import analise_sintatica.ASTNode;

public abstract class ExprNode extends ASTNode {
    public abstract Value avaliar();
}
