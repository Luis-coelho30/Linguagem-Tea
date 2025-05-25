package analise_sintatica.node_type.stmt_type;

import analise_lexica.TeaToken;
import analise_sintatica.node_type.expr_type.ExprNode;

public class DeclNode extends StmtNode {
    private final String nome;
    private final TeaToken tipo;
    private final ExprNode inicializador;

    public DeclNode(String nome, TeaToken tipo, ExprNode inicializador, int linha, int coluna) {
        super(linha, coluna);
        this.nome = nome;
        this.tipo = tipo;
        this.inicializador = inicializador;
    }
}
