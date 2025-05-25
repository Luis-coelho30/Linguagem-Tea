package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class IfNode extends StmtNode {
    private final ExprNode condicao;
    private final StmtNode parteElif;
    private final StmtNode parteElse;

    public IfNode(ExprNode condicao, StmtNode parteElif, StmtNode parteElse, int linha, int coluna) {
        super(linha, coluna);
        this.condicao = condicao;
        this.parteElif = parteElif;
        this.parteElse = parteElse;
    }

    public ExprNode getCondition() {
        return condicao;
    }
    public StmtNode getThenBranch() {
        return parteElif;
    }
    public StmtNode getElseBranch() {
        return parteElse;
    }
}
