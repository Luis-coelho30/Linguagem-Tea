package analise_sintatica.gramatica;

import analise_lexica.TeaToken;

import java.util.*;
import java.util.Arrays;

public class GramaticaTeaBuilder {

    public Gramatica construirGramaticaTea() {
        Gramatica gramatica = new Gramatica(new NaoTerminal("programa"));

        // Não-terminais
        NaoTerminal programa = new NaoTerminal("programa");
        NaoTerminal unidadesPreMain = new NaoTerminal("unidadesPreMain");
        NaoTerminal funcMain = new NaoTerminal("funcMain");
        NaoTerminal unidadesPosMain = new NaoTerminal("unidadesPosMain");

        NaoTerminal declGlobal = new NaoTerminal("declGlobal");
        NaoTerminal funcDecl = new NaoTerminal("funcDecl");
        NaoTerminal paramList = new NaoTerminal("paramList");
        NaoTerminal paramListTail = new NaoTerminal("paramListTail");
        NaoTerminal param = new NaoTerminal("param");
        NaoTerminal paramSufixo = new NaoTerminal("paramSufixo");

        NaoTerminal stmt = new NaoTerminal("stmt");
        NaoTerminal stmtList = new NaoTerminal("stmtList");

        NaoTerminal declStmt = new NaoTerminal("declStmt");
        NaoTerminal declStmtSufixo = new NaoTerminal("declStmtSufixo");
        NaoTerminal declStmt1 = new NaoTerminal("declStmt1");

        NaoTerminal typeORtypeArray = new NaoTerminal("typeORtypeArray");
        NaoTerminal typeArray = new NaoTerminal("typeArray");
        NaoTerminal type = new NaoTerminal("type");

        NaoTerminal ifStmt = new NaoTerminal("ifStmt");
        NaoTerminal elifList = new NaoTerminal("elifList");
        NaoTerminal elif = new NaoTerminal("elif");
        NaoTerminal elsePart = new NaoTerminal("elsePart");

        NaoTerminal whileStmt = new NaoTerminal("whileStmt");
        NaoTerminal forStmt = new NaoTerminal("forStmt");
        NaoTerminal forInit = new NaoTerminal("forInit");
        NaoTerminal forAssign = new NaoTerminal("forAssign");
        NaoTerminal forCond = new NaoTerminal("forCond");
        NaoTerminal forUpdate = new NaoTerminal("forUpdate");

        NaoTerminal doStmt = new NaoTerminal("doStmt");
        NaoTerminal switchStmt = new NaoTerminal("switchStmt");
        NaoTerminal caseList = new NaoTerminal("caseList");
        NaoTerminal caseClause = new NaoTerminal("caseClause");
        NaoTerminal breakStmt = new NaoTerminal("breakStmt");
        NaoTerminal defaultPart = new NaoTerminal("defaultPart");

        NaoTerminal assignStmt = new NaoTerminal("assignStmt");
        NaoTerminal funcCallStmt = new NaoTerminal("funcCallStmt");
        NaoTerminal returnStmt = new NaoTerminal("returnStmt");
        NaoTerminal returnExpr = new NaoTerminal("returnExpr");

        NaoTerminal blocoStmt = new NaoTerminal("blocoStmt");
        NaoTerminal emptyStmt = new NaoTerminal("emptyStmt");

        NaoTerminal expr = new NaoTerminal("expr");
        NaoTerminal assignExpr = new NaoTerminal("assignExpr");

        NaoTerminal exprLog = new NaoTerminal("exprLog");
        NaoTerminal exprLog1 = new NaoTerminal("exprLog1");

        NaoTerminal exprRel = new NaoTerminal("exprRel");
        NaoTerminal exprRel1 = new NaoTerminal("exprRel1");

        NaoTerminal exprComp = new NaoTerminal("exprComp");
        NaoTerminal exprComp1 = new NaoTerminal("exprComp1");

        NaoTerminal exprNot = new NaoTerminal("exprNot");
        NaoTerminal exprArit = new NaoTerminal("exprArit");
        NaoTerminal exprArit1 = new NaoTerminal("exprArit1");

        NaoTerminal termo = new NaoTerminal("termo");
        NaoTerminal termo1 = new NaoTerminal("termo1");

        NaoTerminal fator = new NaoTerminal("fator");
        NaoTerminal fatorSufixo = new NaoTerminal("fatorSufixo");

        NaoTerminal argList = new NaoTerminal("argList");
        NaoTerminal argListTail = new NaoTerminal("argListTail");

        NaoTerminal num = new NaoTerminal("num");

        // Terminais (só os que aparecem explicitamente)
        Terminal tMain = new Terminal(TeaToken.MAIN);
        Terminal tId = new Terminal(TeaToken.IDENTIFICADOR);
        Terminal tIn = new Terminal(TeaToken.NUMERO);
        Terminal tPf = new Terminal(TeaToken.PONTO_FLUTUANTE);
        Terminal tInt = new Terminal(TeaToken.INT);
        Terminal tFloat = new Terminal(TeaToken.FLOAT);
        Terminal tDouble = new Terminal(TeaToken.FLOAT);
        Terminal tChar = new Terminal(TeaToken.CHAR);
        Terminal tBoolean = new Terminal(TeaToken.BOOL);

        Terminal tTrue = new Terminal(TeaToken.BOOL);
        Terminal tFalse = new Terminal(TeaToken.BOOL);

        Terminal tOpenPar = new Terminal(TeaToken.PAREN_ESQ);
        Terminal tClosePar = new Terminal(TeaToken.PAREN_DIR);
        Terminal tOpenBrace = new Terminal(TeaToken.CHAVE_ESQ);
        Terminal tCloseBrace = new Terminal(TeaToken.CHAVE_DIR);
        Terminal tSemicolon = new Terminal(TeaToken.TERMINAL);
        Terminal tComma = new Terminal(TeaToken.VIRGULA);
        Terminal tEqual = new Terminal(TeaToken.ATRIBUICAO);
        Terminal tNot = new Terminal(TeaToken.NOT);
        Terminal tAnd = new Terminal(TeaToken.AND);
        Terminal tOr = new Terminal(TeaToken.OR);
        Terminal tLess = new Terminal(TeaToken.MENOR_QUE);
        Terminal tLessEqual = new Terminal(TeaToken.MENOR_IGUAL);
        Terminal tGreater = new Terminal(TeaToken.MAIOR_QUE);
        Terminal tGreaterEqual = new Terminal(TeaToken.MAIOR_IGUAL);
        Terminal tEqEq = new Terminal(TeaToken.IGUAL);
        Terminal tNotEq = new Terminal(TeaToken.DIFERENTE);
        Terminal tPlus = new Terminal(TeaToken.SOMA);
        Terminal tMinus = new Terminal(TeaToken.SUB);
        Terminal tMul = new Terminal(TeaToken.MUL);
        Terminal tDiv = new Terminal(TeaToken.DIV);
        Terminal tWhile = new Terminal(TeaToken.WHILE);
        Terminal tDo = new Terminal(TeaToken.DO);
        Terminal tFor = new Terminal(TeaToken.FOR);
        Terminal tIf = new Terminal(TeaToken.IF);
        Terminal tElif = new Terminal(TeaToken.ELIF);
        Terminal tElse = new Terminal(TeaToken.ELSE);
        Terminal tSwitch = new Terminal(TeaToken.SWITCH);
        Terminal tCase = new Terminal(TeaToken.CASE);
        Terminal tDefault = new Terminal(TeaToken.DEFAULT);
        Terminal tBreak = new Terminal(TeaToken.BREAK);
        Terminal tReturn = new Terminal(TeaToken.RETURN);
        Terminal tOpenBracket = new Terminal(TeaToken.COLCHETE_ESQ);
        Terminal tCloseBracket = new Terminal(TeaToken.COLCHETE_DIR);

        // programa ::= unidadesPreMain funcMain unidadesPosMain
        gramatica.adicionarRegra(new RegraProd(programa, Arrays.asList(unidadesPreMain, funcMain, unidadesPosMain)));

        // unidadesPreMain ::= declGlobal unidadesPreMain | ε
        gramatica.adicionarRegra(new RegraProd(unidadesPreMain, Arrays.asList(declGlobal, unidadesPreMain)));
        gramatica.adicionarRegra(new RegraProd(unidadesPreMain, Collections.emptyList()));

        // funcMain ::= "main" "(" ")" blocoStmt
        gramatica.adicionarRegra(new RegraProd(funcMain, Arrays.asList(tMain, tOpenPar, tClosePar, blocoStmt)));

        // unidadesPosMain ::= funcDecl unidadesPosMain | ε
        gramatica.adicionarRegra(new RegraProd(unidadesPosMain, Arrays.asList(funcDecl, unidadesPosMain)));
        gramatica.adicionarRegra(new RegraProd(unidadesPosMain, Collections.emptyList()));

        // declGlobal ::= type "id" declStmtSufixo
        gramatica.adicionarRegra(new RegraProd(declGlobal, Arrays.asList(type, tId, declStmtSufixo)));

        // funcDecl ::= typeORtypeArray "id" "(" paramList ")" blocoStmt
        gramatica.adicionarRegra(new RegraProd(funcDecl, Arrays.asList(typeORtypeArray, tId, tOpenPar, paramList, tClosePar, blocoStmt)));

        // paramList ::= param paramListTail | ε
        gramatica.adicionarRegra(new RegraProd(paramList, Arrays.asList(param, paramListTail)));
        gramatica.adicionarRegra(new RegraProd(paramList, Collections.emptyList()));

        // paramListTail ::= "," param paramListTail | ε
        gramatica.adicionarRegra(new RegraProd(paramListTail, Arrays.asList(tComma, param, paramListTail)));
        gramatica.adicionarRegra(new RegraProd(paramListTail, Collections.emptyList()));

        // param ::= type paramSufixo
        gramatica.adicionarRegra(new RegraProd(param, Arrays.asList(type, paramSufixo)));

        // paramSufixo ::= "id" | "id" "[" "]"
        gramatica.adicionarRegra(new RegraProd(paramSufixo, List.of(tId)));
        gramatica.adicionarRegra(new RegraProd(paramSufixo, Arrays.asList(tId, tOpenBracket, tCloseBracket)));

        // stmt ::= declStmt | ifStmt | whileStmt | forStmt | doStmt | switchStmt | assignStmt | funcCallStmt | returnStmt | blocoStmt | breakStmt | emptyStmt
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(declStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(ifStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(whileStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(forStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(doStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(switchStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(assignStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(funcCallStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(returnStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(blocoStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(breakStmt)));
        gramatica.adicionarRegra(new RegraProd(stmt, List.of(emptyStmt)));

        // stmtList ::= stmt stmtList | ε
        gramatica.adicionarRegra(new RegraProd(stmtList, Arrays.asList(stmt, stmtList)));
        gramatica.adicionarRegra(new RegraProd(stmtList, Collections.emptyList()));

        // declStmt ::= type "id" declStmtSufixo
        gramatica.adicionarRegra(new RegraProd(declStmt, Arrays.asList(type, tId, declStmtSufixo)));

        // declStmt1 ::= "=" expr ";" | ";"
        gramatica.adicionarRegra(new RegraProd(declStmt1, Arrays.asList(tEqual, expr, tSemicolon)));
        gramatica.adicionarRegra(new RegraProd(declStmt1, List.of(tSemicolon)));

        // declStmtSufixo ::= "[" num "]" ";" | declStmt1
        gramatica.adicionarRegra(new RegraProd(declStmtSufixo, List.of(tOpenBracket, tIn, tCloseBracket, tSemicolon)));
        gramatica.adicionarRegra(new RegraProd(declStmtSufixo, List.of(declStmt1)));

        // typeORtypeArray ::= type | typeArray
        gramatica.adicionarRegra(new RegraProd(typeORtypeArray, List.of(type)));
        gramatica.adicionarRegra(new RegraProd(typeORtypeArray, List.of(typeArray)));

        // typeArray ::= type "[" "]"
        gramatica.adicionarRegra(new RegraProd(typeArray, Arrays.asList(type, tOpenBracket, tCloseBracket)));

        // type ::= "int" | "float" | "double" | "char" | "boolean"
        gramatica.adicionarRegra(new RegraProd(type, List.of(tInt)));
        gramatica.adicionarRegra(new RegraProd(type, List.of(tFloat)));
        gramatica.adicionarRegra(new RegraProd(type, List.of(tDouble)));
        gramatica.adicionarRegra(new RegraProd(type, List.of(tChar)));
        gramatica.adicionarRegra(new RegraProd(type, List.of(tBoolean)));

        // ifStmt ::= "if" "(" expr ")" stmt elifList elsePart
        gramatica.adicionarRegra(new RegraProd(ifStmt, Arrays.asList(tIf, tOpenPar, expr, tClosePar, stmt, elifList, elsePart)));

        // elifList ::= elif elifList | ε
        gramatica.adicionarRegra(new RegraProd(elifList, Arrays.asList(elif, elifList)));
        gramatica.adicionarRegra(new RegraProd(elifList, Collections.emptyList()));

        // elif ::= "elif" "(" expr ")" stmt
        gramatica.adicionarRegra(new RegraProd(elif, Arrays.asList(tElif, tOpenPar, expr, tClosePar, stmt)));

        // elsePart ::= "else" stmt | ε
        gramatica.adicionarRegra(new RegraProd(elsePart, Arrays.asList(tElse, stmt)));
        gramatica.adicionarRegra(new RegraProd(elsePart, Collections.emptyList()));

        // whileStmt ::= "while" "(" expr ")" stmt
        gramatica.adicionarRegra(new RegraProd(whileStmt, Arrays.asList(tWhile, tOpenPar, expr, tClosePar, stmt)));

        // forStmt ::= "for" "(" forInit forCond ";" forUpdate ")" stmt
        gramatica.adicionarRegra(new RegraProd(forStmt, Arrays.asList(tFor, tOpenPar, forInit, forCond, tSemicolon, forUpdate, tClosePar, stmt)));

        // forInit ::= forAssign | emptyStmt
        gramatica.adicionarRegra(new RegraProd(forInit, List.of(forAssign)));
        gramatica.adicionarRegra(new RegraProd(forInit, List.of(emptyStmt)));

        // forCond ::= expr | emptyStmt
        gramatica.adicionarRegra(new RegraProd(forCond, List.of(expr)));
        gramatica.adicionarRegra(new RegraProd(forCond, List.of(emptyStmt)));

        // forUpdate ::= assignStmt | emptyStmt
        gramatica.adicionarRegra(new RegraProd(forUpdate, Arrays.asList(tId, tEqual, exprArit)));
        gramatica.adicionarRegra(new RegraProd(forUpdate, List.of(emptyStmt)));

        // doStmt ::= "do" stmt "while" "(" expr ")" ";"
        gramatica.adicionarRegra(new RegraProd(doStmt, Arrays.asList(tDo, stmt, tWhile, tOpenPar, expr, tClosePar, tSemicolon)));

        // switchStmt ::= "switch" "(" exprArit ")" "{" caseList defaultPart "}"
        gramatica.adicionarRegra(new RegraProd(switchStmt, Arrays.asList(tSwitch, tOpenPar, exprArit, tClosePar, tOpenBrace, caseList, defaultPart, tCloseBrace)));

        // caseList ::= caseClause caseList | ε
        gramatica.adicionarRegra(new RegraProd(caseList, Arrays.asList(caseClause, caseList)));
        gramatica.adicionarRegra(new RegraProd(caseList, Collections.emptyList()));

        // caseClause ::= "case" exprArit ":" stmtList breakStmt
        gramatica.adicionarRegra(new RegraProd(caseClause, Arrays.asList(tCase, exprArit, new Terminal(TeaToken.DOIS_PONTOS), stmtList, breakStmt)));

        // defaultPart ::= "default" ":" stmtList | ε
        gramatica.adicionarRegra(new RegraProd(defaultPart, Arrays.asList(tDefault, new Terminal(TeaToken.DOIS_PONTOS), stmtList)));
        gramatica.adicionarRegra(new RegraProd(defaultPart, Collections.emptyList()));

        // breakStmt ::= "break" ";"
        gramatica.adicionarRegra(new RegraProd(breakStmt, Arrays.asList(tBreak, tSemicolon)));

        // assignStmt ::= "id" assignExpr ";"
        gramatica.adicionarRegra(new RegraProd(assignStmt, Arrays.asList(tId, assignExpr, tSemicolon)));

        // funcCallStmt ::= "id" "(" argList ")" ";"
        gramatica.adicionarRegra(new RegraProd(funcCallStmt, Arrays.asList(tId, tOpenPar, argList, tClosePar, tSemicolon)));

        // returnStmt ::= "return" returnExpr ";"
        gramatica.adicionarRegra(new RegraProd(returnStmt, Arrays.asList(tReturn, returnExpr, tSemicolon)));

        // returnExpr ::= expr | ε
        gramatica.adicionarRegra(new RegraProd(returnExpr, List.of(expr)));
        gramatica.adicionarRegra(new RegraProd(returnExpr, Collections.emptyList()));

        // blocoStmt ::= "{" stmtList "}"
        gramatica.adicionarRegra(new RegraProd(blocoStmt, Arrays.asList(tOpenBrace, stmtList, tCloseBrace)));

        // emptyStmt ::= ";"
        gramatica.adicionarRegra(new RegraProd(emptyStmt, List.of(tSemicolon)));

        // expr ::= assignExpr
        gramatica.adicionarRegra(new RegraProd(expr, List.of(assignExpr)));

        // assignExpr ::= "id" "=" assignExpr | exprLog
        gramatica.adicionarRegra(new RegraProd(assignExpr, Arrays.asList(tId, tEqual, assignExpr)));
        gramatica.adicionarRegra(new RegraProd(assignExpr, List.of(exprLog)));

        // exprLog ::= exprRel exprLog1
        gramatica.adicionarRegra(new RegraProd(exprLog, Arrays.asList(exprRel, exprLog1)));

        // exprLog1 ::= "AND" exprRel exprLog1 | "OR" exprRel exprLog1 | ε
        gramatica.adicionarRegra(new RegraProd(exprLog1, Arrays.asList(tAnd, exprRel, exprLog1)));
        gramatica.adicionarRegra(new RegraProd(exprLog1, Arrays.asList(tOr, exprRel, exprLog1)));
        gramatica.adicionarRegra(new RegraProd(exprLog1, Collections.emptyList()));

        // exprRel ::= exprComp exprRel1
        gramatica.adicionarRegra(new RegraProd(exprRel, Arrays.asList(exprComp, exprRel1)));

        // exprRel1 ::= "==" exprComp exprRel1 | "!=" exprComp exprRel1 | ε
        gramatica.adicionarRegra(new RegraProd(exprRel1, Arrays.asList(tEqEq, exprComp, exprRel1)));
        gramatica.adicionarRegra(new RegraProd(exprRel1, Arrays.asList(tNotEq, exprComp, exprRel1)));

        // exprComp ::= exprNot exprComp1
        gramatica.adicionarRegra(new RegraProd(exprComp, Arrays.asList(exprNot, exprComp1)));

        // exprComp1 ::= "<" exprArit | "<=" exprArit | ">" exprArit | ">=" exprArit | ε
        gramatica.adicionarRegra(new RegraProd(exprComp1, Arrays.asList(tLess, exprArit)));
        gramatica.adicionarRegra(new RegraProd(exprComp1, Arrays.asList(tLessEqual, exprArit)));
        gramatica.adicionarRegra(new RegraProd(exprComp1, Arrays.asList(tGreater, exprArit)));
        gramatica.adicionarRegra(new RegraProd(exprComp1, Arrays.asList(tGreaterEqual, exprArit)));
        gramatica.adicionarRegra(new RegraProd(exprComp1, Collections.emptyList()));

        // exprNot ::= "!" exprNot | exprArit
        gramatica.adicionarRegra(new RegraProd(exprNot, Arrays.asList(tNot, exprNot)));
        gramatica.adicionarRegra(new RegraProd(exprNot, List.of(exprArit)));

        // exprArit ::= termo exprArit1
        gramatica.adicionarRegra(new RegraProd(exprArit, Arrays.asList(termo, exprArit1)));

        // exprArit1 ::= "+" termo exprArit1 | "-" termo exprArit1 | ε
        gramatica.adicionarRegra(new RegraProd(exprArit1, Arrays.asList(tPlus, termo, exprArit1)));
        gramatica.adicionarRegra(new RegraProd(exprArit1, Arrays.asList(tMinus, termo, exprArit1)));
        gramatica.adicionarRegra(new RegraProd(exprArit1, Collections.emptyList()));

        // termo ::= fator termo1
        gramatica.adicionarRegra(new RegraProd(termo, Arrays.asList(fator, termo1)));

        // termo1 ::= "*" fator termo1 | "/" fator termo1 | ε
        gramatica.adicionarRegra(new RegraProd(termo1, Arrays.asList(tMul, fator, termo1)));
        gramatica.adicionarRegra(new RegraProd(termo1, Arrays.asList(tDiv, fator, termo1)));
        gramatica.adicionarRegra(new RegraProd(termo1, Collections.emptyList()));

        // argList ::= expr argListTail | ε
        gramatica.adicionarRegra(new RegraProd(argList, Arrays.asList(expr, argListTail)));
        gramatica.adicionarRegra(new RegraProd(argList, Collections.emptyList()));

        // argList1 ::= "," expr argList1 | ε
        gramatica.adicionarRegra(new RegraProd(argListTail, Arrays.asList(tComma, expr, argListTail)));
        gramatica.adicionarRegra(new RegraProd(argListTail, Collections.emptyList()));

        // fatorSufixo ::= "(" argList ")" | "[" "in" "]" | ε
        gramatica.adicionarRegra(new RegraProd(fatorSufixo, Arrays.asList(tOpenPar, argList, tClosePar)));
        gramatica.adicionarRegra(new RegraProd(fatorSufixo, Arrays.asList(tOpenBracket, tIn, tCloseBracket)));
        gramatica.adicionarRegra(new RegraProd(fatorSufixo, Collections.emptyList()));

        // fator ::= "(" expr ")" | num | "id" fatorSufixo | "true" | "false"
        gramatica.adicionarRegra(new RegraProd(fator, List.of(num)));
        gramatica.adicionarRegra(new RegraProd(fator, List.of(tId)));
        gramatica.adicionarRegra(new RegraProd(fator, List.of(tTrue)));
        gramatica.adicionarRegra(new RegraProd(fator, List.of(tFalse)));

        // num ::= "num"
        gramatica.adicionarRegra(new RegraProd(num, Arrays.asList(tIn, tPf)));

        return gramatica;
    }

}