package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class ForNode extends StmtNode {
    private final StmtNode inicializacao; // ex: int i = 0;
    private final ExprNode condicao;      // ex: i < 10
    private final StmtNode incremento;    // ex: i = i + 1
    private final StmtNode corpo;

    public ForNode(StmtNode inicializacao, ExprNode condicao, StmtNode incremento, StmtNode corpo) {
        this.inicializacao = inicializacao;
        this.condicao = condicao;
        this.incremento = incremento;
        this.corpo = corpo;
    }
}
