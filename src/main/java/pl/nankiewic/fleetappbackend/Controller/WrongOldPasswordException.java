package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WrongOldPasswordException extends RuntimeException {
    public WrongOldPasswordException() {
    }

    public WrongOldPasswordException(String message) {
        super(message);
    }

    public WrongOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongOldPasswordException(Throwable cause) {
        super(cause);
    }
}
