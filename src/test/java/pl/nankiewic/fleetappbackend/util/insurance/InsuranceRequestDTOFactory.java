package pl.nankiewic.fleetappbackend.util.insurance;

import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceModifyDTO;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InsuranceRequestDTOFactory {

    public static InsuranceModifyDTO buildInsuranceRequestDTO() {
        return InsuranceModifyDTO.builder()
                .id(1L)
                .insuranceType(InsuranceType.OC)
                .cost(BigDecimal.valueOf(234.4))
                .vehicleId(1L)
                .description("hghghg")
                .expirationDate(LocalDate.now())
                .policyNumber("hghghjgbhjbjh")
                .build();
    }
}
