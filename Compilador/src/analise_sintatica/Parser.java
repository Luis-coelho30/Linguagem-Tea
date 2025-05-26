package analise_sintatica;

import analise_lexica.Lexer;
import analise_lexica.TeaToken;
import analise_lexica.Token;
import analise_sintatica.gramatica.GramaticaTea;

import java.util.*;

public class Parser {

    private final GramaticaTea gramaticaTea = new GramaticaTea();
    private final Stack<String> pilha = new Stack<>(); //A pilha eh uma pilha de producoes (listas de simbolos)
    private final Lexer lexer = new Lexer();
    private int apontadorTokens = 0;
    private Token tokenAtual;//, lookahead;
    ArrayList<Token> listaTokens;

    public void analisarSintaxeRecursivaDescent(String programa) { //somente verifica estrutura por enquanto; nao monta a arvore ainda
        //inicializacoes
        listaTokens = lexer.analisarLexico(programa);
        listaTokens.add(new Token(listaTokens.size(), -1, -1, "EOF", TeaToken.EOF, -1));
        tokenAtual = listaTokens.get(apontadorTokens);

        programa();

        if (tokenAtualEh(TeaToken.EOF)) {
            System.out.println("Alles gut!");
        } else {
            erro();
        }
    }

    /* NOTAS:
        - nao terminais que podem ser somente uma coisa (so tem uma producao) nao precisam do if inicial; so fiz por conveniencia e pra caso de alguma coisa errada que me prove errado
        - imagino que nao precise dos "else"s nas verificacoes pois o proprio teatokenatual vai estar apontando para o lugar errado caso de erro
     */

    private void programa() {
        UnidadesPreMain();
        FuncMain();
        UnidadesPosMain();
    }

    //INICIO dos "filhos" de UnidadesPreMain()
    private void UnidadesPreMain() {
        if (tokenAtualEh(TeaToken.INT) || (tokenAtualEh(TeaToken.FLOAT)) || (tokenAtualEh(TeaToken.DOUBLE)) || (tokenAtualEh(TeaToken.CHAR)) || (tokenAtualEh(TeaToken.BOOLEAN))) {
            DeclStmt();
            UnidadesPreMain();
        }
    }

    private void DeclStmt() {
        Type();
        match(TeaToken.IDENTIFICADOR);
        DeclStmtSufixo();
    }

    private void Type() {
        if (tokenAtualEh(TeaToken.INT)) {
            match(TeaToken.INT);
        } else if (tokenAtualEh(TeaToken.FLOAT)) {
            match(TeaToken.FLOAT);
        } else if (tokenAtualEh(TeaToken.DOUBLE)) {
            match(TeaToken.DOUBLE);
        } else if (tokenAtualEh(TeaToken.CHAR)) {
            match(TeaToken.CHAR);
        } else if (tokenAtualEh(TeaToken.BOOLEAN)) {
            match(TeaToken.BOOLEAN);
        } else {
            erro();
        }
    }

    private void DeclStmtSufixo() {
        if (tokenAtualEh(TeaToken.COLCHETE_ESQ)) {
            match(TeaToken.COLCHETE_ESQ);
            match(TeaToken.NUMERO);
            match(TeaToken.COLCHETE_DIR);
            match(TeaToken.TERMINAL);
        } else if (pertenceFirst("DeclStmt1")) {
            DeclStmt1();
        }
    }

    private void DeclStmt1() {
        if (tokenAtualEh(TeaToken.ATRIBUICAO)) {
            match(TeaToken.ATRIBUICAO);
            Expr();
            match(TeaToken.TERMINAL);
        } else if (tokenAtualEh(TeaToken.TERMINAL)) {
            match(TeaToken.TERMINAL);
        }
    }

    private void Expr() {
        ExprLog();
        ExprSufixo();
    }

    private void ExprLog() {
        ExprRel();
        ExprLog1();
    }

    private void ExprRel() {
        ExprComp();
        ExprRel1();
    }

    private void ExprComp() {
        ExprNot();
        ExprComp1();
    }

    private void ExprComp1() {
        if (tokenAtualEh(TeaToken.MENOR_QUE)) {
            match(TeaToken.MENOR_QUE);
            ExprArit();
        } else if (tokenAtualEh(TeaToken.MENOR_IGUAL)) {
            match(TeaToken.MENOR_IGUAL);
            ExprArit();
        } else if (tokenAtualEh(TeaToken.MAIOR_QUE)) {
            match(TeaToken.MAIOR_QUE);
            ExprArit();
        } else if (tokenAtualEh(TeaToken.MAIOR_IGUAL)) {
            match(TeaToken.MAIOR_IGUAL);
            ExprArit();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //    match(TeaToken.EPS);
        //}
    }

    private void ExprArit() {
        Termo();
        ExprArit1();
    }

    private void Termo() {
        Fator();
        Termo1();
    }

    private void Fator() {
        if (tokenAtualEh(TeaToken.PAREN_ESQ)) {
            match(TeaToken.PAREN_ESQ);
            Expr();
        } else if (tokenAtualEh(TeaToken.NUMERO)) {
            match(TeaToken.NUMERO);
        } else if (tokenAtualEh(TeaToken.PONTO_FLUTUANTE)) {
            match(TeaToken.PONTO_FLUTUANTE);
        } else if (tokenAtualEh(TeaToken.IDENTIFICADOR)) {
            match(TeaToken.IDENTIFICADOR);
            FatorSufixo();
        }
    }

    private void FatorSufixo() {
        if (tokenAtualEh(TeaToken.PAREN_ESQ)) {
            match(TeaToken.PAREN_ESQ);
            ArgList();
            match(TeaToken.PAREN_DIR);
        } else if (tokenAtualEh(TeaToken.COLCHETE_ESQ)) {
            match(TeaToken.COLCHETE_ESQ);
            match(TeaToken.NUMERO);
            match(TeaToken.COLCHETE_DIR);
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        // match(TeaToken.EPS);
        //}
    }

    private void ArgList() {
        if (pertenceFirst("Expr")) {
            Expr();
            ArgListTail();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ArgListTail() {
        if (tokenAtualEh(TeaToken.VIRGULA)) {
            match(TeaToken.VIRGULA);
            Expr();
            ArgListTail();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ExprLog1() {
        if (tokenAtualEh(TeaToken.AND)) {
            match(TeaToken.AND);
            ExprRel();
            ExprLog1();
        }
    }

    private void Termo1() {
        if (tokenAtualEh(TeaToken.MUL)) {
            match(TeaToken.MUL);
            Fator();
            Termo1();
        } else if (tokenAtualEh(TeaToken.DIV)) {
            match(TeaToken.DIV);
            Fator();
            Termo1();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ExprArit1() {
        if (tokenAtualEh(TeaToken.SOMA)) {
            match(TeaToken.SOMA);
            Termo();
            ExprArit1();
        } else if (tokenAtualEh(TeaToken.SUB)) {
            match(TeaToken.SUB);
            Termo();
            ExprArit1();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ExprNot() {
        if (tokenAtualEh(TeaToken.NOT)) {
            match(TeaToken.NOT);
            ExprNot();
        } else if (pertenceFirst("ExprArit")) {
            ExprArit();
        }
    }

    private void ExprRel1() {
        if (tokenAtualEh(TeaToken.IGUAL)) {
            match(TeaToken.IGUAL);
            ExprComp();
            ExprRel1();
        } else if (tokenAtualEh(TeaToken.DIFERENTE)) {
            match(TeaToken.DIFERENTE);
            ExprComp();
            ExprRel1();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ExprSufixo() {
        if (tokenAtualEh(TeaToken.ATRIBUICAO)) {
            match(TeaToken.ATRIBUICAO);
            Expr();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //    match(TeaToken.EPS);
        //}
    }

    //FIM dos "filhos" de UnidadesPreMain()

    //INICIO dos "filhos" de FuncMain()

    private void FuncMain() {
        if (tokenAtualEh(TeaToken.MAIN)) {
            match(TeaToken.MAIN);
            match(TeaToken.PAREN_ESQ);
            match(TeaToken.PAREN_DIR);
            BlocoStmt();
        }
    }

    private void BlocoStmt() {
        if (tokenAtualEh(TeaToken.CHAVE_ESQ)) {
            match(TeaToken.CHAVE_ESQ);
            BlocoInstList();
            match(TeaToken.CHAVE_DIR);
        }
    }

    private void BlocoInstList() {
        if (pertenceFirst("BlocoInst")) {
            BlocoInst();
            BlocoInstList();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void BlocoInst() {
        Stmt();
    }

    private void Stmt() {
        if (tokenAtualEh(TeaToken.IDENTIFICADOR)) {
            match(TeaToken.IDENTIFICADOR);
            StmtIdTail();
            match(TeaToken.TERMINAL);
        } else if (pertenceFirst("DeclStmt")) {
            DeclStmt();
        } else if (pertenceFirst("IfStmt")) {
            IfStmt();
        } else if (pertenceFirst("WhileStmt")) {
            WhileStmt();
        } else if (pertenceFirst("ForStmt")) {
            ForStmt();
        } else if (pertenceFirst("DoStmt")) {
            DoStmt();
        } else if (pertenceFirst("SwitchStmt")) {
            SwitchStmt();
        } else if (pertenceFirst("ReturnStmt")) {
            ReturnStmt();
        } else if (pertenceFirst("BlocoStmt")) {
            BlocoStmt();
        } else if (pertenceFirst("BreakStmt")) {
            BreakStmt();
        } else if (pertenceFirst("EmptyStmt")) {
            EmptyStmt();
        }
    }


    private void StmtIdTail() {
        if (tokenAtualEh(TeaToken.PAREN_ESQ)) {
            match(TeaToken.PAREN_ESQ);
            ArgList();
            match(TeaToken.PAREN_DIR);
        } else if (tokenAtualEh(TeaToken.COLCHETE_ESQ)) {
            match(TeaToken.COLCHETE_ESQ);
            match(TeaToken.NUMERO);
            match(TeaToken.COLCHETE_DIR);
            match(TeaToken.ATRIBUICAO);
            Expr();
        } else if (tokenAtualEh(TeaToken.ATRIBUICAO)) {
            match(TeaToken.ATRIBUICAO);
            Expr();
        }
    }

    private void IfStmt() {
        if (tokenAtualEh(TeaToken.IF)) {
            match(TeaToken.IF);
            match(TeaToken.PAREN_ESQ);
            Expr();
            match(TeaToken.PAREN_DIR);
            BlocoStmt();
            OptionalElif();
        }
    }

    private void OptionalElif() {
        if (tokenAtualEh(TeaToken.ELIF)) {
            match(TeaToken.ELIF);
            match(TeaToken.PAREN_ESQ);
            Expr();
            match(TeaToken.PAREN_DIR);
            BlocoStmt();
            OptionalElif();
        } else if (pertenceFirst("OptionalElse")) {
            OptionalElse();
        }
    }

    private void OptionalElse() {
        if (tokenAtualEh(TeaToken.ELSE)) {
            match(TeaToken.ELSE);
            BlocoStmt();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //   match(TeaToken.EPS);
        //}
    }

    private void WhileStmt() {
        if (tokenAtualEh(TeaToken.WHILE)) {
            match(TeaToken.WHILE);
            match(TeaToken.PAREN_ESQ);
            Expr();
            match(TeaToken.PAREN_DIR);
            Stmt();
        }
    }

    private void ForStmt() {
        if (tokenAtualEh(TeaToken.FOR)) {
            match(TeaToken.FOR);
            match(TeaToken.PAREN_ESQ);
            ForInit();
            match(TeaToken.TERMINAL);
            ForCond();
            match(TeaToken.TERMINAL);
            ForUpdate();
            match(TeaToken.PAREN_DIR);
            Stmt();
        }
    }

    private void ForInit() {
        if (pertenceFirst("Type")) {
            Type();
            match(TeaToken.IDENTIFICADOR);
            match(TeaToken.ATRIBUICAO);
            Expr();
        } else if (tokenAtualEh(TeaToken.IDENTIFICADOR)) {
            match(TeaToken.IDENTIFICADOR);
            match(TeaToken.ATRIBUICAO);
            Expr();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //   match(TeaToken.EPS);
        //}
    }

    private void ForCond() {
        if (pertenceFirst("Expr")) {
            Expr();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //    match(TeaToken.EPS);
        //}
    }

    private void ForUpdate() {
        if (tokenAtualEh(TeaToken.IDENTIFICADOR)) {
            match(TeaToken.IDENTIFICADOR);
            match(TeaToken.ATRIBUICAO);
            ExprArit();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //    match(TeaToken.EPS);
        //}
    }

    private void DoStmt() {
        if (tokenAtualEh(TeaToken.DO)) {
            match(TeaToken.DO);
            Stmt();
            match(TeaToken.WHILE);
            match(TeaToken.PAREN_ESQ);
            Expr();
            match(TeaToken.PAREN_DIR);
            match(TeaToken.TERMINAL);
        }
    }

    private void SwitchStmt() {
        if (tokenAtualEh(TeaToken.SWITCH)) {
            match(TeaToken.SWITCH);
            match(TeaToken.PAREN_ESQ);
            ExprArit();
            match(TeaToken.PAREN_DIR);
            match(TeaToken.CHAVE_ESQ);
            CaseSectionList();
            match(TeaToken.CHAVE_DIR);
        }
    }

    private void CaseSectionList() {
        if (pertenceFirst("CaseSection")) {
            CaseSection();
            CaseSectionList();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void CaseSection() {
        if (pertenceFirst("CaseLabels")) {
            CaseLabels();
            CaseBody();
        }
    }

    private void CaseLabels() {
        if (tokenAtualEh(TeaToken.CASE)) {
            match(TeaToken.CASE);
            ExprArit();
            match(TeaToken.DOIS_PONTOS);
            CaseLabels();
        } else if (tokenAtualEh(TeaToken.DEFAULT)) {
            match(TeaToken.DEFAULT);
            match(TeaToken.DOIS_PONTOS);
        }
    }

    private void CaseBody() {
        if (pertenceFirst("Stmt")) {
            Stmt();
            CaseBody();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ReturnStmt() {
        if (tokenAtualEh(TeaToken.RETURN)) {
            match(TeaToken.RETURN);
            ReturnExpr();
            match(TeaToken.TERMINAL);
        }
    }

    private void ReturnExpr() {
        if (pertenceFirst("Expr")) {
            Expr();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //   match(TeaToken.EPS);
        //}
    }

    private void BreakStmt() {
        if (tokenAtualEh(TeaToken.BREAK)) {
            match(TeaToken.BREAK);
            match(TeaToken.TERMINAL);
        }
    }

    private void EmptyStmt() {
        if (tokenAtualEh(TeaToken.TERMINAL)) {
            match(TeaToken.TERMINAL);
        }
    }

    //FIM dos "filhos" de FuncMain

    //INICIO dos "filhos" de UnidadesPosMain

    private void UnidadesPosMain() {
        if (pertenceFirst("FuncDecl")) {
            FuncDecl();
            UnidadesPosMain();
        }
        //else if (tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void FuncDecl() {
        if (pertenceFirst("TypeORtypeArray")) {
            TypeORtypeArray();
            match(TeaToken.IDENTIFICADOR);
            match(TeaToken.PAREN_ESQ);
            ParamList();
            match(TeaToken.PAREN_DIR);
            BlocoStmt();
        }
    }

    private void TypeORtypeArray() {
        if (pertenceFirst("Type")) {
            Type();
            TypeSufixo();
        }
    }

    private void TypeSufixo() {
        if (tokenAtualEh(TeaToken.COLCHETE_ESQ)) {
            match(TeaToken.COLCHETE_ESQ);
            match(TeaToken.COLCHETE_DIR);
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ParamList() {
        if (pertenceFirst("Param")) {
            Param();
            ParamListTail();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void Param() {
        if (pertenceFirst("Type")) {
            Type();
            match(TeaToken.IDENTIFICADOR);
            ParamSufixo();
        }
    }

    private void ParamSufixo() {
        if (tokenAtualEh(TeaToken.COLCHETE_ESQ)) {
            match(TeaToken.COLCHETE_ESQ);
            match(TeaToken.COLCHETE_DIR);
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    private void ParamListTail() {
        if (tokenAtualEh(TeaToken.VIRGULA)) {
            match(TeaToken.VIRGULA);
            Param();
            ParamListTail();
        }
        //else if(tokenAtualEh(TeaToken.EPS)){
        //  match(TeaToken.EPS);
        //}
    }

    //FIM dos "filhos" de UnidadesPosMain

    private void match(TeaToken esperado) { //consome o token caso de match
        if (tokenAtualEh(esperado)) {
            avancar();
        } else {
            erro();
        }
    }

    private void avancar() {
        apontadorTokens++;
        if (apontadorTokens < listaTokens.size()) {
            tokenAtual = listaTokens.get(apontadorTokens);
        }
    }

    private boolean tokenAtualEh(TeaToken esperado) {
        return tokenAtual.getTipo() == esperado;
    }

    private Token getAtual() {
        return tokenAtual;
    }

    private boolean pertenceFirst(String NT) { //verifica se o token atual faz parte do conjunto FIRST de um nao terminal
        Set<String> firstSet = gramaticaTea.getFirst().get(NT);
        String nomeTokenAtual = tokenAtual.getTipo().name();
        return firstSet.contains(nomeTokenAtual);
    }

    private boolean ehTerminal(String simbolo) {
        if (simbolo.equals("$")) {
            return true;
        }

        for (TeaToken teaToken : TeaToken.values()) {
            if (teaToken.name().equals(simbolo)) {
                return true;
            }
        }

        return false;
    }

    private String getTipoToken(Token token) {
        String tokenName = token.getTipo().name();
        if (tokenName.equals("EOF")) {
            tokenName = "$";
        }

        return tokenName;
    }

    private void erro() {
        throw new RuntimeException("Erro sintático!");
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.analisarSintaxeRecursivaDescent("main() { int contador = 0; while (contador < 5) { contador = contador + 1;} if (contador == 5) {}}");
    }
}