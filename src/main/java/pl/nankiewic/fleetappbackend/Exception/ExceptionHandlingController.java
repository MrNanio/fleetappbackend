package pl.nankiewic.fleetappbackend.Exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {



    @ExceptionHandler(EntityExistsException.class)
    public final ResponseEntity<Object> handleEntityBadRequest(EntityExistsException exception,
                                                               WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception,
                                                             WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

   @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundUserName(UsernameNotFoundException exception,
                                                                     WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public final ResponseEntity<Object> handleUsernameAlreadyTaken(UsernameAlreadyTakenException exception,
                                                                   WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(UserAccountEnabledException.class)
    public final ResponseEntity<Object> handleUserAccountEnabled(UserAccountEnabledException exception,
                                                                 WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredential(BadCredentialsException exception,
                                                            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), "niepoprawny e-mail lub hasło",
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }
    @ExceptionHandler(TokenException.class)
    public final ResponseEntity<Object> handleToken(TokenException exception,
                                                    WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(WrongOldPasswordException.class)
    public final ResponseEntity<Object> handleWrongPassword(WrongOldPasswordException exception,
                                                            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), "złe stare hasło",
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(PermissionDeniedException.class)
    public final ResponseEntity<Object> handlePermissionDenied(PermissionDeniedException exception,
                                                               WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), "brak uprawnień",
                request.getDescription(true));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }



}
