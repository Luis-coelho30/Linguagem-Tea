package analise_sintatica.gramatica;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GramaticaTea {
    private Gramatica gramatica;
    private Map<String, Set<String>> first;
    private Map<String, Set<String>> follow;

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
        /*
        I) preencher terminais primeiro, depois nao terminais
        II) repetir o seguinte ate nenhuma alteracao ocorrer:
	        - para cada nao-terminal A, para cada producao A -> Bw, definir:
		            FIRST(A) = FIRST(A) U FIRST(B)
        */
        //I)
        //percorre cada regra da gramatica
        List<Boolean> dir;
        for(RegraProd regra : gramatica.getRegras()){
            first.computeIfAbsent(regra.getLadoEsq().getNome(), k -> new HashSet<>());
            //Para cada regra, pegar a lista de simbolos associados a ela
            dir = regra.listarTerminaisDerivados();
            for (int i = 0; i < dir.size(); i++) {
                if (dir.get(i) == true) { //se encontrou um terminal, coloca no set first do simbolo (lado direito)
                    first.computeIfAbsent(regra.getLadoDir().get(i).getNome(), k -> new HashSet<>()).add(regra.getLadoDir().get(i).getNome());
                } else { //se for um nao terminal, deixa vazio
                    first.computeIfAbsent(regra.getLadoDir().get(i).getNome(), k -> new HashSet<>());
                }
            }
        }
        //II)
        boolean mudanca, eps;
        String A;
        int tamOrig = 0;
        do{
            mudanca = false;
            //Para cada regra de cada nao-terminal
            for(RegraProd regra : gramatica.getRegras()){
                //Pega o nao terminal em questao
                A = regra.getLadoEsq().getNome();
                //Para o conjunto First(A)
                Set<String> firstA = first.get(A);
                tamOrig = firstA.size();
                //Pega os simbolos do nao terminal
                dir = regra.listarTerminaisDerivados();
                eps = true;

                for(int i = 0; i < dir.size(); i++){
                    Set<String> prim = first.get(regra.getLadoDir().get(i).getNome());
                    //nao tenho certeza ainda porque talvez o null seja um problema
                    //if(prim == null){
                    //    prim = new HashSet<>();
                    //}
                    for(String t : prim){
                        //if(t.isEps() == false){
                        firstA.add(t);
                        //}
                    }
                    //if(temEps(prim) == false){
                    //    eps = false;
                    //    break; //nao pode mais mudar
                    //}
                }
                /*
                if(eps == true){
                    Terminal epsilon = Terminal.epsilon();
                    if(firstA.contains(epsilon) == false){
                        firstA.add(epsilon);
                    }
                }
                */
                //verificacoes de epsilon estao comentadas pois precisaria criar um token; mais tarde arrumo isso
            }

            //erro: "cannot resolve symbol firstA; arrumar depois
            //if(firstA.size() > tamOrig){
            //    mudanca = true;
            //}

        }while (mudanca == true);
    }

    private void calcularFollow() {

    }

    public Gramatica getGramatica() {
        return gramatica;
    }

    public Map<String, Set<String>> getFirst() {
        return first;
    }

    public Map<String, Set<String>> getFollow() {
        return follow;
    }
}
