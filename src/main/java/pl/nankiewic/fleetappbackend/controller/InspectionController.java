package pl.nankiewic.fleetappbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.controller.validator.InspectionAccessValidator;
import pl.nankiewic.fleetappbackend.controller.validator.ObjectModificationValidation;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionModifyDTO;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.service.InspectionService;

import java.util.List;

@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/inspection")
@CrossOrigin(origins = "http://localhost:4200")
public class InspectionController {

    private final InspectionService inspectionService;
    private final InspectionAccessValidator validator;

    public InspectionController(final InspectionService inspectionService,
                                final InspectionAccessValidator validator) {
        this.inspectionService = inspectionService;
        this.validator = validator;
    }

    @PostMapping
    public InspectionModifyDTO addInspection(@Validated @RequestBody InspectionModifyDTO inspectionModifyDTO) {
        validator.checkAccessToVehicleByOwner(inspectionModifyDTO.getVehicleId());
        return inspectionService.createInspection(inspectionModifyDTO);
    }

    @PutMapping
    public InspectionModifyDTO updateInspection(@Validated(ObjectModificationValidation.class) @RequestBody InspectionModifyDTO inspectionModifyDTO) {
        validator.checkAccessToVehicleByOwner(inspectionModifyDTO.getVehicleId());
        return inspectionService.updateInspection(inspectionModifyDTO);
    }

    @GetMapping("/v/{vehicleId}")
    public List<InspectionView> getInspectionsByVehicle(@PathVariable Long vehicleId) {
        validator.checkAccessToVehicleByOwner(vehicleId);
        return inspectionService.getInspectionByVehicle(vehicleId);
    }

    @GetMapping("/{inspectionId}")
    public InspectionView getInspectionById(@PathVariable Long inspectionId) {
        validator.checkAccessToResource(inspectionId);
        return inspectionService.getInspectionById(inspectionId);
    }

    @GetMapping
    List<InspectionView> getInspectionsByUser() {
        return inspectionService.getInspectionsByUser();
    }

    @DeleteMapping("/{inspectionId}")
    public void deleteInspection(@PathVariable Long inspectionId) {
        validator.checkAccessToResource(inspectionId);
        inspectionService.deleteInspectionById(inspectionId);
    }

}