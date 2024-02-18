package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionDTO;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.service.InspectionService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/inspection")
@CrossOrigin(origins = "http://localhost:4200")
public class InspectionController {

    private final InspectionService inspectionService;

    @PostMapping
    public void addInspection(@RequestBody @Valid InspectionDTO inspectionDTO) {
        inspectionService.createInspection(inspectionDTO);
    }

    @PutMapping
    public void updateInspection(@RequestBody @Valid InspectionDTO inspectionDTO) {
        inspectionService.updateInspection(inspectionDTO);
    }

    @GetMapping("/v/{id}")
    public List<InspectionView> getInspectionsByVehicle(@PathVariable Long id) {
        return inspectionService.getInspectionByVehicle(id);
    }

    @GetMapping("/{id}")
    public InspectionView getInspectionById(@PathVariable Long id) {
        return inspectionService.getInspectionById(id);
    }

    @GetMapping
    List<InspectionView> getInspectionsByUser() {
        return inspectionService.getInspectionsByUser();
    }

    @DeleteMapping("/{id}")
    public void deleteInspection(@PathVariable Long id) {
        inspectionService.deleteInspectionById(id);
    }

}