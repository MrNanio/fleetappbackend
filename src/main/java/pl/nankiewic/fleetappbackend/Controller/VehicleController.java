package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.VehicleService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final CheckService checkService;

    @Autowired
    public VehicleController(VehicleService vehicleService, CheckService checkService) {
        this.vehicleService = vehicleService;
        this.checkService = checkService;
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @PostMapping()
    public void addVehicle(@RequestBody @Valid VehicleRequestResponseDTO vehicleRequestResponseDTO,
                           Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        vehicleService.createVehicle(vehicleRequestResponseDTO, userDetails.getUsername());
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping()
    public Iterable<VehicleDTO> getVehiclesByVehicleOwner(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return vehicleService.getVehiclesDataByUser(userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicleById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return vehicleService.getVehicleDataById(id);
        } else throw new PermissionDeniedException();
    }

//    @PutMapping
//    public Vehicle updateVehicle(@RequestBody @Valid VehicleRequestResponseDTO vehicleRequestResponseDTO,
//                                 Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return vehicleService.save(vehicleRequestResponseDTO, userDetails.getUsername());
//    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            vehicleService.deleteVehicleById(id);
        } else throw new PermissionDeniedException();
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
