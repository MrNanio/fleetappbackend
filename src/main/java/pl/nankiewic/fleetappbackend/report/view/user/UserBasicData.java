package pl.nankiewic.fleetappbackend.report.view.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicData {

    private String name;

    private String surname;

    private String email;

}
