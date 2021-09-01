package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UseDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long vehicleId;
    @NotNull
    private Long userId;
    @NotNull
    private Short trip;
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyy")
    private LocalDate tripDate;
    private String tripType;
    private String description;

}
