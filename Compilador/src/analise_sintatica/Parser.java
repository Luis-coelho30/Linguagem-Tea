package analise_sintatica;

import analise_lexica.Lexer;
import analise_lexica.TeaToken;
import analise_lexica.Token;
import analise_sintatica.gramatica.GramaticaTea;
import exception.AnaliseSintaticaException;

import java.util.*;

public class Parser {

    private final GramaticaTea gramaticaTea = new GramaticaTea();
    private final Stack<String> pilha = new Stack<>(); //A pilha eh uma pilha de producoes (listas de simbolos)
    private final Lexer lexer = new Lexer();
    private int apontadorTokens = 0;

    public void analisarSintaxe(String programa) {
        ArrayList<Token> listaDeTokens = lexer.analisarLexico(programa);
        //Preparando estado inicial
        pilha.push("$");
        pilha.push("Programa");

        do {
            String topo = pilha.peek();
            //Se o topo eh terminal, entao tentar fazer match
            if (ehTerminal(topo)) {
                if(!match(listaDeTokens.get(apontadorTokens))) {
                    erro("Esperado " + topo + " recebido: " + listaDeTokens.get(apontadorTokens).getTipo(),
                            listaDeTokens.get(apontadorTokens).getLinha(), listaDeTokens.get(apontadorTokens).getPosInicial());
                }
            }
            //Senao, buscar nova regra e empilhar
            else {
                List<String> producao = gramaticaTea.obterProducao(topo, getTipoToken(listaDeTokens.get(apontadorTokens)));

                if(producao != null) {
                    pilha.pop();
                    for (int i = producao.size() - 1; i >= 0; i--) {
                        String simbolo = producao.get(i);
                        pilha.push(simbolo);
                    }
                }
                else {
                    erro("Token inesperado", listaDeTokens.get(apontadorTokens).getLinha(), listaDeTokens.get(apontadorTokens).getPosInicial());
                }
            }
        } while(!pilha.isEmpty());

        System.out.println("Analise completa!");
    }

    /**
     * match - Compara o token da entrada com o token no topo da pilha da analise sintatica
     *
     * @param inputToken - o Token da entrada
     * @return True se se o token da entrada for igual o do topo da pilha, caso contrario False
     */
    private boolean match(Token inputToken) {
        String topo = pilha.peek();
        TeaToken tipoToken = inputToken.getTipo();
        boolean match = false;

        if(tipoToken == TeaToken.EOF && topo.equals("$")) {
            pilha.pop();
            match = true;
        }
        else if(inputToken.getTipo() == TeaToken.valueOf(topo)){
            apontadorTokens++; //Consome o token
            pilha.pop();
            match = true;
        }

        return match;
    }

    private boolean ehTerminal(String simbolo) {
        if(simbolo.equals("$"))
            return true;

        for(TeaToken teaToken : TeaToken.values()) {
            if(teaToken.name().equals(simbolo)) {
                return true;
            }
        }

        return false;
    }

    private String getTipoToken(Token token) {
        String tokenName = token.getTipo().name();
        if(tokenName.equals("EOF")) {
            tokenName = "$";
        }

        return tokenName;
    }

    private void erro(String msgErro, int linha, int coluna) {
        throw new AnaliseSintaticaException("Erro na linha " + linha + ", coluna " + coluna + ":\n" + msgErro);
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        String programa =   "int z = 10; " +

                            "main() {" +
                                "int x; " +
                                "soma(x, z);" +
                            "} " +

                            "int soma(int a, int b) { " +
                            "return a + b; " +
                            "}";

        String programa2 = "int 3; main() { 55 + 1;} ;;;";

        String programa3 = "main() {" +
                                "y = 99 - 80; " +
                                "return 10; " +
                            "}";
        parser.analisarSintaxe(programa3);
    }
}
