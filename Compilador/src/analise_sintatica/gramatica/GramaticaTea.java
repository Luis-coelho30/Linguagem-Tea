package analise_sintatica.gramatica;

import java.util.Map;
import java.util.Set;

public class GramaticaTea {
    private Gramatica gramatica;
    private Map<Simbolos, Set<Terminal>> first;
    private Map<NaoTerminal, Set<Terminal>> follow;

    public GramaticaTea() {
        construirGramatica();
        calcularFirst();
        calcularFollow();
    }

    private void construirGramatica() {

        NaoTerminal programa = new NaoTerminal("programa");
        NaoTerminal unidPreMain = new NaoTerminal("unidPreMain");








        gramatica = new Gramatica(programa);

    }

    private void calcularFirst() {

    }

    private void calcularFollow() {

    }

    public Gramatica getGramatica() {
        return gramatica;
    }

    public Map<Simbolos, Set<Terminal>> getFirst() {
        return first;
    }

    public Map<NaoTerminal, Set<Terminal>> getFollow() {
        return follow;
    }
}
