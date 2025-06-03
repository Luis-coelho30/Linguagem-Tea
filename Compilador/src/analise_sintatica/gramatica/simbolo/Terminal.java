package analise_sintatica.gramatica.simbolo;

import analise_lexica.TeaToken;

public class Terminal implements Simbolo {
    private final TeaToken tipoToken;

    public Terminal(String tipoToken) {
        this.tipoToken = TeaToken.valueOf(tipoToken);
    }
    //agora representa terminais usando valueof, nao tipo de TeaToken

    @Override
    public boolean ehTerminal() {
        return true;
    }

    @Override
    public String getNome() {
        return tipoToken.name();
    }

}
