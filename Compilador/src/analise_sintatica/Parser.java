package analise_sintatica;

import analise_lexica.Lexer;
import analise_lexica.TeaToken;
import analise_lexica.Token;
import analise_sintatica.gramatica.GramaticaTea;

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
                    return; //TODO erro sintatico, esperado pilha.peek(), recebido listaTokens.get(apontadorTokens)
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
                    return; //TODO erro sintatico, token inesperado
                }
            }
        } while(!pilha.isEmpty());

        System.out.println("CONSEGUI!");
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
        boolean match;

        if(tipoToken == TeaToken.EOF && topo.equals("$")) {
            pilha.pop();
            match = true;
        }
        else {
            apontadorTokens++; //Consome o token
            pilha.pop();
            match = inputToken.getTipo() == TeaToken.valueOf(topo);
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

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.analisarSintaxe("main() {int x = 5 + 53; sobrado();}");
    }
}
