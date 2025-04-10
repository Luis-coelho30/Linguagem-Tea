package tabela_de_simbolos;

public class Simbolo {
    private String lexema;
    private String tipo;
    private String categoria;
    private String escopo;
    private Object valor;
    private boolean constante;

    public Simbolo(String lexema) {
        this.lexema = lexema;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean isConstante() {
        return constante;
    }

    public void setConstante(boolean constante) {
        this.constante = constante;
    }
}
