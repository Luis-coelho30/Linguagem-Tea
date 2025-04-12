package analise_lexica;

/**
 * A classe Token implementa o tipo Token
 *
 * @author Luis Augusto Coelho de Souza
 * @author Matheus Heimrath Barbosa
 * @author Guilherme Schenekenberg Teixeira
 * @author Pedro Vianna Carvalho
 *
 * @version 1.0
 */
public class Token {

    private int index;
    private int posInicial, posFinal;
    private String lexema;
    private TokenType tipo;
    private int linha;

    public Token(int index, int posInicial, int posFinal, String lexema, TokenType tipo, int linha) {
        this.index = index;
        this.posInicial = posInicial;
        this.posFinal = posFinal;
        this.lexema = lexema;
        this.tipo = tipo;
        this.linha = linha;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPosInicial() {
        return posInicial;
    }

    public void setPosInicial(int posInicial) {
        this.posInicial = posInicial;
    }

    public int getPosFinal() {
        return posFinal;
    }

    public void setPosFinal(int posFinal) {
        this.posFinal = posFinal;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public void setTipo(TokenType tipo) {
        this.tipo = tipo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public String toString() {
        return "[@" + this.index + "," + this.posInicial + "-" + this.posFinal +
                "='" + this.lexema + "',<" + this.tipo + ">," + this.linha + "]";
    }
}
