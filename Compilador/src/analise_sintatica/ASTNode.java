package analise_sintatica;

public abstract class ASTNode {
    public String tipoNo() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return tipoNo();
    }
}
