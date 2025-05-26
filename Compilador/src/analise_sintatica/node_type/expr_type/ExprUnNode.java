package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;

public class ExprUnNode extends ExprNode{
    private final String operador;
    private final ExprNode operando;

    public ExprUnNode(String operador, ExprNode operando) {
        this.operador = operador;
        this.operando = operando;
    }

    @Override
    public Value avaliar() {
        return new Value(operador, TeaToken.NOT);
    }
}
