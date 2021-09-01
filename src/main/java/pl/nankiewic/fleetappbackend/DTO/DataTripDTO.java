package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataTripDTO {
    private Short value;
    private LocalDate date;

}
