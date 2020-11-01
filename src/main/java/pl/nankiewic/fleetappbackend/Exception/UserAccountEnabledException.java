package pl.nankiewic.fleetappbackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserAccountEnabledException extends RuntimeException{
    public UserAccountEnabledException() {
    }
    public UserAccountEnabledException(String message) {
        super(message);
    }

    public UserAccountEnabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountEnabledException(Throwable cause) {
        super(cause);
    }
}
