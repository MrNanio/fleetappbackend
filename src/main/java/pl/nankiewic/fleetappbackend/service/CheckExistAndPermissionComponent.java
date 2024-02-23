package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.entity.*;
import pl.nankiewic.fleetappbackend.repository.*;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CheckExistAndPermissionComponent {

    private final CurrentVehicleUserRepository currentVehicleUserRepository;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;

    public boolean accessToVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .map(v -> isMyVehicleByOwn(v) || isMyVehicleByShare(v))
                .orElse(false);
    }


    public boolean accessToRefueling(Long refuelingId) {
        return vehicleRefuelingRepository.findById(refuelingId)
                .map(VehicleRefueling::getVehicle)
                .map(v -> isMyVehicleByOwn(v) || isMyVehicleByShare(v))
                .orElse(false);
    }

    public boolean accessToUse(Long useId) {
        return vehicleUseRepository.findById(useId)
                .map(VehicleUse::getVehicle)
                .map(v -> isMyVehicleByOwn(v) || isMyVehicleByShare(v))
                .orElse(false);
    }

    private boolean isMyVehicleByOwn(Vehicle vehicle) {
        var userId = JWTokenHelper.getJWTUserId();

        return Optional.of(vehicle)
                .map(Vehicle::getUser)
                .map(User::getId)
                .map(id -> id.equals(userId))
                .orElse(false);
    }

    private boolean isMyVehicleByShare(Vehicle vehicle) {
        var userId = JWTokenHelper.getJWTUserId();

        return currentVehicleUserRepository.findCurrentVehicleUsersByVehicleIs(vehicle).stream()
                .map(CurrentVehicleUser::getUser)
                .map(User::getId)
                .anyMatch(id -> id.equals(userId));
    }

}