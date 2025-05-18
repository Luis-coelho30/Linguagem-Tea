package analise_sintatica.gramatica;

import analise_lexica.TeaToken;

public class Terminal implements Simbolos{
    private final TeaToken tipoToken;

    public Terminal(TeaToken tipoToken) {
        this.tipoToken = tipoToken;
    }

    @Override
    public boolean ehTerminal() {
        return true;
    }

    @Override
    public String getNome() {
        return tipoToken.name();
    }

    public TeaToken getTipo() {
        return tipoToken;
    }

}
