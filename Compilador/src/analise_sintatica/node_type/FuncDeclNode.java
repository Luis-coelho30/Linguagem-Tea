package analise_sintatica.node_type;

import analise_lexica.TeaToken;
import analise_sintatica.ASTNode;
import analise_sintatica.node_type.stmt_type.BlockNode;
import analise_sintatica.node_type.stmt_type.DeclNode;

import java.util.List;

public class FuncDeclNode extends ASTNode {
    private final String nome;
    private final TeaToken tipoRetorno;
    private final List<DeclNode> parametros;
    private final BlockNode corpo;

    public FuncDeclNode(String nome, TeaToken tipoRetorno, List<DeclNode> parametros, BlockNode corpo, int linha, int coluna) {
        super(linha, coluna);
        this.nome = nome;
        this.tipoRetorno = tipoRetorno;
        this.parametros = parametros;
        this.corpo = corpo;
    }

    public String getNome() {
        return nome;
    }

    public TeaToken getTipoRetorno() {
        return tipoRetorno;
    }

    public List<DeclNode> getParametros() {
        return parametros;
    }

    public BlockNode getCorpo() {
        return corpo;
    }
}
