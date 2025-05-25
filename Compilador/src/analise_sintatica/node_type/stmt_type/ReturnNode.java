package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class ReturnNode extends StmtNode {
    private final ExprNode expression; // pode ser null para return vazio

    public ReturnNode(ExprNode expression, int linha, int coluna) {
        super(linha, coluna);
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }
}
