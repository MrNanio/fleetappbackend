package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.FuelType;
import pl.nankiewic.fleetappbackend.entity.VehicleMake;
import pl.nankiewic.fleetappbackend.service.VehicleService;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PreAuthorize("hasRole('SUPERUSER')")
    @PostMapping()
    public void addVehicle(@RequestBody @Valid VehicleRequestResponseDTO vehicleRequestResponseDTO,
                           Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        vehicleService.createVehicle(vehicleRequestResponseDTO, userDetails.getUsername());
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @PutMapping
    public void updateVehicle(@RequestBody @Valid VehicleRequestResponseDTO vehicleRequestResponseDTO,
                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        vehicleService.updateVehicle(vehicleRequestResponseDTO, userDetails.getUsername());
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping()
    public Iterable<VehicleView> getVehiclesByVehicleOwner(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return vehicleService.getVehiclesDataByUser(userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public Optional<VehicleView> getVehicleById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return vehicleService.getVehicleDataById(id, userDetails.getUsername());
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @DeleteMapping("/{id}")
    public void deleteVehicle(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        vehicleService.deleteVehicleById(id, userDetails.getUsername());
    }

    @GetMapping("/make")
    public Iterable<VehicleMake> getMake() {
        return vehicleService.getMake();
    }

    @GetMapping("/fuelType")
    public Iterable<FuelType> getFuelType() {
        return vehicleService.getFuelType();
    }

}