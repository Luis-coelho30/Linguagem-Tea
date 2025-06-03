package analise_sintatica.gramatica;

import analise_sintatica.gramatica.simbolo.NaoTerminal;
import analise_sintatica.gramatica.simbolo.Simbolo;
import analise_sintatica.gramatica.simbolo.Terminal;

import java.util.*;

public class Gramatica {
    private final String simboloS; //nao terminal inicial; agora eh string (antes era NaoTerminal)
    private final List<RegraProd> regras = new ArrayList<>();
    private final Map<String, List<RegraProd>> regrasLadoEsq = new HashMap<>(); //lista de strings associadas a uma lista de regras de producao
    private final Set<String> derivamVazio = new HashSet<>();
    private Map<String, List<RegraProd>> ocorrenciasPorSimbolo = new HashMap<>();

    private Gramatica(String simboloS) {
        this.simboloS = simboloS;
    }

    public void adicionarRegra(RegraProd regraProd) {
        regras.add(regraProd);

        String naoTerminal = regraProd.getLadoEsq().getNome(); //como eh string, opera pelo nome
        List<RegraProd> lista;

        if(!regrasLadoEsq.containsKey(naoTerminal)) {
            lista = new ArrayList<>();
            regrasLadoEsq.put(naoTerminal, lista);
        }

        lista = regrasLadoEsq.get(naoTerminal);
        lista.add(regraProd);
    }

    public String getSimboloS() {
        return simboloS;
    }

    public List<RegraProd> getRegras() {
        return regras;
    }

    public List<RegraProd> getRegrasPara(String nt) {
        return regrasLadoEsq.getOrDefault(nt, Collections.emptyList());
    }

    private void setOcorrenciasPorSimbolo(Map<String, List<RegraProd>> ocorrenciasPorSimbolo) {
        this.ocorrenciasPorSimbolo = ocorrenciasPorSimbolo;
    }

    public List<RegraProd> getOcorrenciasSimb(String nt) {
        return ocorrenciasPorSimbolo.get(nt);
    }

    public void addDerivamVazio(String naoTerminal) {
        derivamVazio.add(naoTerminal);
    }

    public Set<String> getDerivamVazio() {
        return derivamVazio;
    }

    public static class GramaticaBuilder {

        private final Gramatica gramatica;
        private final Map<String, List<RegraProd>> ocorrenciasPorSimbolo = new HashMap<>();

        public GramaticaBuilder(String simboloS) { //construtor de gramatica
            this.gramatica = new Gramatica(simboloS);
        }

        public GramaticaBuilder regra(String ladoEsq, String... ladoDir) { //constutor de regra
            NaoTerminal nt = new NaoTerminal(ladoEsq);
            List<Simbolo> simbolosDir = new ArrayList<>();

            for (String s : ladoDir) {
                if(ehNaoTerminal(s)) {
                    simbolosDir.add(new NaoTerminal(s));
                }
                else {
                    simbolosDir.add(new Terminal(s.toUpperCase()));
                    //terminais: inicial maiuscula
                }
            }

            RegraProd regra = new RegraProd(nt, simbolosDir);
            gramatica.adicionarRegra(regra);
            return this;
        }

        public GramaticaBuilder marcarVazio(String naoTerminal) {
            gramatica.addDerivamVazio(naoTerminal);
            return this;
        }

        public GramaticaBuilder montarMapaDeOcorrencias() {
            for (RegraProd regra : gramatica.getRegras()) {
                for (Simbolo simb : regra.getLadoDir()) {
                    if (!simb.ehTerminal()) {
                        ocorrenciasPorSimbolo
                                .computeIfAbsent(simb.getNome(), k -> new ArrayList<>())
                                .add(regra);
                    }
                }
            }

            return this;
        }

        public Gramatica build() {
            gramatica.setOcorrenciasPorSimbolo(ocorrenciasPorSimbolo);
            return gramatica;
        }

        public boolean ehNaoTerminal(String simbolo) {
            return Character.isUpperCase(simbolo.charAt(0));
        } //identifica se eh nao terminal ao observar se o primeiro caractere eh minusculo
    }

}
