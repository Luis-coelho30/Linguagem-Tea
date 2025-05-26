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
    private final Stack<ASTNode> pilhaAST = new Stack<>();
    private String nextOp = "";
    private boolean arvoreNaoTerminada;
    private final Lexer lexer = new Lexer();
    private int apontadorTokens = 0;

    public void analisarSintaxe(String programa) {
        ArrayList<Token> listaDeTokens = lexer.analisarLexico(programa);
        //Preparando estado inicial
        pilha.push("$");
        pilha.push("Expr");

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
                    construirNoAST(lhs, producao, listaDeTokens.get(apontadorTokens));
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
                    pilhaAST.push(new LiteralNode(new Value(inputToken.getLexema(), tipoToken)));
                    break;
                case TeaToken.IDENTIFICADOR:
                    pilhaAST.push(new VariavelNode(inputToken.getLexema()));
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
            case "ExprComp1":
            case "ExprRel1":
            case "ExprLog":
                if(!pilhaAST.isEmpty()) {
                    String operador;
                    switch (pilha.peek()) {
                        case "AND":
                        case "OR":
                        case "IGUAL":
                        case "DIFERENTE":
                        case "MAIOR_QUE":
                        case "MENOR_QUE":
                        case "MAIOR_IGUAL":
                        case "MENOR_IGUAL":
                            operador = pilha.peek();
                            ExprBinNode novaExpr = new ExprBinNode(operador, (ExprNode) pilhaAST.pop(), null);
                            pilhaAST.push(novaExpr);
                            arvoreNaoTerminada = true;
                            break;
                    }
                }
            break;

            case "ExprNot":
                if(tokenAtual.getLexema().equals("NOT")) {
                    ExprUnNode novaNegacao = new ExprUnNode("!", null);
                    pilhaAST.push(novaNegacao);
                    arvoreNaoTerminada = true;

                }
                if (!pilhaAST.isEmpty()) {

                }
                break;

            case "ExprArit1":
                if(!pilhaAST.isEmpty()) {
                    if(nextOp.equals("MUL") || nextOp.equals("DIV")) {
                        ExprNode dir = (ExprNode)pilhaAST.pop();
                        ExprNode esq = (ExprNode)pilhaAST.pop();
                        ExprBinNode exprMul = new ExprBinNode(nextOp, esq, dir);
                        pilhaAST.push(exprMul);
                        nextOp = "";
                    }
                    if(tokenAtual.getTipo() == TeaToken.SOMA || tokenAtual.getTipo() == TeaToken.SUB) {
                        ExprNode novoRamo = (ExprNode) pilhaAST.pop();
                        ExprBinNode novaExpr;
                        if(arvoreNaoTerminada) {
                            ExprBinNode arvore = (ExprBinNode) pilhaAST.pop();
                            arvore.setDir(novoRamo);
                            novaExpr = new ExprBinNode(tokenAtual.getTipo().name(), arvore, null);
                        } else {
                            novaExpr = new ExprBinNode(tokenAtual.getTipo().name(), novoRamo, null);
                        }
                        arvoreNaoTerminada = true;
                        pilhaAST.push(novaExpr);
                    }
                    else if (producao.isEmpty() && arvoreNaoTerminada) {
                        arvoreNaoTerminada = false;
                        ExprNode novoRamo = (ExprNode) pilhaAST.pop();
                        ExprBinNode arvore = (ExprBinNode) pilhaAST.pop();
                        arvore.setDir(novoRamo);
                        pilhaAST.push(arvore);
                    }
                }
                break;

            case "Termo1":
                if(!producao.isEmpty())
                    nextOp = pilha.peek();
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        String programa =   "int z = 10; " +

                            "main() {" +
                            "int x = (5 + 53) == 5; " +
                            "soma(x, z);" +
                            "} " +

                            "int soma(int a, int b) { " +
                            "return a + b; " +
                            "}";
        String expr = "!(5 == 5)";
        parser.analisarSintaxe(expr);
    }
}
