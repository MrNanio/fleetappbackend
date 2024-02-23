package pl.nankiewic.fleetappbackend.dto.repair;

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
public class RepairModifyDTO {

    @NotNull(groups = ObjectModificationValidation.class)
    private Long id;

    @NotNull
    private Long vehicleId;

    @NotNull
    private String title;

    @NotNull
    private LocalDate repairDate;

    @NotNull
    private BigDecimal cost;

    private String description;

}
