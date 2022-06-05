package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;

import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.service.ShareService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/share")
    public void addShare(Authentication authentication, @RequestBody @Valid ShareDTO shareDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        shareService.setCurrentVehicleUserToVehicle(shareDTO, userDetails.getUsername());
    }

    @GetMapping("/share/user/{id}")
    public Iterable<VehicleRequestResponseDTO> getShareVehicleByIdUser(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shareService.getShareVehicleListByUserId(id, userDetails.getUsername());
    }

    @GetMapping("/share/vehicles")
    public Iterable<VehicleRequestResponseDTO> getPossibleVehicleListToShare(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shareService.getPossibleVehiclesList(userDetails.getUsername());
    }

    @DeleteMapping("/share/vehicle/{id}")
    public void deleteShareVehicleByIdVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        shareService.deleteShareVehicleListByVehicleId(id, userDetails.getUsername());
    }
}
