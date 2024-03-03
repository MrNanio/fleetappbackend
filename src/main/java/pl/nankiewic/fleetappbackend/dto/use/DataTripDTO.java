package pl.nankiewic.fleetappbackend.dto.use;

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
