package pl.nankiewic.fleetappbackend.DTO;


import lombok.*;
import pl.nankiewic.fleetappbackend.entity.Address;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataDTO {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private String email;

}
