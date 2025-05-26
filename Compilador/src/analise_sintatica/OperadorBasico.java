package analise_sintatica;

public class OperadorBasico implements Operador{

    private final String simbolo;
    private final boolean associativoDireita;
    private final int precedencia;

    public OperadorBasico(String simbolo, boolean associativoDireita, int precedencia) {
        this.simbolo = simbolo;
        this.associativoDireita = associativoDireita;
        this.precedencia = precedencia;
    }

    @Override
    public boolean ehAssociativoDireita() {
        return associativoDireita;
    }

    @Override
    public int getPrecedencia() {
        return precedencia;
    }

    @Override
    public String getSimbolo() {
        return simbolo;
    }
}
