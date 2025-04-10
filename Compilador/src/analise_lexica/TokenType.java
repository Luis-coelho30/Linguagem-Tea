package analise_lexica;

public enum TokenType {

    //Tokens com um caractere
    PAREN_ESQ, PAREN_DIR, CHAVE_ESQ, CHAVE_DIR,
    VIRGULA, PONTO, SUB, SOMA, MUL, DIV, TERMINATOR,
    COLCHETE_ESQ, COLCHETE_DIR,
    ATRIBUICAO, NOT,

    //Tokens com dois caracteres
    DIFERENTE, IGUAL,
    MAIOR_QUE, MAIOR_IGUAL,
    MENOR_QUE, MENOR_IGUAL,

    //Tipos literais
    IDENTIFICADOR, STRING, NUMERO, CARACTERE,

    // Palavras reservadas
    AND, OR, INT, FLOAT, DOUBLE, BOOL, CHAR, FALSE, TRUE, INC, DEC,
    ELSE, IF, FOR, WHILE, DO, STRUCT, PRINT, RETURN, EOF
}
