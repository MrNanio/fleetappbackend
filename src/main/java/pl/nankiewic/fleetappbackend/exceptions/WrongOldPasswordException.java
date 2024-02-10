package pl.nankiewic.fleetappbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WrongOldPasswordException extends RuntimeException {
    public WrongOldPasswordException() {
    }
}
