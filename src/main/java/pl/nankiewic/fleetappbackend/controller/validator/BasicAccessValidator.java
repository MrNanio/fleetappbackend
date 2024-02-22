package pl.nankiewic.fleetappbackend.controller.validator;

import org.springframework.stereotype.Component;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.PERMISSION_DENIED_ERROR;

@Component
abstract class BasicAccessValidator {

    private final VehicleRepository vehicleRepository;
    private final CurrentVehicleUserRepository currentVehicleUserRepository;

    protected BasicAccessValidator(final VehicleRepository vehicleRepository,
                                   final CurrentVehicleUserRepository currentVehicleUserRepository) {
        this.vehicleRepository = vehicleRepository;
        this.currentVehicleUserRepository = currentVehicleUserRepository;
    }

    public void checkAccessToVehicleByOwnerOrByShare(Long vehicleId) {
        var userId = JWTokenHelper.getJWTUserId();

        if (isNotVehicleOwner(vehicleId, userId) && isNotVehicleUserByShare(vehicleId, userId)) {
            throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
        }
    }

    public void checkAccessToVehicleByOwner(Long vehicleId) {
        var userId = JWTokenHelper.getJWTUserId();

        if (isNotVehicleOwner(vehicleId, userId)) {
            throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
        }
    }

    private boolean isNotVehicleUserByShare(Long vehicleId, Long userId) {
        return !currentVehicleUserRepository.existsByUserIdAndVehicleId(userId, vehicleId);
    }

    private boolean isNotVehicleOwner(Long vehicleId, Long userId) {
       return !vehicleRepository.existsByUserIdAndVehicleId(userId, vehicleId);
    }

}
