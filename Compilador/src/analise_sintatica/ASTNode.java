package analise_sintatica;

public abstract class ASTNode {
    protected final int linha;
    protected final int coluna;

    public ASTNode(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public String tipoNo() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return tipoNo() + " em (" + linha + ", " + coluna + ")";
    }
}
