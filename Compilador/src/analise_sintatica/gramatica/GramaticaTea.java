package analise_sintatica.gramatica;

import java.util.*;

public class GramaticaTea {
    private Gramatica gramatica;
    private final Map<String, Set<String>> first = new HashMap<>(); //Map de strings (NT) para um Set de Strings (T) -> associa um NT a um conjunto de terminais como conjunto first
    private final Map<String, Set<String>> follow = new HashMap<>(); //Map de strings (NT) para um Set de Strings (T) -> associa um NT a um conjunto de terminais como conjunto follow
    private final Map<String, Map<String, RegraProd>> tabelaLL1 = new HashMap<>(); //Um map de strings (NT) para um Map de strings (T) para regras de producao
    private final Set<String> derivamEpsilon = new HashSet<>();

    public GramaticaTea() {
        construirGramatica();
        calcularFirst();
        //calcularFollow();
    }

    private void construirGramatica() {
        GramaticaTeaBuilder builder = new GramaticaTeaBuilder();
        gramatica = builder.construirGramaticaTea();
        derivamEpsilon.addAll(gramatica.getDerivamVazio());
    }

    public void calcularFirst(){
        //I) Criar conjuntos FIRST e adicionar a cada conjunto seu primeiro terminal (se houver)
        // Inicializa FIRST de terminais
        for (RegraProd regra : gramatica.getRegras()) {
            for (int i = 0; i < regra.getNomesSimbolosDir().size(); i++) {
                String simb = regra.getNomeSimboloDir(i);
                if (regra.simbEhTerminal(i)) {
                    first.computeIfAbsent(simb, k -> new HashSet<>()).add(simb);
                }
            }
        }

        // Inicializa FIRST dos NT como vazios
        for (RegraProd regra : gramatica.getRegras()) {
            String A = regra.getLadoEsq().getNome();
            first.computeIfAbsent(A, k -> new HashSet<>());
        }

        //II) Aos conjuntos cujo primeiro simbolo da derivacao nao for um terminal: FIRST(A) = FIRST(A) U FIRST(B) se A -> Bw;

        boolean houveMudanca;
        //Inicia o loop externo que mantem o algoritmo em execucao ate que todos os FIRST tenham sido completados
        do{
            houveMudanca = false;
            //Para cada regra de producao
            for (RegraProd regra : gramatica.getRegras()) {
                //Pegue o nome do nao-terminal A associado
                String A = regra.getLadoEsq().getNome();
                //Pegue o conjunto do nao-terminal A
                Set<String> firstA = first.get(A);
                //Pegue o tamanho original desse conjunto
                int tamanhoOriginal = firstA.size();

                //Pegue os nomes dos simbolos da derivacao de A
                List<String> direita = regra.getNomesSimbolosDir();
                //Assuma que todos derivam em Vazio
                boolean todosDerivamEps = true;
                //Para cada simbolo, se este derivar em vazio adicionar todos elementos do conjunto, menos o vazio
                //First(A) = First(B) - { eps }
                for (String simb : direita) {
                    Set<String> firstSimb = first.getOrDefault(simb, Set.of());
                    for (String terminal : firstSimb) {
                        if (!terminal.equals("eps")) {
                            firstA.add(terminal);
                        }
                    }
                    //Se um simbolo nao derivar em vazio, termine o loop (fim do first)
                    if (!derivamEpsilon.contains(simb)) {
                        todosDerivamEps = false;
                        break;
                    }
                }

                //Se todos derivarem em vazio, entao First(A) = First(A) U { eps }
                if (todosDerivamEps) {
                    firstA.add("eps");
                }

                if (firstA.size() > tamanhoOriginal) {
                    houveMudanca = true;
                }
            }
        }while(houveMudanca);
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

    public void calcularFollow() {
        //Inicializa todos os conjuntos de nao-terminais Follow
        for (RegraProd regra : gramatica.getRegras()) {
            String A = regra.getLadoEsq().getNome();
            follow.computeIfAbsent(A, k -> new HashSet<>());
        }
        //1.O simbolo inicial da gramatica sempre deve conter o simbolo $ (indicador de fim de cadeia)
        String S = gramatica.getSimboloS();
        follow.get(S).add("$");

        boolean houveMudanca;
        //Inicia loop externo para Follow que garante que todos os follows sejam atualizados (ja que cada Follow depende de outro)
        do {
            houveMudanca = false;
            //Para cada nao terminal no conjunto follow
            for (String naoTerminal : follow.keySet()) {
                //Recolha o conjunto de regras que possuam A na direita
                List<RegraProd> ocorrenciasSimb = gramatica.getOcorrenciasSimb(naoTerminal);

                if(ocorrenciasSimb == null) {
                    continue;
                }

                for (RegraProd regraProd : ocorrenciasSimb) {
                    int proxDeNT = regraProd.getPosicaoSimb(naoTerminal) + 1;
                    //Salva o tamanho antigo para detectar mudanças
                    int tamanhoAntigo = follow.get(naoTerminal).size();
                    //Recolha o tamanho do lado direito
                    int ladoDirSize = regraProd.getLadoDir().size();

                    //1. Se A for o ultimo na derivacao, entao o FOLLOW do lado esquerdo sera selecionado
                    if (proxDeNT == ladoDirSize) {
                        follow.get(naoTerminal).addAll(follow.get(regraProd.getLadoEsq().getNome()));
                    }
                    //2. Se o simbolo apos A for um terminal, entao Follow(A) = Follow(A) U { terminal }
                    else if (regraProd.simbEhTerminal(proxDeNT)) {
                        follow.get(naoTerminal).add(regraProd.getNomeSimboloDir(proxDeNT));
                    }
                    //3. Se o proximo de A for um nao-terminal B, entao Follow(A) = Follow(A) U First(B):
                    else{
                        //Se o nao-terminal B conter eps
                        if(derivamEpsilon.contains(regraProd.getNomeSimboloDir(proxDeNT))) {
                            int cursor = proxDeNT;
                            do {
                                for (String simb : first.get(regraProd.getNomeSimboloDir(cursor))) {
                                    if(!simb.equals("EPS")) {
                                        follow.get(naoTerminal).add(simb);
                                    }
                                }
                                cursor++;
                            }while(cursor < ladoDirSize && derivamEpsilon.contains(regraProd.getNomeSimboloDir(proxDeNT)));
                            //Se acabaram os simbolos, entao NT pode ser o simbolo mais a direita, entao Follow(NT) = Follow(NT) + Follow(ladoEsq)
                            if(cursor == ladoDirSize) {
                                follow.get(naoTerminal).addAll(follow.get(regraProd.getLadoEsq().getNome()));
                            }
                            //Encontrou-se um simbolo sem eps, entao Follow(A) = Follow(A) U First(B):
                            else {
                                follow.get(naoTerminal).addAll(first.get(regraProd.getNomeSimboloDir(cursor)));
                            }
                        }
                        //Senao:
                        else {
                            follow.get(naoTerminal).addAll(first.get(regraProd.getNomeSimboloDir(proxDeNT)));
                        }
                    }
                    //Se o tamanho do conjunto mudou, houve mudança
                    if (follow.get(naoTerminal).size() > tamanhoAntigo) {
                        houveMudanca = true;
                    }
                }
            }
        } while (houveMudanca);
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

    private void construirTabelaLL1(){
        /*
            Para cada produção A → alfa da gramática, faça o seguinte:
            1. Para cada terminal a em FIRST(A), inclua A → alfa em M[A,a].
            2. Se EPS pertence a FIRST(alfa), inclua A → alfa em M[A,b] para cada terminal b em FOLLOW(A).
            Se $ pertence a FIRST(A) e $ pertence a FOLLOW(A), acrescente também A → alfa em M[A,$].
         */
        //Para cada Regra da linguagem
        for(RegraProd regraProd : gramatica.getRegras()) {
            //Obter o NT da esquerda
            String A = regraProd.getLadoEsq().getNome();

            for(String simb : regraProd.getNomesSimbolosDir()) {

            }


        }
    }

    private Set<String> obterFirstAlfa(List<String> alfa) {
        Set<String> firstAlfa = new HashSet<>();

        for(String simb : alfa) {

        }

        return firstAlfa;
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
