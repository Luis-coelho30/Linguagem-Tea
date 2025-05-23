package analise_sintatica.gramatica;

public class GramaticaTeaBuilder {

    public Gramatica construirGramaticaTea() {
        //tudo organizado em strings agora
        Gramatica gramatica = new Gramatica.GramaticaBuilder("Programa")
                                            .regra("Programa", "UnidadesPreMain", "FuncMain", "UnidadesPosMain")

                                            .regra("UnidadesPreMain", "DeclGlobal", "UnidadesPreMain")
                                            .regra("UnidadesPreMain", "eps").marcarVazio("UnidadesPreMain")

                                            .regra("FuncMain", "main", "paren_esq", "paren_dir", "BlocoStmt")

                                            .regra("UnidadesPosMain", "FuncDecl", "UnidadesPosMain")
                                            .regra("UnidadesPosMain", "eps").marcarVazio("UnidadesPosMain")

                                            .regra("DeclGlobal", "Type", "identificador")

                                            .regra("FuncDecl", "TypeORtypeArray", "identificador", "paren_esq", "ParamList", "paren_dir", "BlocoStmt")
                                            .regra("ParamList", "Param", "ParamListTail")
                                            .regra("ParamList", "eps").marcarVazio("ParamList")
                                            .regra("ParamListTail", "virgula", "Param", "ParamListTail")
                                            .regra("ParamListTail", "eps").marcarVazio("ParamListTail")
                                            .regra("Param", "Type", "ParamSufixo")
                                            .regra("ParamSufixo", "identificador")
                                            .regra("ParamSufixo", "identificador", "colchete_esq", "colchete_dir")

                                            .regra("Stmt", "DeclStmt")
                                            .regra("Stmt", "IfStmt")
                                            .regra("Stmt", "WhileStmt")
                                            .regra("Stmt", "ForStmt")
                                            .regra("Stmt", "DoStmt")
                                            .regra("Stmt", "SwitchStmt")
                                            .regra("Stmt", "AssignStmt")
                                            .regra("Stmt", "FuncCallStmt")
                                            .regra("Stmt", "ReturnStmt")
                                            .regra("Stmt", "BlocoStmt")
                                            .regra("Stmt", "BreakStmt")
                                            .regra("Stmt", "EmptyStmt")
                                            .regra("StmtList", "Stmt", "StmtList")
                                            .regra("StmtList", "eps").marcarVazio("StmtList")

                                            .regra("DeclStmt", "Type", "identificador", "DeclStmtSufixo")
                                            .regra("DeclStmtSufixo", "colchete_esq", "numero", "colchete_dir", "terminal")
                                            .regra("DeclStmtSufixo", "DeclStmt1")
                                            .regra("DeclStmt1", "atribuicao", "Expr", "terminal")
                                            .regra("DeclStmt1", "terminal")

                                            .regra("TypeORtypeArray", "Type")
                                            .regra("TypeORtypeArray", "TypeArray")
                                            .regra("TypeArray", "Type", "colchete_esq", "colchete_dir")
                                            .regra("Type", "int")
                                            .regra("Type", "float")
                                            .regra("Type", "double")
                                            .regra("Type", "char")
                                            .regra("Type", "boolean")

                                            .regra("IfStmt", "if", "paren_esq", "Expr", "paren_dir", "Stmt", "ElifList", "ElsePart")
                                            .regra("ElifList", "Elif", "ElifList")
                                            .regra("ElifList", "eps").marcarVazio("ElifList")
                                            .regra("Elif", "elif", "paren_esq", "Expr", "paren_dir", "Stmt")
                                            .regra("ElsePart", "else", "Stmt")
                                            .regra("ElsePart", "eps").marcarVazio("ElsePart")

                                            .regra("WhileStmt", "while", "paren_esq", "Expr", "paren_dir", "Stmt")

                                            .regra("ForStmt", "for", "paren_esq", "ForInit", "ForCond", "terminal", "ForUpdate", "paren_dir", "Stmt")
                                            .regra("ForInit", "ForAssign")
                                            .regra("ForInit", "DeclStmt")
                                            .regra("ForInit", "terminal")
                                            .regra("ForAssign", "identificador", "atribuicao", "Expr", "terminal")
                                            .regra("ForCond", "Expr")
                                            .regra("ForCond", "eps").marcarVazio("ForCond")
                                            .regra("ForUpdate", "identificador", "atribuicao", "ExprArit")
                                            .regra("ForUpdate", "eps").marcarVazio("ForUpdate")

                                            .regra("DoStmt", "do", "Stmt", "while", "paren_esq", "Expr", "paren_dir", "terminal")

                                            .regra("SwitchStmt", "switch", "paren_esq", "ExprArit", "paren_dir", "chave_esq", "CaseList", "DefaultPart", "chave_dir")
                                            .regra("CaseList", "CaseClause", "CaseList")
                                            .regra("CaseList", "eps").marcarVazio("CaseList")
                                            .regra("CaseClause", "case", "ExprArit", "dois_pontos", "StmtList", "BreakStmt")
                                            .regra("BreakStmt", "break", "terminal")
                                            .regra("DefaultPart", "default", "dois_pontos", "StmtList")
                                            .regra("DefaultPart", "eps").marcarVazio("DefaultPart")

                                            .regra("AssignStmt", "identificador", "atribuicao", "AssignExpr", "terminal")

                                            .regra("FuncCallStmt", "identificador", "paren_esq", "ArgList", "paren_dir", "terminal")

                                            .regra("ReturnStmt", "return", "ReturnExpr", "terminal")
                                            .regra("ReturnExpr", "Expr")
                                            .regra("ReturnExpr", "eps").marcarVazio("ReturnExpr")

                                            .regra("BlocoStmt", "chave_esq", "StmtList", "chave_dir")

                                            .regra("EmptyStmt", "terminal")

                                            .regra("Expr", "AssignExpr")

                                            .regra("AssignExpr", "identificador", "atribuicao", "AssignExpr")
                                            .regra("AssignExpr", "ExprLog")

                                            .regra("ExprLog", "ExprRel", "ExprLog1")
                                            .regra("ExprLog1", "and", "ExprRel", "ExprLog1")
                                            .regra("ExprLog1", "or", "ExprRel", "ExprLog1")
                                            .regra("ExprLog1", "eps").marcarVazio("ExprLog1")

                                            .regra("ExprRel", "ExprComp", "ExprRel1")
                                            .regra("ExprRel1", "igual", "ExprComp", "ExprRel1")
                                            .regra("ExprRel1", "diferente", "ExprComp", "ExprRel1")
                                            .regra("ExprRel1", "eps").marcarVazio("ExprRel1")

                                            .regra("ExprComp", "ExprNot", "ExprComp1")
                                            .regra("ExprComp1", "menor_que", "ExprArit")
                                            .regra("ExprComp1", "menor_igual", "ExprArit")
                                            .regra("ExprComp1", "maior_que", "ExprArit")
                                            .regra("ExprComp1", "maior_igual", "ExprArit")
                                            .regra("ExprComp1", "eps").marcarVazio("ExprComp1")

                                            .regra("ExprNot", "not", "ExprNot") //"!"
                                            .regra("ExprNot", "ExprArit")

                                            .regra("ExprArit", "Termo", "ExprArit1")
                                            .regra("ExprArit1", "soma", "ExprArit1")
                                            .regra("ExprArit1", "sub", "ExprArit1")
                                            .regra("ExprArit1", "eps").marcarVazio("ExprArit1")

                                            .regra("Termo", "Fator", "Termo1")
                                            .regra("Termo1", "mul", "Termo1")
                                            .regra("Termo1", "div", "Termo1")
                                            .regra("Termo1", "eps").marcarVazio("Termo1")

                                            .regra("Fator", "paren_esq", "Expr", "paren_dir")
                                            .regra("Fator", "numero")
                                            .regra("Fator", "ponto_flutuante")
                                            .regra("Fator", "identificador", "FatorSufixo")

                                            .regra("FatorSufixo", "paren_esq", "ArgList", "paren_dir")
                                            .regra("FatorSufixo", "colchete_esq", "numero", "colchete_dir")
                                            .regra("FatorSufixo", "eps").marcarVazio("FatorSufixo")

                                            .regra("ArgList", "Expr", "ArgListTail")
                                            .regra("ArgList", "eps").marcarVazio("ArgList")

                                            .regra("ArgListTail", "virgula", "Expr", "ArgListTail")
                                            .regra("ArgListTail", "eps").marcarVazio("ArgListTail")
                                            .montarMapaDeOcorrencias()
                                            .build();

        return gramatica;
    }

}