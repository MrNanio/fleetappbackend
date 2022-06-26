package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.ShareVehicleMapper;
import pl.nankiewic.fleetappbackend.repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShareService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final CurrentVehicleUserRepository currentVehicleUserRepository;
    private final ShareVehicleMapper shareVehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public List<CurrentVehicleUser> saveCurrentVehicleUserToVehicle(ShareDTO shareDTO) {
        return vehicleRepository.findAllById(shareDTO.getVehicleId()).stream()
                .map(v -> shareVehicleMapper.shareDtoToCurrentVehicleUser(v.getId(), shareDTO.getUserId()))
                .map(currentVehicleUserRepository::save)
                .collect(Collectors.toList());
    }

    public Iterable<VehicleView> getSharedVehicleByUserId(Long userId) {
        return vehicleRepository.findVehicleByShared(userId);
    }

    public Iterable<VehicleView> getOwnedAndSharedVehiclesViewList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .map(User::getId)
                .map(vehicleRepository::findVehicleByShareOrOwn)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteShareVehicleListByVehicleId(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            if (currentVehicleUserRepository.existsByVehicle_Id(id)) {
                CurrentVehicleUser currentVehicleUser = currentVehicleUserRepository.findByVehicle(id);
                currentVehicleUserRepository.deleteById(currentVehicleUser.getId());
            } else throw new EntityNotFoundException("VehicleShare not found");
        } else throw new PermissionDeniedException();
    }
}