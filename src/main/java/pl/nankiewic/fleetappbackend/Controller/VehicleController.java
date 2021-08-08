package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.VehicleService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/vehicle")
public class VehicleController {

    VehicleService vehicleService;
    CheckService checkService;
    VehicleMakeRepository vehicleMakeRepository;
    FuelTypeRepository fuelTypeRepository;
    @Autowired
    public VehicleController(VehicleService vehicleService, CheckService checkService,
                             VehicleMakeRepository vehicleMakeRepository, FuelTypeRepository fuelTypeRepository) {
        this.vehicleService = vehicleService;
        this.checkService = checkService;
        this.vehicleMakeRepository = vehicleMakeRepository;
        this.fuelTypeRepository = fuelTypeRepository;
    }
    @PreAuthorize("hasRole('SUPERUSER')")
    @PostMapping()
    public Vehicle addVehicle(@RequestBody @Valid VehicleDTO vehicleDTO, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return vehicleService.save(vehicleDTO, userDetails.getUsername());
    }
    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping()
    public Iterable<VehicleDTO> getVehiclesByVehicleOwner(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return vehicleService.getVehiclesDataByUser(userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicleById(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return vehicleService.getVehicleDataById(id);
        } else throw new PermissionDeniedException();
    }
    @PutMapping
    public Vehicle updateVehicle(@RequestBody @Valid VehicleDTO vehicleDTO, Authentication authentication){
       UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       return vehicleService.save(vehicleDTO, userDetails.getUsername());
    }
    @DeleteMapping("/{id}")
    public void deleteVehicle(Authentication authentication, @PathVariable Long id){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            vehicleService.deleteVehicleById(id);
        } else throw new PermissionDeniedException();
    }
    @GetMapping("/make")
    public Iterable<VehicleMake> getMake(){
       return vehicleMakeRepository.findAll();
    }
    @GetMapping("/fuelType")
    public Iterable<FuelType> getFuelType(){
        return fuelTypeRepository.findAll();
    }

}
