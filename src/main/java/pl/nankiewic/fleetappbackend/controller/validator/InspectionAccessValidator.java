package pl.nankiewic.fleetappbackend.controller.validator;

import org.springframework.stereotype.Component;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.PERMISSION_DENIED_ERROR;

@Component
public class InspectionAccessValidator extends BasicAccessValidator {

    private final VehicleInspectionRepository vehicleInspectionRepository;

    public InspectionAccessValidator(final VehicleRepository vehicleRepository,
                                     final CurrentVehicleUserRepository currentVehicleUserRepository,
                                     final VehicleInspectionRepository vehicleInspectionRepository) {
        super(vehicleRepository, currentVehicleUserRepository);
        this.vehicleInspectionRepository = vehicleInspectionRepository;
    }

    public void checkAccessToResource(Long insuranceId) {
        var userId = JWTokenHelper.getJWTUserId();

        if (!vehicleInspectionRepository.existsByVehicleOwnerIdAndInsuranceId(userId, insuranceId)) {
            throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
        }
    }

}
