package erro;

public class CompileTimeException extends RuntimeException {
    public CompileTimeException(String message) {
        super(message);
    }

    public CompileTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
