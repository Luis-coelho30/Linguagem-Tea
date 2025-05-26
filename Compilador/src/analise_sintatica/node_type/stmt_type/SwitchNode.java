package analise_sintatica.node_type.stmt_type;

import analise_sintatica.node_type.expr_type.ExprNode;

import java.util.List;

public class SwitchNode extends StmtNode{
    private final ExprNode expressao;
    private final List<CaseNode> casos;
    private final StmtNode padrao; // pode ser null

    public SwitchNode(ExprNode expressao, List<CaseNode> casos, StmtNode padrao) {
        this.expressao = expressao;
        this.casos = casos;
        this.padrao = padrao;
    }
}
