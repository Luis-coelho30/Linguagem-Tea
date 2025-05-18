package analise_sintatica.gramatica;

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
}
