package pl.nankiewic.fleetappbackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsernameAlreadyTakenException extends RuntimeException {
    public  UsernameAlreadyTakenException() {
        super();
    }
    public  UsernameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
    public  UsernameAlreadyTakenException(String message) {
        super(message);
    }
    public  UsernameAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}
