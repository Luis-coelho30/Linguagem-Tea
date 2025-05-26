package analise_sintatica.node_type.stmt_type;

import analise_lexica.TeaToken;
import analise_sintatica.node_type.expr_type.ExprNode;

public class DeclNode extends StmtNode {
    private final String nome;
    private final String tipo;
    private final ExprNode inicializador;

    public DeclNode(String nome, String tipo, ExprNode inicializador) {
        this.nome = nome;
        this.tipo = tipo;
        this.inicializador = inicializador;
    }
}
