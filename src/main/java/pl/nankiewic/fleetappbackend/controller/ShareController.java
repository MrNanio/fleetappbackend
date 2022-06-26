package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.service.ShareService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/share")
    public Iterable<CurrentVehicleUser> addShare(@RequestBody @Valid ShareDTO shareDTO) {
        return shareService.saveCurrentVehicleUserToVehicle(shareDTO);
    }

    @GetMapping("/share/user/{id}")
    public Iterable<VehicleView> getSharedVehicleByUserId(@PathVariable Long id) {
        return shareService.getSharedVehicleByUserId(id);
    }

    @GetMapping("/share/vehicles")
    public Iterable<VehicleView> getOwnedAndSharedVehiclesViewList() {
        return shareService.getOwnedAndSharedVehiclesViewList();
    }

    @DeleteMapping("/share/vehicle/{id}")
    public void deleteShareVehicleByIdVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        shareService.deleteShareVehicleListByVehicleId(id, userDetails.getUsername());
    }
}
