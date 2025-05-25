package analise_sintatica.gramatica;

public class GramaticaTeaBuilder {

    public Gramatica construirGramaticaTea() {
        //tudo organizado em strings agora
        Gramatica gramatica = new Gramatica.GramaticaBuilder("Programa")
                                            .regra("Programa", "UnidadesPreMain", "FuncMain", "UnidadesPosMain")

                                            .regra("UnidadesPreMain", "DeclStmt", "UnidadesPreMain")
                                            .regra("UnidadesPreMain", "eps").marcarVazio("UnidadesPreMain")

                                            .regra("FuncMain", "main", "paren_esq", "paren_dir", "BlocoStmt")

                                            .regra("UnidadesPosMain", "FuncDecl", "UnidadesPosMain")
                                            .regra("UnidadesPosMain", "eps").marcarVazio("UnidadesPosMain")

                                            .regra("FuncDecl", "TypeORtypeArray", "identificador", "paren_esq", "ParamList", "paren_dir", "BlocoStmt")
                                            .regra("ParamList", "Param", "ParamListTail")
                                            .regra("ParamList", "eps").marcarVazio("ParamList")
                                            .regra("ParamListTail", "virgula", "Param", "ParamListTail")
                                            .regra("ParamListTail", "eps").marcarVazio("ParamListTail")
                                            .regra("Param", "Type", "identificador", "ParamSufixo")
                                            .regra("ParamSufixo", "colchete_esq", "colchete_dir")
                                            .regra("ParamSufixo", "eps").marcarVazio("ParamSufixo")

                                            .regra("Stmt", "identificador", "StmtIdTail", "terminal")
                                            .regra("Stmt", "DeclStmt")
                                            .regra("Stmt", "IfStmt")
                                            .regra("Stmt", "WhileStmt")
                                            .regra("Stmt", "ForStmt")
                                            .regra("Stmt", "DoStmt")
                                            .regra("Stmt", "SwitchStmt")
                                            .regra("Stmt", "ReturnStmt")
                                            .regra("Stmt", "BlocoStmt")
                                            .regra("Stmt", "BreakStmt")
                                            .regra("Stmt", "EmptyStmt")
                                            .regra("StmtIdTail", "paren_esq", "ArgList", "paren_dir")
                                            .regra("StmtIdTail", "colchete_esq", "numero", "colchete_dir", "atribuicao", "Expr")
                                            .regra("StmtIdTail",  "atribuicao", "Expr")

                                            .regra("DeclStmt", "Type", "identificador", "DeclStmtSufixo")
                                            .regra("DeclStmtSufixo", "colchete_esq", "numero", "colchete_dir", "terminal")
                                            .regra("DeclStmtSufixo", "DeclStmt1")
                                            .regra("DeclStmt1", "atribuicao", "Expr", "terminal")
                                            .regra("DeclStmt1", "terminal")

                                            .regra("TypeORtypeArray", "Type", "TypeSufixo")
                                            .regra("TypeSufixo", "colchete_esq", "colchete_dir")
                                            .regra("TypeSufixo", "eps").marcarVazio("TypeSufixo")
                                            .regra("Type", "int")
                                            .regra("Type", "float")
                                            .regra("Type", "double")
                                            .regra("Type", "char")
                                            .regra("Type", "boolean")

                                            .regra("IfStmt", "if", "paren_esq", "Expr", "paren_dir", "Stmt", "OptionalElif")
                                            .regra("OptionalElif", "elif", "paren_esq", "Expr", "paren_dir", "Stmt", "OptionalElif")
                                            .regra("OptionalElif", "OptionalElse")
                                            .regra("OptionalElse", "else", "Stmt")
                                            .regra("ElifORElse", "eps").marcarVazio("ElifORElse")


                                            .regra("WhileStmt", "while", "paren_esq", "Expr", "paren_dir", "Stmt")

                                            .regra("ForStmt", "for", "paren_esq", "ForInit", "terminal", "ForCond", "terminal", "ForUpdate", "paren_dir", "Stmt")
                                            .regra("ForInit", "Type", "identificador", "atribuicao", "Expr")
                                            .regra("ForInit", "identificador", "atribuicao", "Expr")
                                            .regra("ForInit", "eps").marcarVazio("ForInit")
                                            .regra("ForCond", "Expr")
                                            .regra("ForCond", "eps").marcarVazio("ForCond")
                                            .regra("ForUpdate", "identificador", "atribuicao", "ExprArit")
                                            .regra("ForUpdate", "eps").marcarVazio("ForUpdate")

                                            .regra("DoStmt", "do", "Stmt", "while", "paren_esq", "Expr", "paren_dir", "terminal")

                                            .regra("SwitchStmt", "switch", "paren_esq", "ExprArit", "paren_dir", "chave_esq", "CaseSectionList", "chave_dir")
                                            .regra("CaseSectionList", "CaseSection", "CaseSectionList")
                                            .regra("CaseSectionList", "eps").marcarVazio("CaseSectionList")
                                            .regra("CaseSection", "CaseLabels", "CaseBody")
                                            .regra("CaseLabels", "case", "ExprArit", "dois_pontos", "CaseLabels")
                                            .regra("CaseLabels", "default", "dois_pontos")
                                            .regra("CaseBody", "Stmt", "CaseBody")
                                            .regra("CaseBody", "eps").marcarVazio("CaseBody")

                                            .regra("BreakStmt", "break", "terminal")

                                            .regra("ReturnStmt", "return", "ReturnExpr", "terminal")
                                            .regra("ReturnExpr", "Expr")
                                            .regra("ReturnExpr", "eps").marcarVazio("ReturnExpr")

                                            .regra("BlocoStmt", "chave_esq", "BlocoInstList", "chave_dir")
                                            .regra("BlocoInstList", "BlocoInst", "BlocoInstList")
                                            .regra("BlocoInstList", "eps").marcarVazio("BlocoInstList")
                                            .regra("BlocoInst", "Stmt")

                                            .regra("EmptyStmt", "terminal")

                                            .regra("Expr", "ExprLog", "ExprSufixo")

                                            .regra("ExprSufixo", "atribuicao", "Expr")
                                            .regra("ExprSufixo", "eps").marcarVazio("ExprSufixo")

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
                                            .regra("ExprArit1", "soma", "Termo", "ExprArit1")
                                            .regra("ExprArit1", "sub", "Termo", "ExprArit1")
                                            .regra("ExprArit1", "eps").marcarVazio("ExprArit1")

                                            .regra("Termo", "Fator", "Termo1")
                                            .regra("Termo1", "mul", "Fator", "Termo1")
                                            .regra("Termo1", "div", "Fator", "Termo1")
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