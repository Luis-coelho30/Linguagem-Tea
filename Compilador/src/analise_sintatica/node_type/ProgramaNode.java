package analise_sintatica.node_type;

import analise_sintatica.ASTNode;
import analise_sintatica.node_type.stmt_type.DeclNode;

import java.util.List;

public class ProgramaNode extends ASTNode {
    private final List<DeclNode> variaveisGlobais;
    private final List<FuncDeclNode> funcoes;
    private final MainNode main;

    public ProgramaNode(List<DeclNode> variaveisGlobais, List<FuncDeclNode> funcoes, MainNode main) {
        this.variaveisGlobais = variaveisGlobais;
        this.funcoes = funcoes;
        this.main = main;
    }

    public List<DeclNode> getVariaveisGlobais() {
        return variaveisGlobais;
    }

    public List<FuncDeclNode> getFuncoes() {
        return funcoes;
    }

    public MainNode getMain() {
        return main;
    }
}
