package analise_sintatica.gramatica;

public class Main {
    public static void main(String[] args) {
        /*System.out.println("oi");
        Lexer lexer = new Lexer();
        ArrayList<Token> listaTokens = new ArrayList<>();
        listaTokens = lexer.analisarLexico("999 int ; 1 do while ((() ;;5; 4.5{}djfjfjfjf nickiminaj");
        System.out.println(listaTokens.toString());
         */

        GramaticaTea analisador = new GramaticaTea();

        analisador.imprimirTabelaLL1();
    }
}
