package analise_lexica;

/**
 * O enum TokenType define os tipos de tokens existentes na linguagem Tea
 *
 * @author Luis Augusto Coelho de Souza
 * @author Matheus Heimrath Barbosa
 * @author Guilherme Schenekenberg Teixeira
 * @author Pedro Vianna Carvalho
 *
 * @version 1.0
 */
public enum TeaToken {

    //Tokens com um caractere
    PAREN_ESQ, PAREN_DIR, CHAVE_ESQ, CHAVE_DIR,
    VIRGULA, PONTO, DOIS_PONTOS, SUB, SOMA, MUL, DIV, TERMINAL,
    COLCHETE_ESQ, COLCHETE_DIR,
    ATRIBUICAO, NOT, MOD,

    //Tokens com dois caracteres
    DIFERENTE, IGUAL,
    MAIOR_QUE, MAIOR_IGUAL,
    MENOR_QUE, MENOR_IGUAL,

    //Tipos literais
    IDENTIFICADOR, STRING, NUMERO, PONTO_FLUTUANTE,CARACTERE,

    // Palavras reservadas
    AND, OR, INT, FLOAT, DOUBLE, BOOL, CHAR, INC, DEC,
    ELSE, IF, FOR, WHILE, DO, SWITCH, CASE, DEFAULT, STRUCT, PRINT, RETURN, EOF
}
