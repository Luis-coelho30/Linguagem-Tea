package analise_sintatica;

import analise_lexica.TeaToken;
import analise_lexica.Token;
import analise_sintatica.node_type.expr_type.*;

import java.util.*;

public class ShuntingYardParser {

    private final ArrayList<Token> listaDeTokens;
    private final Map<String, Operador> operadores;

    private void criarNoBinario(Stack<ExprNode> stack, String operator) {
        final ExprNode noDir = stack.pop();
        final ExprNode noEsq = stack.pop();
        stack.push(new ExprBinNode(operator, noEsq, noDir));
    }

    private void criarNoUnario(Stack<ExprNode> stack, String operator) {
        final ExprNode operando = stack.pop();
        stack.push(new ExprUnNode(operator, operando));
    }

    public ShuntingYardParser(ArrayList<Token> listaDeTokens) {
        this.listaDeTokens = listaDeTokens;
        List<Operador> listaOperadores = new ArrayList<>();
        listaOperadores.add(new OperadorBasico("OR", false, 1));
        listaOperadores.add(new OperadorBasico("AND", false, 2));
        listaOperadores.add(new OperadorBasico("==", false, 3));
        listaOperadores.add(new OperadorBasico("!=", false, 3));
        listaOperadores.add(new OperadorBasico("<", false, 4));
        listaOperadores.add(new OperadorBasico(">", false, 4));
        listaOperadores.add(new OperadorBasico("<=", false, 4));
        listaOperadores.add(new OperadorBasico(">=", false, 4));
        listaOperadores.add(new OperadorBasico("+", false, 5));
        listaOperadores.add(new OperadorBasico("-", false, 5));
        listaOperadores.add(new OperadorBasico("*", false, 6));
        listaOperadores.add(new OperadorBasico("/", false, 6));
        listaOperadores.add(new OperadorBasico("%", false, 6));
        listaOperadores.add(new OperadorBasico("!", true, 7)); // ! é right-associative
        operadores = new HashMap<>();
        for (Operador o : listaOperadores) {
            operadores.put(o.getSimbolo(), o);
        }
    }

    public ASTNode converterInfixaParaAST() {
        final Stack<String> operatorStack = new Stack<>();
        final Stack<ExprNode> operandStack = new Stack<>();
        String poppedOperator = "";

        for (Token token : listaDeTokens) {
            String lexema = token.getLexema();
            if (lexema.equals("(")) {
                operatorStack.push("(");
            } else if (lexema.equals(")")) {
                while (!operatorStack.isEmpty() && !(poppedOperator = operatorStack.pop()).equals("(")) {
                    if (operadores.containsKey(poppedOperator)) {
                        final Operador operador = operadores.get(poppedOperator);
                        if (operador.ehAssociativoDireita()) {
                            criarNoUnario(operandStack, poppedOperator);
                        } else {
                            criarNoBinario(operandStack, poppedOperator);
                        }
                    }
                }
                if (!poppedOperator.equals("(")) {
                    // Tratar erro de parênteses desbalanceados
                    System.err.println("Erro: Parênteses desbalanceados");
                    return null;
                }
            } else if (operadores.containsKey(lexema)) {
                final Operador opr1 = operadores.get(lexema);
                while (!operatorStack.isEmpty() && operadores.containsKey(operatorStack.peek())) {
                    final Operador opr2 = operadores.get(operatorStack.peek());
                    if ((!opr1.ehAssociativoDireita() && opr1.getPrecedencia() <= opr2.getPrecedencia()) ||
                            (opr1.ehAssociativoDireita() && opr1.getPrecedencia() < opr2.getPrecedencia())) {
                        operatorStack.pop();
                        if (opr2.ehAssociativoDireita()) {
                            criarNoUnario(operandStack, opr2.getSimbolo());
                        } else {
                            criarNoBinario(operandStack, opr2.getSimbolo());
                        }
                    } else {
                        break;
                    }
                }
                operatorStack.push(lexema);
            } else {
                if(token.getTipo() == TeaToken.IDENTIFICADOR) {
                    operandStack.push(new VariavelNode(lexema));
                } else {
                    operandStack.push(new LiteralNode(new Value(token.getLexema(), token.getTipo())));
                }

            }
        }

        while (!operatorStack.isEmpty()) {
            final String operator = operatorStack.pop();
            if (operadores.containsKey(operator)) {
                final Operador op = operadores.get(operator);
                if (op.ehAssociativoDireita()) {
                    criarNoUnario(operandStack, operator);
                } else {
                    criarNoBinario(operandStack, operator);
                }
            } else {
                // Tratar erro de operador inválido na pilha
                System.err.println("Erro: Operador inválido na pilha: " + operator);
                return null;
            }
        }

        return operandStack.isEmpty() ? null : operandStack.pop();
    }
}