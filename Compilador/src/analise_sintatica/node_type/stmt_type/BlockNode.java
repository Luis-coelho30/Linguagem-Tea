package analise_sintatica.node_type.stmt_type;

import java.util.List;

public class BlockNode extends StmtNode {
    private final List<StmtNode> statements;

    public BlockNode(List<StmtNode> statements) {
        this.statements = statements;
    }

    public List<StmtNode> getStatements() {
        return statements;
    }
}
