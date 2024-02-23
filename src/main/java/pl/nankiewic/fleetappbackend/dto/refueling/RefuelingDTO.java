package pl.nankiewic.fleetappbackend.dto.refueling;

import lombok.*;
import pl.nankiewic.fleetappbackend.controller.validator.ObjectModificationValidation;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefuelingDTO {

    @NotNull(groups = ObjectModificationValidation.class)
    private Long id;

    @NotNull
    private Long vehicleId;

    @NotNull
    private Long userId;

    @NotNull
    private LocalDate refuelingDate;

    @NotNull
    private String litre;

    @NotNull
    private BigDecimal cost;

    private String description;

}
