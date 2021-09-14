package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumInsuranceType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceTypeDTO {
    private Long id;
    private EnumInsuranceType name;

}
