package pl.nankiewic.fleetappbackend.dto.use;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.nankiewic.fleetappbackend.controller.validator.ObjectModificationValidation;
import pl.nankiewic.fleetappbackend.entity.enums.TripType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UseDTO {

    @NotNull(groups = ObjectModificationValidation.class)
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

    @NotNull
    private TripType tripType;

    private String description;

}
