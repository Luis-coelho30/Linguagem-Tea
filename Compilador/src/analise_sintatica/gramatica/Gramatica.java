package analise_sintatica.gramatica;

import java.util.*;

public class Gramatica {
    private final NaoTerminal simboloS;
    private final List<RegraProd> regras = new ArrayList<>();
    private final Map<NaoTerminal, List<RegraProd>> regrasLadoEsq = new HashMap<>();

    public Gramatica(NaoTerminal simboloS) {
        this.simboloS = simboloS;
    }

    public void adicionarRegra(RegraProd regraProd) {
        regras.add(regraProd);

        NaoTerminal naoTerminal = regraProd.getLadoEsq();
        List<RegraProd> lista = regrasLadoEsq.get(naoTerminal);

        if(!regrasLadoEsq.containsKey(naoTerminal)) {
            lista = new ArrayList<RegraProd>();
            regrasLadoEsq.put(naoTerminal, lista);
        }

        lista.add(regraProd);
    }

    public NaoTerminal getSimboloS() {
        return simboloS;
    }

    public List<RegraProd> getRegras() {
        return regras;
    }

    public List<RegraProd> getRegrasPara(NaoTerminal nt) {
        return regrasLadoEsq.getOrDefault(nt, Collections.emptyList());
    }

}
