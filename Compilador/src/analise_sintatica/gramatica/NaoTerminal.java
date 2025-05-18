package analise_sintatica.gramatica;

public class NaoTerminal implements Simbolos{
    private final String nome;

    public NaoTerminal(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean ehTerminal() {
        return false;
    }

    @Override
    public String getNome() {
        return nome;
    }
}
