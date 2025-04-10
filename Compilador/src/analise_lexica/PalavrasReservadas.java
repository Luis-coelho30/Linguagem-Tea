package analise_lexica;

import java.util.HashMap;

public class PalavrasReservadas {
    HashMap<String, TokenType> conjuntoPalavrasReservadas = new HashMap<>();

    public PalavrasReservadas() {
        conjuntoPalavrasReservadas.put("AND", TokenType.AND);
        conjuntoPalavrasReservadas.put("OR", TokenType.OR);
        conjuntoPalavrasReservadas.put("int", TokenType.INT);
        conjuntoPalavrasReservadas.put("boolean", TokenType.BOOL);
        conjuntoPalavrasReservadas.put("char", TokenType.CHAR);
        conjuntoPalavrasReservadas.put("float", TokenType.FLOAT);
        conjuntoPalavrasReservadas.put("double", TokenType.DOUBLE);
        conjuntoPalavrasReservadas.put("inc", TokenType.INC);
        conjuntoPalavrasReservadas.put("dec", TokenType.DEC);
        conjuntoPalavrasReservadas.put("else", TokenType.ELSE);
        conjuntoPalavrasReservadas.put("if", TokenType.IF);
        conjuntoPalavrasReservadas.put("FALSE", TokenType.FALSE);
        conjuntoPalavrasReservadas.put("TRUE", TokenType.TRUE);
        conjuntoPalavrasReservadas.put("for", TokenType.FOR);
        conjuntoPalavrasReservadas.put("while", TokenType.WHILE);
        conjuntoPalavrasReservadas.put("do", TokenType.DO);
        conjuntoPalavrasReservadas.put("struct", TokenType.STRUCT);
        conjuntoPalavrasReservadas.put("print", TokenType.PRINT);
        conjuntoPalavrasReservadas.put("return", TokenType.RETURN);
        conjuntoPalavrasReservadas.put("EOF", TokenType.EOF);
    }

    public TokenType obterTipoToken(String lexema) {
        TokenType tokenType;
        tokenType = conjuntoPalavrasReservadas.get(lexema);
        if(tokenType == null) {
            tokenType = TokenType.IDENTIFICADOR;
        }

        return tokenType;
    }
}
