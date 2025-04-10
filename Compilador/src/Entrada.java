import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Scanner;

public class Entrada {

    File programa;

    public Entrada(File programa) {
        this.programa = programa;
    }

    public String lerArquivo() {
        String programaStr;
        try {

            programaStr = Files.readString(new Path("\programas\teste.txt"));

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao foi encontrado");
        }

        return programaStr;
    }
}
