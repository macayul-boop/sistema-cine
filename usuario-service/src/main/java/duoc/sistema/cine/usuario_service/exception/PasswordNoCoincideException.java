package duoc.sistema.cine.usuario_service.exception;

public class PasswordNoCoincideException extends RuntimeException {
    public PasswordNoCoincideException(String message) {
        super(message);
    }
}
