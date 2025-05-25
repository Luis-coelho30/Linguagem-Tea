package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class WhileNode extends StmtNode {
    private final ExprNode condicao;
    private final StmtNode corpo;

    public WhileNode(ExprNode condicao, StmtNode corpo, int linha, int coluna) {
        super(linha, coluna);
        this.condicao = condicao;
        this.corpo = corpo;
    }

    public ExprNode getCondition() {
        return condicao;
    }
    public StmtNode getBody() {
        return corpo;
    }
}
