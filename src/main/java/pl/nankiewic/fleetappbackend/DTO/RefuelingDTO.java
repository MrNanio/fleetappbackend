package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefuelingDTO {
    private Long id;
    private Long vehicleId;
    private Long userId;
    private LocalDate refuelingDate;
    private String litre;
    private String cost;
    private String description;

}
