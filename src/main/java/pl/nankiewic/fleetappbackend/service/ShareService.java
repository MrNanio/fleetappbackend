package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.ShareVehicleMapper;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShareService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final CurrentVehicleUserRepository currentVehicleUserRepository;
    private final VehicleMapper vehicleMapper;
    private final ShareVehicleMapper shareVehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public void setCurrentVehicleUserToVehicle(ShareDTO shareDTO, String email) {
        if (userRepository.existsByEmail(email) && userRepository.existsById(shareDTO.getUserId())) {
            shareDTO.getVehicleId().forEach(v -> currentVehicleUserRepository.save(
                    shareVehicleMapper.shareDtoToCurrentVehicleUser(v, shareDTO.getUserId())
            ));
        } else throw new EntityNotFoundException("User not found");
    }

    public Iterable<VehicleRequestResponseDTO> getShareVehicleListByUserId(Long id, String username) {
        if (userRepository.existsByEmail(username) && userRepository.existsById(id)) {
            var myByShare = currentVehicleUserRepository.findCurrentVehicleUsersByUserIs(id);
            return myByShare.stream()
                    .map(CurrentVehicleUser::getVehicle)
                    .map(vehicleMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } else throw new EntityNotFoundException("User not found");
    }

    public Iterable<VehicleRequestResponseDTO> getPossibleVehiclesList(String username) {
        if (userRepository.existsByEmail(username)) {
            var myByOwner = vehicleRepository.findVehiclesDataByUserVehicleDto(username);
            var myVehicleNowShare = currentVehicleUserRepository.findCurrentVehicleUsersByVehicleOwner(username);
            return myByOwner.stream()
                    .filter(v -> myVehicleNowShare.stream()
                            .noneMatch(s -> v.getId().equals(s.getVehicle().getId())))
                    .map(vehicleMapper::vehicleDTOtoVehicleResponseDTO)
                    .collect(Collectors.toList());
        } else throw new EntityNotFoundException("User not found");
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