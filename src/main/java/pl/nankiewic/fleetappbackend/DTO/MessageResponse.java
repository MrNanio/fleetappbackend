package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private String message;
    private LocalDateTime date;

}

