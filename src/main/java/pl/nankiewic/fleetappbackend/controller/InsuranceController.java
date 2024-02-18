package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;
import pl.nankiewic.fleetappbackend.service.InsuranceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/insurance")
@CrossOrigin(origins = "http://localhost:4200")
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    public void addInsurance(@RequestBody @Valid InsuranceRequestDTO insuranceRequestDTO) {
        insuranceService.createVehicleInsurance(insuranceRequestDTO);
    }

    @PutMapping
    public void updateInsurance(@RequestBody @Valid InsuranceRequestDTO insuranceRequestDTO) {
        insuranceService.updateVehicleInsurance(insuranceRequestDTO);
    }

    @GetMapping("/{id}")
    public InsuranceView getInsurancesById(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id);
    }

    @GetMapping("/v/{id}")
    public List<InsuranceView> getInsurancesByVehicle(@PathVariable Long id) {
        return insuranceService.getInsurancesByVehicle(id);
    }

    @GetMapping
    public List<InsuranceView> getInsurancesByUser() {
        return insuranceService.getInsurancesByUser();
    }

    @DeleteMapping("/{id}")
    public void deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsuranceById(id);
    }

    @GetMapping("/types")
    public Set<InsuranceType> getInsuranceTypes() {
        return insuranceService.getInsuranceTypes();
    }

}