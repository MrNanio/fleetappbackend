package pl.nankiewic.fleetappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsernameAlreadyTakenException extends RuntimeException {
    public  UsernameAlreadyTakenException(String message) {
        super(message);
    }

}
