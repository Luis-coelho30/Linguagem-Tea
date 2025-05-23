package analise_sintatica.gramatica;

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
            //Para cada regra de producao
            for (RegraProd regra : gramatica.getRegras()) {
                //Recolha A, o nao-terminal associado a regra
                String A = regra.getLadoEsq().getNome();
                //Recolha o conjunto de regras que possuam A na direita
                List<RegraProd> ocorrenciasSimb = gramatica.getOcorrenciasSimb(A);

                if(ocorrenciasSimb == null) {
                    continue;
                }

                for (RegraProd regraComA : ocorrenciasSimb) {
                    int proxDeA = regraComA.getPosicaoSimb(A) + 1;
                    //Salva o tamanho antigo para detectar mudanças
                    int tamanhoAntigo = follow.get(A).size();

                    //1. Se A for o ultimo na derivacao, entao o FOLLOW do lado esquerdo sera selecionado
                    if (proxDeA == regraComA.getLadoDir().size()) {
                        follow.get(A).addAll(follow.get(regraComA.getLadoEsq().getNome()));
                    }
                    //2. Se o simbolo apos A for um terminal, entao Follow(A) = Follow(A) U { terminal }
                    else if (regraComA.simbEhTerminal(proxDeA)) {
                        String terminal = regraComA.getNomeSimboloDir(proxDeA);
                        follow.get(A).add(terminal);
                    }
                    //3. Se o proximo de A for um nao-terminal B, entao Follow(A) = Follow(A) U First(B):
                    else{
                        String B = regraComA.getNomeSimboloDir(proxDeA);
                        Set<String> firstB = first.get(B);
                        //Adiciona todos os simbolos de First(B) ao Follow(A), menos o vazio
                        for (String simb : firstB) {
                            if (!simb.equals("eps")) {
                                follow.get(A).add(simb);
                            }
                        }
                        //Se o First(B) conter o vazio, entao:
                        int i = proxDeA;
                        while (i < regraComA.getLadoDir().size() && first.get(regraComA.getNomeSimboloDir(i)).contains("eps")) {
                            i++;
                            // Se houver outro símbolo à frente
                            if (i < regraComA.getLadoDir().size()) {
                                String proxNome = regraComA.getNomeSimboloDir(i);
                                //1. O proximo terminal depois do proximo simbolo sera adicionado ao FOLLOW
                                if (regraComA.simbEhTerminal(i)) {
                                    follow.get(A).add(proxNome);
                                    break;
                                }
                                //2. O proximo FIRST do proximo nao terminal sera adicionado ao follow
                                else {
                                    for (String simb : first.get(proxNome)) {
                                        if (!simb.equals("eps")) {
                                            follow.get(A).add(simb);
                                        }
                                    }
                                }
                            } else {
                                //3. Se B eh o ultimo simbolo da regra, entao Follow(A) = Follow(A) U Follow(ladoEsq)
                                follow.get(A).addAll(follow.get(regraComA.getLadoEsq().getNome()));
                            }
                        }
                    }
                    //Se o tamanho do conjunto mudou, houve mudança
                    if (follow.get(A).size() > tamanhoAntigo) {
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
        System.out.println(" ");
        System.out.println(" ");
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
