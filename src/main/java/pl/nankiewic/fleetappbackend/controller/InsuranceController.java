package pl.nankiewic.fleetappbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.controller.validator.InsuranceAccessValidator;
import pl.nankiewic.fleetappbackend.controller.validator.ObjectModificationValidation;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceModifyDTO;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;
import pl.nankiewic.fleetappbackend.service.InsuranceService;

import java.util.List;
import java.util.Set;

@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("api/insurance")
@CrossOrigin(origins = "http://localhost:4200")
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final InsuranceAccessValidator validator;

    public InsuranceController(final InsuranceService insuranceService,
                               final InsuranceAccessValidator validator) {
        this.insuranceService = insuranceService;
        this.validator = validator;
    }

    @PostMapping
    public InsuranceModifyDTO addInsurance(@Validated @RequestBody InsuranceModifyDTO insuranceModifyDTO) {
        validator.checkAccessToVehicleByOwner(insuranceModifyDTO.getVehicleId());
        return insuranceService.createVehicleInsurance(insuranceModifyDTO);
    }

    @PutMapping
    public InsuranceModifyDTO updateInsurance(@Validated(ObjectModificationValidation.class) @RequestBody InsuranceModifyDTO insuranceModifyDTO) {
        validator.checkAccessToVehicleByOwner(insuranceModifyDTO.getVehicleId());
        return insuranceService.updateVehicleInsurance(insuranceModifyDTO);
    }

    @GetMapping("/{insuranceId}")
    public InsuranceView getInsurancesById(@PathVariable Long insuranceId) {
        validator.checkAccessToResource(insuranceId);
        return insuranceService.getInsuranceViewByInsuranceId(insuranceId);
    }

    @GetMapping("/v/{vehicleId}")
    public List<InsuranceView> getInsurancesByVehicle(@PathVariable Long vehicleId) {
        validator.checkAccessToVehicleByOwner(vehicleId);
        return insuranceService.getInsurancesByVehicle(vehicleId);
    }

    @GetMapping
    public List<InsuranceView> getInsurancesByUser() { //może wiecej danych w view zwracanych
        return insuranceService.getInsurancesByUser();
    }

    @DeleteMapping("/{insuranceId}")
    public void deleteInsurance(@PathVariable Long insuranceId) {
        validator.checkAccessToResource(insuranceId);
        insuranceService.deleteInsuranceById(insuranceId);
    }

    @GetMapping("/types")
    public Set<InsuranceType> getInsuranceTypes() {
        return insuranceService.getInsuranceTypes();
    }

}