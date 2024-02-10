package pl.nankiewic.fleetappbackend.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "user not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
