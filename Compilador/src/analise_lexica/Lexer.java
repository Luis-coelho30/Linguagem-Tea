package analise_lexica;

import exception.AnaliseLexicaException;

import java.util.ArrayList;

/**
 * A classe Lexer implementa o tokenizador responsavel pela saida da lista de tokens da analise lexica
 *
 * @author Luis Augusto Coelho de Souza
 * @author Matheus Heimrath Barbosa
 * @author Guilherme Schenekenberg Teixeira
 * @author Pedro Vianna Carvalho
 *
 * @version 1.0
 */
public class Lexer {

    int index; //Indica a posicao do token na lista de tokens
    int posAtual; //Indica a posicao do apontador que percorre o programa
    int posToken; //Indica a posicao final de cada token de acordo com a linha em que ele esta presente
    int linha; //A linha do token
    //Inicia a classe que armazena as palavras reservadas da linguagem
    private final DicionarioPalavrasReservadas dicionarioPalavrasReservadas = new DicionarioPalavrasReservadas();
    //Inicia a lista de tokens do Lexer
    private ArrayList<Token> listaTokens = new ArrayList<>();

    /**
     * Realiza a analise lexica de uma String programa de acordo com o lexico da linguagem Tea.
     *
     * @param programa - O programa a ser analisado.
     * @return - A lista de Tokens do programa.
     */
    public ArrayList<Token> analisarLexico(String programa) {

        posAtual = -1; posToken = -1;
        index = 1;
        linha = 1;
        listaTokens.clear();

        while(!fimPrograma(programa)) {
            char c = avancar(programa);

            //Buscando tokens com um unico caractere
            switch (c) {
                case '(' : inserirToken(TeaToken.PAREN_ESQ, "("); break;
                case ')' : inserirToken(TeaToken.PAREN_DIR, ")"); break;
                case '{' : inserirToken(TeaToken.CHAVE_ESQ, "{"); break;
                case '}' : inserirToken(TeaToken.CHAVE_DIR, "}"); break;
                case ',' : inserirToken(TeaToken.VIRGULA, ","); break;
                case '.' : inserirToken(TeaToken.PONTO, "."); break;
                case '-' : inserirToken(TeaToken.SUB, "-"); break;
                case '+' : inserirToken(TeaToken.SOMA, "+"); break;
                case '*' : inserirToken(TeaToken.MUL, "*"); break;
                case '%' : inserirToken(TeaToken.MOD, "%"); break;
                case ';' : inserirToken(TeaToken.TERMINAL, ";"); break;
                case '[' : inserirToken(TeaToken.COLCHETE_ESQ, "["); break;
                case ']' : inserirToken(TeaToken.COLCHETE_DIR, "]"); break;
                case ':' : inserirToken(TeaToken.DOIS_PONTOS, ":"); break;
                case ' ':
                case '\r':
                case '\t':
                case '_':
                case '\0':
                    break;
                case '\n': linha++; posToken = -1; break;

                //Buscando tokens com um ou dois caracteres
                case '=' :
                    if(avancar(programa)=='=') inserirToken(TeaToken.IGUAL, "==");
                    else {
                        recuar();
                        inserirToken(TeaToken.ATRIBUICAO, "=");
                    }
                    break;

                case '<' :
                    if(avancar(programa)=='=') inserirToken(TeaToken.MENOR_IGUAL, "<=");
                    else {
                        recuar();
                        inserirToken(TeaToken.MENOR_QUE, "<");
                    }
                    break;

                case '>' :
                    if(avancar(programa)=='=') inserirToken(TeaToken.MAIOR_IGUAL, ">=");
                    else {
                        recuar();
                        inserirToken(TeaToken.MAIOR_QUE, ">");
                    }
                    break;

                case '!' :
                    if(avancar(programa)=='=') inserirToken(TeaToken.DIFERENTE, "!=");
                    else {
                        recuar();
                        inserirToken(TeaToken.NOT, "!");
                    }
                    break;

                case '/' :
                    if(verificarCharAtual(programa,'/')) {
                        while (!verificarCharAtual(programa, '\n')) avancar(programa);
                    }
                    else inserirToken(TeaToken.DIV, "/");
                    break;

                case '\'': adicionarLiteralCaractere(programa); break;

                //Buscando tokens com mais de dois caracteres

                case '\"' : adicionarLiteralString(programa); break;

                default:
                    //Literais Numericos
                    if(verificarDigito(c)) {
                        adicionarLiteralNumerico(programa);
                    } else if (verificarLetra(c)) { //Palavras reservadas (e identificadores)
                        adicionarPalavraReservada(programa);
                    } else {
                        //Erro: caractere nao suportado
                        throw new AnaliseLexicaException("Caractere '" + c + "' nao suportado!");
                    }
            }
        }

        listaTokens.add(new Token(index, posToken, posToken, "EOF", TeaToken.EOF, linha));

        return listaTokens;
    }

    /**
     * Retorna true se o apontador tiver completado a leitura do programa e caso contrario, false
     *
     * @param programa A string que contem o programa a ser analisado
     * @return true se o apontador tiver completado a leitura do programa senao false
     */
    public boolean fimPrograma(String programa) {
        return posAtual == programa.length();
    }

    /**
     * Realiza a adicao de um lexema na lista de tokens
     *
     * @param tipo o tipo do token
     * @param lexema o lexema do token
     */
    private void inserirToken(TeaToken tipo, String lexema) {
        int inicioToken = posToken - lexema.length() + 1;
        listaTokens.add(new Token(index, inicioToken, posToken, lexema, tipo, linha));
        index++;
    }

    /**
     * Avanca o apontador que realiza a leitura do programa
     *
     * @param programa a String que sera analisada
     * @return o proximo caractere do programa
     */
    private char avancar(String programa) {
        char proxChar;
        posAtual++; posToken++;

        if(fimPrograma(programa)) { proxChar = '\0'; }
        else proxChar = programa.charAt(posAtual);

        return proxChar;
    }

    /**
     * Realiza o recuo dos apontadores do programa
     *
     */
    private void recuar() {
        posAtual = posAtual - 1;
        posToken = posToken - 1;
    }

    /**
     * Retorna true se o caractere atual eh ou nao o esperado
     *
     * @param expectedChar - o caractere esperado
     * @return true se expectedChar era o caractere esperado e, caso contrario, false
     */
    private boolean verificarCharAtual(String programa, char expectedChar) {
        return !fimPrograma(programa) && programa.charAt(posAtual) == expectedChar;
    }

    /**
     * Verificar se o caractere c eh um digito [0-9]
     *
     * @param c o caractere c a ser analisado
     * @return true se c for um digito, senao false
     */
    private boolean verificarDigito(char c) {
        return Character.toString(c).matches("\\d");
    }

    /**
     * Verificar se o caractere c eh uma letra [a-zA-Z]
     *
     * @param c o caractere c a ser analisado
     * @return true se c for uma letra, senao false
     */
    private boolean verificarLetra(char c) {
        return Character.toString(c).matches("[a-zA-Z]");
    }

    /**
     * Realiza a adicao de um literal String na lista de tokens
     *
     * @param programa a String do programa a ser analisado
     */
    private void adicionarLiteralString(String programa) {
        String novoLexema = "";
        char c = avancar(programa);
        //Buscar fim da string (\")
        while(c != '\0' && c != '\"') {
            if(verificarCharAtual(programa, '\n')) {
                novoLexema = novoLexema.concat("\\n");
            }
            else {
                novoLexema = novoLexema.concat(Character.toString(c));
            }
            c = avancar(programa);
        }
        if(c == '\0') {
            //Erro: String nao terminada
            throw new AnaliseLexicaException("Literal String nao terminada.");
        }

        inserirToken(TeaToken.STRING, novoLexema);
    }

    /**
     * Realiza a adicao de um literal numerico na lista de tokens
     *
     * @param programa a String do programa a ser analisado
     */
    private void adicionarLiteralNumerico(String programa) {
        String novoLexema = "";
        char c = programa.charAt(posAtual);

        do {
            novoLexema = novoLexema.concat(Character.toString(c));
            c = avancar(programa);
        } while (verificarDigito(c));

        if(c == '.') {
            novoLexema = novoLexema.concat(Character.toString(c));
            c = avancar(programa); //Consome o '.'
            if(verificarDigito(c)) {
                do {
                    novoLexema = novoLexema.concat(Character.toString(c));
                    c = avancar(programa);
                } while (verificarDigito(c));
                recuar();
                inserirToken(TeaToken.PONTO_FLUTUANTE, novoLexema);
            }
            else {
                //Erro: literal numerico nao terminado
                throw new AnaliseLexicaException("Literal numerico nao terminado.");
            }
        }
        else {
            recuar();
            inserirToken(TeaToken.NUMERO, novoLexema); //Absorve o token
        }
    }

    /**
     * Realiza a adicao de um literal caractere na lista de tokens
     *
     * @param programa a String do programa a ser analisado
     */
    private void adicionarLiteralCaractere(String programa) {
        char c = avancar(programa); //Consome o caractere '
        if (c=='\'') {
            //erro: Literal vazio
            throw new AnaliseLexicaException("Literal caractere vazio.");
        } else if(avancar(programa)!='\'') {
            //erro: Literal invalida, uma literal caractere so comporta um unico caractere
            throw new AnaliseLexicaException("Literal caractere invalida: o literal caractere deve possuir apenas um caractere.");
        }

        inserirToken(TeaToken.CARACTERE, Character.toString(c));
    }

    /**
     * Realiza a adicao de uma palavra reservada se ela existir no conjunto definido, senao adiciona um identificador
     *
     * @param programa a String do programa a ser analisado
     */
    private void adicionarPalavraReservada(String programa) {
        String novoLexema = "";
        char c = programa.charAt(posAtual);
        do {
            novoLexema = novoLexema.concat(Character.toString(c));
            c = avancar(programa);
        } while (verificarLetra(c) || verificarDigito(c));

        TeaToken tipo = dicionarioPalavrasReservadas.obterTipoToken(novoLexema);
        recuar();
        inserirToken(tipo, novoLexema);
    }
}
