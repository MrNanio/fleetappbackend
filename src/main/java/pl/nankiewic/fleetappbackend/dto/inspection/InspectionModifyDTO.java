package pl.nankiewic.fleetappbackend.dto.inspection;

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
public class InspectionModifyDTO {

    @NotNull(groups = ObjectModificationValidation.class)
    private Long id;

    @NotNull
    private Long vehicleId;

    @NotNull
    private LocalDate inspectionDate;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private BigDecimal cost;

    private String description;

}
