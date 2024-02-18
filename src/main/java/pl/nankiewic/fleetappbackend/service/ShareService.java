package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.ShareDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.ShareVehicleMapper;
import pl.nankiewic.fleetappbackend.repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShareService {

    private final VehicleRepository vehicleRepository;
    private final CurrentVehicleUserRepository currentVehicleUserRepository;
    private final ShareVehicleMapper shareVehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public List<CurrentVehicleUser> saveCurrentVehicleUserToVehicle(ShareDTO shareDTO) {
        return vehicleRepository.findAllById(shareDTO.getVehicleId()).stream()
                .map(v -> shareVehicleMapper.shareDtoToCurrentVehicleUser(v.getId(), shareDTO.getUserId()))
                .map(currentVehicleUserRepository::save)
                .collect(Collectors.toList());
    }

    public List<VehicleView> getSharedVehicleByUserId(Long userId) {
        return vehicleRepository.findVehicleViewsByCurrentUserId(userId);
    }

    public List<VehicleView> getOwnedAndSharedVehiclesViewList() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleRepository.findVehicleViewsByCurrentUserIdOrOwnerId(userId);
    }

    public void deleteShareVehicleListByVehicleId(Long id) {
        if (checkExistAndPermissionComponent.accessToVehicle(id)) {
            if (currentVehicleUserRepository.existsByVehicle_Id(id)) {
                CurrentVehicleUser currentVehicleUser = currentVehicleUserRepository.findByVehicle(id);
                currentVehicleUserRepository.deleteById(currentVehicleUser.getId());
            } else throw new EntityNotFoundException("VehicleShare not found");
        } else throw new PermissionDeniedException();
    }

}