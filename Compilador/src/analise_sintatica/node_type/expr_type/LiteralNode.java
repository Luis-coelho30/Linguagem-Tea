package analise_sintatica.node_type.expr_type;

public class LiteralNode extends ExprNode {
    private final Value valor;

    public LiteralNode(Value valor) {
        this.valor = valor;
    }

    @Override
    public Value avaliar() {
        return valor;
    }

    @Override
    public String toString() {
        return "Literal(" + valor + ")";
    }
}
