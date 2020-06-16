package cz.jr.trailtour.backend.exceptions;

/**
 * Created by Jiří Rýdel on 6/16/20, 12:11 PM
 */
public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
