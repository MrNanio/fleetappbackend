package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleView;
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
    public VehicleRequestResponseDTO addVehicle(@RequestBody @Valid VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        return vehicleService.createVehicle(vehicleRequestResponseDTO);
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @PutMapping
    public void updateVehicle(@RequestBody @Valid VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        vehicleService.updateVehicle(vehicleRequestResponseDTO);
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping()
    public Iterable<VehicleView> getVehiclesByVehicleOwner() {
        return vehicleService.getVehiclesDataByUser();
    }

    @GetMapping("/{id}")
    public Optional<VehicleView> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleDataById(id);
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicleById(id);
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
