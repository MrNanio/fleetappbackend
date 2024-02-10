package pl.nankiewic.fleetappbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockOrUnblock {

    private Long id;
    private String userStatus;
}
