package pl.nankiewic.fleetappbackend.util.insurance;

import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InsuranceRequestDTOFactory {

    public static InsuranceRequestDTO buildInsuranceRequestDTO() {
        return InsuranceRequestDTO.builder()
                .id(1L)
                .insuranceType("NNW")
                .cost(BigDecimal.valueOf(234.4))
                .vehicleId(1L)
                .description("hghghg")
                .expirationDate(LocalDate.now())
                .policyNumber("hghghjgbhjbjh")
                .build();
    }
}
