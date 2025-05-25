package analise_sintatica.node_type.expr_type;

import analise_sintatica.ASTNode;

public abstract class ExprNode extends ASTNode {

    public ExprNode(int linha, int coluna) {
        super(linha, coluna);
    }

    public abstract Value avaliar();
}
