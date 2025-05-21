package analise_sintatica.gramatica;

import analise_sintatica.gramatica.simbolo.NaoTerminal;
import analise_sintatica.gramatica.simbolo.Simbolos;

import java.util.*;

public class GramaticaTea {
    private Gramatica gramatica;
    private final Map<String, Set<String>> first = new HashMap<>(); //set de strings agora
    private final Map<String, Set<String>> follow = new HashMap<>(); //set de strings agora
    private final Set<String> derivamEpsilon = new HashSet<>();

    public GramaticaTea() {
        construirGramatica();
        calcularFirst();
        calcularFollow();
    }

    private void construirGramatica() {
        GramaticaTeaBuilder builder = new GramaticaTeaBuilder();
        gramatica = builder.construirGramaticaTea();
    }

    public void calcularFirst(){
        /*
        passos:
        I) preencher terminais e nao terminais cujo primeiro eh terminal primeiro, depois nao terminais cujo primeiro eh nao terminal
        II) repetir o seguinte ate nenhuma alteracao ocorrer:
	        - para cada nao terminal A, para cada producao A -> Bw, onde B pode ser terminal ou nao terminal e w eh uma cadeia qualquer, definir:
		            FIRST(A) = FIRST(A) U FIRST(B)
        */

        //I)
        for(RegraProd regra : gramatica.getRegras()){
            String A = regra.getLadoEsq().getNome();
            first.computeIfAbsent(A, k -> new HashSet<>());

            for(Simbolos sim : regra.getLadoDir()){ //inicializa first de terminais
                String nome = sim.getNome();
                Set<String> firstSim = first.computeIfAbsent(nome, k -> new HashSet<>());
                if(sim.ehTerminal() == true){
                    firstSim.add(nome);
                }
            }
            //provavelmente da pra simplificar essa funcao mas to com sono

            List<Simbolos> direita = regra.getLadoDir();

            Simbolos primeiroDir = direita.get(0);
            if(primeiroDir.getNome().equals("eps")){
                derivamEpsilon.add(A);
                first.get(A).add(primeiroDir.getNome());
            }
            else if(primeiroDir.ehTerminal()) {
                first.get(A).add(primeiroDir.getNome());
            }
        }

        //II)
        boolean houveMudanca;
        do{
            houveMudanca = false;
            for(RegraProd regra : gramatica.getRegras()){ //terminais cujo primeiro eh nao terminal
                String A = regra.getLadoEsq().getNome();
                Set<String> firstA = first.get(A); //recupera o conjunto atual (deve estar vazio)
                int tamanhoOriginal = firstA.size(); //enquanto esse tamanho mudar, continua o loop
                List<Simbolos> direita = regra.getLadoDir();

                boolean todosDerivamEps = true;
                for(Simbolos sim : direita){ //para cada simbolo, T ou NT, no lado direito
                    Set<String> firstSimbolo = first.getOrDefault(sim.getNome(), Set.of()); //conjunto first desse T ou NT
                    for(String terminal : firstSimbolo){ //para cada terminal desse conjunto first
                        if(!terminal.equals("eps")){
                            firstA.add(terminal);
                        }
                    }
                    if(!derivamEpsilon.contains(sim.getNome())){
                        todosDerivamEps = false;
                        break;
                    }
                }
                if((todosDerivamEps == true) && (!firstA.contains("eps"))){
                    firstA.add("eps");
                    derivamEpsilon.add(A);
                }
                if(firstA.size() != tamanhoOriginal){
                    houveMudanca = true;
                }
            }
        }while(houveMudanca == true);
    }

    private Set<String> obterFirst(String A){
        return first.get(A);
    }

    public void exibirFirsts() {
        for (Map.Entry<String, Set<String>> entry : first.entrySet()) {
            System.out.print("FIRST(" + entry.getKey() + ") = { ");
            for (String t : entry.getValue()) {
                System.out.print(t + " ");
            }
            System.out.println("}");
        }
    }

    public void calcularFollow(){
        /*
        follow(A), onde A eh um nao terminal: conjunto de terminais a que podem aparecer imediatamente para a direita de A em alguma forma
        exemplo: S -> AaB

        - simbolos podem existir entre A e a desde que derivem epsilon e desaparecam (por isso o hashmap derivamEpsilon)
        - se A pode ser o simbolo rightmost em alguma forma, entao follow(A) = $ (indica fim da definicao)
        - $ eh um simbolo especial que nao pertence a nenhuma gramatica

        passos:
        I) compute follow(A) for all non-terminals A
        II) apply these rules until nothing can be added to follow(A):
            1. place $ in follow(S) (S: simbolo nao terminal inicial)
            2. se tiver uma producao A -> BC, entao tudo em first(C) exceto epsilon esta em follow(B)
            3. se tiver uma producao A -> zB ou uma producao A -> zBC, onde first(C) contem epsilon, entao tudo em follow(A) esta em follow(B)
         */

        for(RegraProd regra : gramatica.getRegras()) {
            String A = regra.getLadoEsq().getNome();
            follow.computeIfAbsent(A, k -> new HashSet<>());
        }

        //1.
        String S = gramatica.getRegras().get(0).getLadoEsq().getNome();
        follow.get(S).add("$");

        boolean houveMudanca, contemEps;
        do {
            houveMudanca = false;
            for(RegraProd regra : gramatica.getRegras()){ //para cada regra
                String A = regra.getLadoEsq().getNome(); //A = lado esquerdo
                List<Simbolos> direita = regra.getLadoDir(); //vamos analisar as regras

                for(int i = 0; i < direita.size(); i++) { //para cada simbolo na direita
                    Simbolos atual = direita.get(i);

                    if (!atual.ehTerminal()){
                        String B = direita.get(i).getNome();
                        Set<String> followB = follow.computeIfAbsent(B, k -> new HashSet<>());
                        int tamanhoOriginal = followB.size();

                        //2.
                        contemEps = false;
                        if (i + 1 < direita.size()) { //se tem alguma coisa depois do B

                            String C = direita.get(i + 1).getNome();
                            Set<String> firstC = obterFirst(C);
                            if (firstC.remove("EPS")) {
                                contemEps = true;
                            }
                            followB.addAll(firstC);
                            /*
                            nota: TERMINAIS NAO POSSUEM CONJUNTO FOLLOW. eu so criei eles aqui por conveniencia na hora de adicionar terminais aos follows de nao terminais
                            (concatena os follows direto ao inves de ficar verificando se eh ou nao um terminal.
                            se ficar ruim no futuro, da pra mudar; eh so para uso interno
                             */
                        }
                        //3.
                        if (contemEps == true) {
                            followB.addAll(obterFollow(A));
                        }
                        //3.
                        else { //se B eh o ultimo da producao A
                            followB.addAll(obterFollow(A));
                        }

                        if (followB.size() > tamanhoOriginal) {
                            houveMudanca = true;
                        }
                    }
                }
            }
        }while(houveMudanca == false);
    }

    private Set<String> obterFollow(String A){
        return follow.get(A);
    }

    public void exibirFollows(){
        for (Map.Entry<String, Set<String>> entry : follow.entrySet()) {
            System.out.print("FOLLOW(" + entry.getKey() + ") = { ");
            for (String t : entry.getValue()) {
                System.out.print(t + " ");
            }
            System.out.println("}");
        }
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
