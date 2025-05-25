package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;

public class Value {
    private final String valor;
    private final TeaToken tipo;

    public Value(String valor, TeaToken tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public boolean ehInteiro() {
        return tipo.equals(TeaToken.NUMERO);
    }

    public boolean ehPontoFlutuante() {
        return tipo.equals(TeaToken.PONTO_FLUTUANTE);
    }

    public boolean ehString() {
        return tipo.equals(TeaToken.STRING);
    }

    public boolean ehBooleano() {
        return tipo.equals(TeaToken.BOOL);
    }

    public boolean ehNumerico() {
        return ehInteiro() || ehPontoFlutuante();
    }

    public int tornarInteiro() {
        return Integer.parseInt(valor);
    }

    public double tornarDouble() {
        return Double.parseDouble(valor);
    }

    public String tornarString() {
        return valor;
    }

    public boolean tornarBooleano() {
        return Boolean.parseBoolean(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}

