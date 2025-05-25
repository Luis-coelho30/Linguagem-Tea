package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class DoWhileNode extends StmtNode{
    private final StmtNode corpo;
    private final ExprNode condicao;

    public DoWhileNode(StmtNode corpo, ExprNode condicao, int linha, int coluna) {
        super(linha, coluna);
        this.corpo = corpo;
        this.condicao = condicao;
    }

    public ExprNode getCondition() {
        return condicao;
    }
    public StmtNode getBody() {
        return corpo;
    }
}
