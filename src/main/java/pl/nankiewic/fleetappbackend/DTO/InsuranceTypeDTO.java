package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceTypeDTO {
    private Long id;
    private String name;

}
