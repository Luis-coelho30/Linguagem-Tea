package analise_sintatica.node_type;

import analise_sintatica.ASTNode;
import analise_sintatica.node_type.stmt_type.BlockNode;

public class MainNode extends ASTNode {
    private final BlockNode corpo;

    public MainNode(BlockNode corpo, int linha, int coluna) {
        super(linha, coluna);
        this.corpo = corpo;
    }

    public BlockNode getCorpo() {
        return corpo;
    }
}
