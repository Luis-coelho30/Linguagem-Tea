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

    public List<Boolean> listarTerminaisDerivados() {
        List<Boolean> resultado = new ArrayList<>();
        for (Simbolos simbolo : ladoDir) { //para cada simbolo do lado direito
            resultado.add(simbolo.ehTerminal()); //se for terminal, marca
        }
        return resultado;
    }
    //lista as posicoes dos terminais derivados a partir de um nao-terminal em uma regra de producao

}
