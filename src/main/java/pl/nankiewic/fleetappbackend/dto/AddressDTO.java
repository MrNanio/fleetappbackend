package pl.nankiewic.fleetappbackend.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    @NotNull
    private Long id;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String buildingNumber;

    private String flatNumber;
    @NotNull
    private String postalCode;

}
