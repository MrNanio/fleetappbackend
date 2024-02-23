package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.controller.validator.ObjectModificationValidation;
import pl.nankiewic.fleetappbackend.dto.repair.RepairModifyDTO;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.service.RepairService;

import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/repair")
@CrossOrigin(origins = "http://localhost:4200")
public class RepairController {

    private final RepairService repairService;

    @PostMapping
    public RepairModifyDTO addRepair(@Validated @RequestBody RepairModifyDTO repairModifyDTO) {
        return repairService.createVehicleRepair(repairModifyDTO);
    }

    @PutMapping
    public RepairModifyDTO updateRepair(@Validated(ObjectModificationValidation.class) @RequestBody RepairModifyDTO repairModifyDTO) {
        return repairService.updateVehicleRepair(repairModifyDTO);
    }

    @GetMapping
    public List<RepairView> getRepairsByUser() {
        return repairService.getRepairsByUser();
    }

    @GetMapping("/v/{id}")
    public List<RepairView> getRepairsByVehicle(@PathVariable Long id) {
        return repairService.getRepairViewsByVehicleId(id);
    }

    @GetMapping("/{id}")
    public RepairView getRepairById(@PathVariable Long id) {
        return repairService.getRepairViewById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRepair(@PathVariable Long id) {
        repairService.deleteRepairById(id);
    }

}