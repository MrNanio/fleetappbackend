package pl.nankiewic.fleetappbackend.dto.vehicle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleDTO extends VehicleBaseDTO {

    @NotNull
    private Long id;

}