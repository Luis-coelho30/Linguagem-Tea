package analise_lexica;

import java.util.HashMap;

/**
 * A classe PalavrasReservadas implementa um hashmap que simboliza o dicionario de palavras presentes na linguagem Tea
 *
 * @author Luis Augusto Coelho de Souza
 * @author Matheus Heimrath Barbosa
 * @author Guilherme Schenekenberg Teixeira
 * @author Pedro Vianna Carvalho
 *
 * @version 1.0
 */
public class DicionarioPalavrasReservadas {
    HashMap<String, TeaToken> palavrasReservadasDic = new HashMap<>();

    public DicionarioPalavrasReservadas() {
        palavrasReservadasDic.put("AND", TeaToken.AND);
        palavrasReservadasDic.put("OR", TeaToken.OR);
        palavrasReservadasDic.put("int", TeaToken.INT);
        palavrasReservadasDic.put("boolean", TeaToken.BOOL);
        palavrasReservadasDic.put("char", TeaToken.CHAR);
        palavrasReservadasDic.put("float", TeaToken.FLOAT);
        palavrasReservadasDic.put("double", TeaToken.DOUBLE);
        palavrasReservadasDic.put("string", TeaToken.STRING);
        palavrasReservadasDic.put("inc", TeaToken.INC);
        palavrasReservadasDic.put("dec", TeaToken.DEC);
        palavrasReservadasDic.put("else", TeaToken.ELSE);
        palavrasReservadasDic.put("if", TeaToken.IF);
        palavrasReservadasDic.put("for", TeaToken.FOR);
        palavrasReservadasDic.put("while", TeaToken.WHILE);
        palavrasReservadasDic.put("do", TeaToken.DO);
        palavrasReservadasDic.put("switch", TeaToken.SWITCH);
        palavrasReservadasDic.put("case", TeaToken.CASE);
        palavrasReservadasDic.put("default", TeaToken.DEFAULT);
        palavrasReservadasDic.put("struct", TeaToken.STRUCT);
        palavrasReservadasDic.put("print", TeaToken.PRINT);
        palavrasReservadasDic.put("return", TeaToken.RETURN);
    }

    public TeaToken obterTipoToken(String lexema) {
        TeaToken teaToken;
        teaToken = palavrasReservadasDic.get(lexema);
        if(teaToken == null) {
            teaToken = TeaToken.IDENTIFICADOR;
        }

        return teaToken;
    }
}
