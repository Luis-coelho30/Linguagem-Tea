package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;

import java.util.List;

public class FunctionCallNode extends ExprNode {
    private final String funcId;
    private final List<ExprNode> funcArgList;

    public FunctionCallNode(String funcId, List<ExprNode> funcArgList, int linha, int coluna) {
        super(linha, coluna);
        this.funcId = funcId;
        this.funcArgList = funcArgList;
    }

    public String getFuncId() {
        return funcId;
    }

    public List<ExprNode> getFuncArgList() {
        return funcArgList;
    }

    @Override
    public Value avaliar() {
        return new Value(funcId, TeaToken.STRING);
    }
}
