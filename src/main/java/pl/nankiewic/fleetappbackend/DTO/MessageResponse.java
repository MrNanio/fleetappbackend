package pl.nankiewic.fleetappbackend.DTO;

import java.time.LocalDateTime;

public class MessageResponse {
    private String message;
    private LocalDateTime date;

    public MessageResponse() {
    }

    public MessageResponse(String message, LocalDateTime date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

