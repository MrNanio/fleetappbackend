package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.Service.InsuranceService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/insurance")
@CrossOrigin(origins = "http://localhost:4200")
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    public void addInsurance(Authentication authentication,
                             @RequestBody @Valid InsuranceRequestDTO insuranceRequestDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        insuranceService.createVehicleInsurance(insuranceRequestDTO, userDetails.getUsername());
    }

    @PutMapping
    public void updateInsurance(Authentication authentication,
                                @RequestBody @Valid InsuranceRequestDTO insuranceRequestDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        insuranceService.updateVehicleInsurance(insuranceRequestDTO, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public InsuranceDTO getInsurancesById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return insuranceService.getInsuranceById(id, userDetails.getUsername());
    }

    @GetMapping("/v/{id}")
    public Iterable<InsuranceDTO> getInsurancesByVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return insuranceService.getInsurancesByVehicle(id, userDetails.getUsername());
    }

    @GetMapping
    public Iterable<InsuranceDTO> getInsurancesByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return insuranceService.getInsurancesByUser(userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteInsurance(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        insuranceService.deleteInsuranceById(id, userDetails.getUsername());
    }

    @GetMapping("/types")
    public Iterable<InsuranceTypeDTO> getInsuranceTypes() {
        return insuranceService.getInsuranceTypes();
    }
}
