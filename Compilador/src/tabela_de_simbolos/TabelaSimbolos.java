package tabela_de_simbolos;

import java.util.Hashtable;

public class TabelaSimbolos {

    private static TabelaSimbolos instancia;

    private Hashtable<String, Simbolo> tabelaDeSimbolos;

    public static TabelaSimbolos getInstance() {
        if(instancia == null) { instancia = new TabelaSimbolos(); }
        return instancia;
    }

    public void inserir(String lexema) {
        if(!contemSimbolo(lexema)) {
            Simbolo novoSimbolo = new Simbolo(lexema);
            tabelaDeSimbolos.put(lexema, novoSimbolo);
        }
        /* else {
            //TO-DO simbolo repetido, identificador ja existente
        } */
    }

    public void definirTipo(String lexema, String tipo) {
        tabelaDeSimbolos.get(lexema).setTipo(tipo);
    }

    public void definirCategoria(String lexema, String categoria) {
        tabelaDeSimbolos.get(lexema).setCategoria(categoria);
    }

    public void definirEscopo(String lexema, String escopo) {
        tabelaDeSimbolos.get(lexema).setEscopo(escopo);
    }

    public void definirValor(String lexema, Object valor) {
        tabelaDeSimbolos.get(lexema).setValor(valor);
    }

    public void definirConstante(String lexema, boolean valor) {
        tabelaDeSimbolos.get(lexema).setConstante(valor);
    }

    public boolean contemSimbolo(String lexema) {
        return tabelaDeSimbolos.containsKey(lexema);
    }
}
