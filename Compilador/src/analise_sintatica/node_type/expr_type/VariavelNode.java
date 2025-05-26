package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;

public class VariavelNode extends ExprNode {
    private final String nome;

    public VariavelNode(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Value avaliar() {
        return new Value(nome, TeaToken.IDENTIFICADOR);
    }

    @Override
    public String toString() {
        return "Var(" + nome + ")";
    }
}
