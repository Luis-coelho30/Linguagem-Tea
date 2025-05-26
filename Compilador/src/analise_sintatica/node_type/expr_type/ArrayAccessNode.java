package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;

public class ArrayAccessNode extends ExprNode {
    private final String nome;
    private final int indice;

    public ArrayAccessNode(String nome, int indice) {
        this.nome = nome;
        this.indice = indice;
    }

    @Override
    public Value avaliar() {
        return new Value(nome, TeaToken.IDENTIFICADOR);
    }
}
