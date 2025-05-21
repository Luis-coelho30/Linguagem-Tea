import analise_lexica.TeaToken;
import analise_lexica.Token;
import analise_lexica.Lexer;

import analise_sintatica.gramatica.Gramatica;
import analise_sintatica.gramatica.simbolo.Simbolos;
import analise_sintatica.gramatica.RegraProd;
import analise_sintatica.gramatica.GramaticaTea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class main {
    public static void main(String args[]){
        /*System.out.println("oi");
        Lexer lexer = new Lexer();
        ArrayList<Token> listaTokens = new ArrayList<>();
        listaTokens = lexer.analisarLexico("999 int ; 1 do while ((() ;;5; 4.5{}djfjfjfjf nickiminaj");
        System.out.println(listaTokens.toString());
         */

        GramaticaTea analisador = new GramaticaTea();
        analisador.calcularFirst();

        analisador.exibirFirsts();
    }
}