package pl.nankiewic.fleetappbackend.dto.insurance;

import lombok.*;
import pl.nankiewic.fleetappbackend.controller.validator.ObjectModificationValidation;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceModifyDTO {

    @NotNull(groups = ObjectModificationValidation.class)
    private Long id;

    @NotNull
    private Long vehicleId;

    @NotNull
    private LocalDate effectiveDate;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private String policyNumber;

    @NotNull
    private InsuranceType insuranceType;

    private String description;

}
