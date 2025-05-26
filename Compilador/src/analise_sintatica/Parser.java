package analise_sintatica;

import analise_lexica.Lexer;
import analise_lexica.TeaToken;
import analise_lexica.Token;
import analise_sintatica.gramatica.GramaticaTea;
import analise_sintatica.node_type.expr_type.*;

import java.util.*;

public class Parser {

    private final GramaticaTea gramaticaTea = new GramaticaTea();
    private final Stack<String> pilha = new Stack<>(); //A pilha eh uma pilha de producoes (listas de simbolos)
    private final Stack<ASTNode> treeStack = new Stack<>();
    private final Stack<ASTNode> nodeStack = new Stack<>();
    private String nextOp = "";
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
                    String lhs = pilha.pop();
                    for (int i = producao.size() - 1; i >= 0; i--) {
                        String simbolo = producao.get(i);
                        pilha.push(simbolo);
                    }
                    //construirNoAST(lhs, producao, listaDeTokens.get(apontadorTokens));
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
        boolean match = false;

        if(tipoToken == TeaToken.EOF && topo.equals("$")) {
            pilha.pop();
            match = true;
        }
        else if(inputToken.getTipo() == TeaToken.valueOf(topo)){
            apontadorTokens++; //Consome o token
            pilha.pop();
            match = true;

            switch (tipoToken) {
                case TeaToken.NUMERO:
                case TeaToken.PONTO_FLUTUANTE:
                case TeaToken.BOOL:
                case TeaToken.CHAR:
                case TeaToken.STRING:
                    nodeStack.push(new LiteralNode(new Value(inputToken.getLexema(), tipoToken)));
                    break;
                case TeaToken.IDENTIFICADOR:
                    nodeStack.push(new VariavelNode(inputToken.getLexema()));
                    break;
            }

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

    private void construirNoAST(String regra, List<String> producao,Token tokenAtual) {
        switch (regra) {

        }
        /*switch (regra) {
            case "ExprComp1":
            case "ExprRel1":
            case "ExprLog":
                if(!nodeStack.isEmpty()) { //Se existir uma expressao completa em nodeStack
                    String operador;
                    switch (pilha.peek()) { //Se a expressao atual for comparativa, relacional ou logica
                        case "AND":
                        case "OR":
                        case "IGUAL":
                        case "DIFERENTE":
                        case "MAIOR_QUE":
                        case "MENOR_QUE":
                        case "MAIOR_IGUAL":
                        case "MENOR_IGUAL":
                            operador = pilha.peek(); //Pegue o operador
                            //Crie uma arvore com o operador e a expressao
                            ExprBinNode novaExpr = new ExprBinNode(operador, (ExprNode) nodeStack.pop(), null);
                            treeStack.push(novaExpr); //Empilhe a arvore
                            break;
                    }
                }
            break;

            case "ExprNot":
                if(pilha.peek().equals("NOT")) {
                    ExprUnNode novaNegacao = new ExprUnNode("!", null);
                    treeStack.push(novaNegacao);
                }
                break;

            case "ExprArit1":
                if(!nodeStack.isEmpty()) { //Se houver uma expressao na pilha de nos
                    if(nextOp.equals("MUL") || nextOp.equals("DIV")) { //Por precedencia, se houver uma operacao de * ou /
                        ExprNode dir = (ExprNode)nodeStack.pop(); //Pegue o operando direito
                        ExprNode esq = (ExprNode)nodeStack.pop(); //Pegue o operando esquerdo
                        ExprBinNode exprMul = new ExprBinNode(nextOp, esq, dir); //Construa uma arvore completa
                        nodeStack.push(exprMul); //empilhe na pilha de nos
                        nextOp = ""; //reinicie o marcador de operador
                    }
                    if(tokenAtual.getTipo() == TeaToken.SOMA || tokenAtual.getTipo() == TeaToken.SUB) {
                        ExprNode expr = (ExprNode) nodeStack.pop();
                        ExprBinNode novaExpr;
                        if(!treeStack.isEmpty()) {
                            ExprBinNode arvore = (ExprBinNode) treeStack.pop();
                            arvore.setDir(expr);
                            novaExpr = new ExprBinNode(tokenAtual.getTipo().name(), arvore, null);
                        } else {
                            novaExpr = new ExprBinNode(tokenAtual.getTipo().name(), expr, null);
                        }
                        treeStack.push(novaExpr);
                    }
                }
                break;

            case "Termo1":
                if(!producao.isEmpty())
                    nextOp = pilha.peek();
        }*/
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        String programa =   "int z = 10; " +

                            "main() {" +
                            "int x = 5 + 3 * (7 + 8) - 9; " +
                            "soma(x, z);" +
                            "} " +

                            "int soma(int a, int b) { " +
                            "return a + b; " +
                            "}";
        parser.analisarSintaxe(programa);
    }
}
