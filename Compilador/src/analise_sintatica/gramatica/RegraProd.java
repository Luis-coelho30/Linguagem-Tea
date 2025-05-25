package analise_sintatica.gramatica;

import analise_sintatica.gramatica.simbolo.NaoTerminal;
import analise_sintatica.gramatica.simbolo.Simbolos;

import java.util.ArrayList;
import java.util.List;

public class RegraProd {
    private final NaoTerminal ladoEsq;
    private final List<Simbolos> ladoDir;

    public RegraProd(NaoTerminal ladoEsq, List<Simbolos> ladoDir) {
        this.ladoEsq = ladoEsq;
        this.ladoDir = ladoDir;
    }

    public NaoTerminal getLadoEsq() {
        return ladoEsq;
    }

    public List<Simbolos> getLadoDir() {
        return ladoDir;
    }

    public int getPosicaoSimb(String simbName) {
        int i = 0;
        while (i < ladoDir.size() && !getNomeSimboloDir(i).equals(simbName)) {
            i++;
        } if(i == ladoDir.size()) {
            i = -1;
        }

        return i;
    }

    //Retorna o nome do simbolo da posicao i de uma producao
    public String getNomeSimboloDir(int i) {
        return ladoDir.get(i).getNome();
    }

    public List<String> getNomesSimbolosDir() {
        List<String> nomes = new ArrayList<>();

        ladoDir.forEach(simb -> nomes.add(simb.getNome()));

        return nomes;
    }

    public boolean simbEhTerminal(int i) {
        return getLadoDir().get(i).ehTerminal();
    }

}
