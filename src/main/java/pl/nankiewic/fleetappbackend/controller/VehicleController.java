package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleBaseDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleMake;
import pl.nankiewic.fleetappbackend.service.VehicleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PreAuthorize("hasRole('SUPERUSER')")
    @PostMapping
    public VehicleDTO createVehicle(@RequestBody @Valid VehicleBaseDTO vehicleBaseDTO) {
        return vehicleService.createVehicle(vehicleBaseDTO);
    }

   @PreAuthorize("hasRole('SUPERUSER')")
    @PutMapping
    public VehicleDTO updateVehicle(@RequestBody @Valid VehicleDTO vehicleRequestResponseDTO) {
        return vehicleService.updateVehicle(vehicleRequestResponseDTO);
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicleById(id);
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping()
    public List<VehicleView> getVehiclesByVehicleOwner() {
        return vehicleService.findVehicleViewsByUser();
    }

    @GetMapping("/{id}")
    public VehicleView getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleDataById(id);
    }

    @GetMapping("/make")
    public List<VehicleMake> getMake() {
        return vehicleService.getMake();
    }

    @GetMapping("/fuelType")
    public Set<FuelType> getFuelType() {
        return vehicleService.getFuelType();
    }

}