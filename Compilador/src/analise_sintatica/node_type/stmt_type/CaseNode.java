package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class CaseNode {
    private final ExprNode valor;
    private final StmtNode corpo;

    public CaseNode(ExprNode valor, StmtNode corpo) {
        this.valor = valor;
        this.corpo = corpo;
    }

    public ExprNode getValor() {
        return valor;
    }

    public StmtNode getCorpo() {
        return corpo;
    }
}
