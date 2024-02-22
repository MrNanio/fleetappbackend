package pl.nankiewic.fleetappbackend.controller.validator;

import org.springframework.stereotype.Component;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.PERMISSION_DENIED_ERROR;

@Component
public class InsuranceAccessValidator extends BasicAccessValidator {

    private final VehicleInsuranceRepository vehicleInsuranceRepository;

    public InsuranceAccessValidator(final VehicleRepository vehicleRepository,
                                    final CurrentVehicleUserRepository currentVehicleUserRepository,
                                    final VehicleInsuranceRepository vehicleInsuranceRepository) {
        super(vehicleRepository, currentVehicleUserRepository);
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
    }

    public void checkAccessToResource(Long insuranceId) {
        var userId = JWTokenHelper.getJWTUserId();

        if (!vehicleInsuranceRepository.existsByVehicleOwnerIdAndInsuranceId(userId, insuranceId)) {
            throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
        }
    }

}
