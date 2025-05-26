package analise_sintatica;

public interface Operador {
    boolean ehAssociativoDireita();

    int getPrecedencia();

    String getSimbolo();
}
