package analise_sintatica.node_type.stmt_type;

import analise_sintatica.ASTNode;

public abstract class StmtNode extends ASTNode {
    public StmtNode(int linha, int coluna) {
        super(linha, coluna);
    }
}
