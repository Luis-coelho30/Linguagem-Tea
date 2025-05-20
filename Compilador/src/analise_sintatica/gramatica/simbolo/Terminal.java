package analise_sintatica.gramatica.simbolo;

import analise_lexica.TeaToken;

public class Terminal implements Simbolos {
    private final TeaToken tipoToken;

    public Terminal(String tipoToken) {
        this.tipoToken = TeaToken.valueOf(tipoToken);
    }

    @Override
    public boolean ehTerminal() {
        return true;
    }

    @Override
    public String getNome() {
        return tipoToken.name();
    }

}
