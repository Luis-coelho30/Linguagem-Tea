package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;
import analise_lexica.Token;

public class ExprBinNode extends ExprNode {
    private final String operador;
    private ExprNode esq;
    private ExprNode dir;

    public ExprBinNode(String operador, ExprNode esq, ExprNode dir) {
        this.operador = operador;
        this.esq = esq;
        this.dir = dir;
    }

    public String getOperador() {
        return operador;
    }

    public ExprNode getEsq() {
        return esq;
    }

    public ExprNode getDir() {
        return dir;
    }

    public void setEsq(ExprNode esq) {
        this.esq = esq;
    }

    public void setDir(ExprNode dir) {
        this.dir = dir;
    }

    @Override
    public Value avaliar() {
        Value valorEsq = esq.avaliar();
        Value valorDir = dir.avaliar();
        String novoValor;
        boolean resultadoBool;

        if (operador.equals("==") || operador.equals("!=") || operador.equals(">") || operador.equals("<") || operador.equals(">=") || operador.equals("<=")) {
            // Comparações numéricas
            if (valorEsq.ehNumerico() && valorDir.ehNumerico()) {
                double a = valorEsq.tornarDouble();
                double b = valorDir.tornarDouble();

                switch (operador) {
                    case "==":
                        resultadoBool = a == b;
                        break;
                    case "!=":
                        resultadoBool = a != b;
                        break;
                    case ">":
                        resultadoBool = a > b;
                        break;
                    case "<":
                        resultadoBool = a < b;
                        break;
                    case ">=":
                        resultadoBool = a >= b;
                        break;
                    case "<=":
                        resultadoBool = a <= b;
                        break;
                    default:
                        throw new RuntimeException("Operador inválido");
                }
                novoValor = String.valueOf(resultadoBool);
                return new Value(novoValor, TeaToken.BOOL);
            }
        }
        else if (operador.equals("&&") || operador.equals("||")) {
            if (!valorEsq.ehBooleano() || !valorDir.ehBooleano()) {
                throw new RuntimeException("Operadores lógicos requerem booleanos.");
            }
            boolean a = valorEsq.tornarBooleano();
            boolean b = valorDir.tornarBooleano();

            switch (operador) {
                case "&&":
                    resultadoBool = a && b;
                    break;
                case "||":
                    resultadoBool = a || b;
                    break;
                default:
                    throw new RuntimeException("Operador logico invalido");
            }
            novoValor = String.valueOf(resultadoBool);
            return new Value(novoValor, TeaToken.BOOL);
        }
        else {
            if(valorEsq.ehString() || valorDir.ehString()) {
                if(!operador.equals("+")) {
                    throw new RuntimeException("Operacao nao suportada");
                } else {
                    return new Value("" + valorEsq + valorDir, TeaToken.STRING);
                }
            }
            else if (valorEsq.ehPontoFlutuante() || valorDir.ehPontoFlutuante()) {
                double a = valorEsq.tornarDouble();
                double b = valorDir.tornarDouble();
                double resultadoDouble;
                switch (operador) {
                    case "+":
                        resultadoDouble = a + b;
                        break;
                    case "-":
                        resultadoDouble = a - b;
                        break;
                    case "*":
                        resultadoDouble = a * b;
                        break;
                    case "/":
                        resultadoDouble = a / b;
                        break;
                    default:
                        throw new RuntimeException("Operador invalido");
                }
                novoValor = String.valueOf(resultadoDouble);
                return new Value(novoValor, TeaToken.PONTO_FLUTUANTE);
            }
            else {
                int a = valorEsq.tornarInteiro();
                int b = valorDir.tornarInteiro();
                int resultadoInt;
                switch (operador) {
                    case "+":
                        resultadoInt = a + b;
                        break;
                    case "-":
                        resultadoInt = a - b;
                        break;
                    case "*":
                        resultadoInt = a * b;
                        break;
                    case "/":
                        resultadoInt = a / b;
                        break;
                    default:
                        throw new RuntimeException("Operador invalido");
                }
                novoValor = String.valueOf(resultadoInt);
                return new Value(novoValor, TeaToken.NUMERO);
            }
        }

        return null; //Nunca eh alcancado
    }
}
