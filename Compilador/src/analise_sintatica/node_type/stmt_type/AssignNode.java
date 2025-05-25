package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

public class AssignNode extends StmtNode {
    private final ExprNode variavel;
    private final ExprNode expressao;

    public AssignNode(ExprNode variavel, ExprNode expressao, int linha, int coluna) {
        super(linha, coluna);
        this.variavel = variavel;
        this.expressao = expressao;
    }

    public ExprNode getVariavel() {
        return variavel;
    }

    public ExprNode getExpressao() {
        return expressao;
    }
}
