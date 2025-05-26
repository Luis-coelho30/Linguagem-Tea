package analise_sintatica.node_type.expr_type;

import analise_lexica.TeaToken;

import java.util.List;

public class FunctionCallNode extends ExprNode {
    private String funcId;
    private List<ExprNode> funcArgList;

    public FunctionCallNode(String funcId, List<ExprNode> funcArgList) {
        this.funcId = funcId;
        this.funcArgList = funcArgList;
    }

    public String getFuncId() {
        return funcId;
    }

    public List<ExprNode> getFuncArgList() {
        return funcArgList;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public void setFuncArgList(List<ExprNode> funcArgList) {
        this.funcArgList = funcArgList;
    }

    @Override
    public Value avaliar() {
        return new Value(funcId, TeaToken.STRING);
    }
}
