package exception;

public class AnaliseLexicaException extends CompileTimeException {

    public AnaliseLexicaException(String message) {
        super(message);
    }

    public AnaliseLexicaException(String message, Throwable cause) {
        super(message, cause);
    }
}
