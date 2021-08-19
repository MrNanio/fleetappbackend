package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.InsuranceService;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/insurance")
@CrossOrigin(origins = "http://localhost:4200")
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final CheckService checkService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService, CheckService checkService) {
        this.insuranceService = insuranceService;
        this.checkService = checkService;
    }

    @PostMapping
    VehicleInsurance addInsurance(Authentication authentication, @RequestBody @Valid InsuranceDTO insuranceDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), insuranceDTO.getVehicleId())) {
            return insuranceService.save(insuranceDTO);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/v/{id}")
    Iterable<InsuranceDTO> getInsurancesByVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return insuranceService.getInsurancesByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/{id}")
    InsuranceDTO getInsurancesById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToInsurance(userDetails.getUsername(), id)) {
            return insuranceService.getInsuranceById(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping
    Iterable<InsuranceDTO> getInsurancesByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return insuranceService.getInsurancesByUser(userDetails.getUsername());
    }

    @PutMapping
    VehicleInsurance updateInsurance(Authentication authentication, @RequestBody @Valid InsuranceDTO insuranceDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), insuranceDTO.getVehicleId())) {
            return insuranceService.save(insuranceDTO);
        } else throw new PermissionDeniedException();
    }

    @DeleteMapping("/{id}")
    public void deleteInsurance(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToInsurance(userDetails.getUsername(), id)) {
            insuranceService.deleteInsuranceById(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/types")
    Iterable<InsuranceTypeDTO> getInsuranceTypes() {
        return insuranceService.getInsuranceTypes();
    }
}
