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
        GramaticaTeaBuilder builder = new GramaticaTeaBuilder();
        gramatica = builder.construirGramaticaTea();
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
