package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.repair.RepairDTO;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.service.RepairService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/repair")
@CrossOrigin(origins = "http://localhost:4200")
public class RepairController {

    private final RepairService repairService;

    @PostMapping
    public void addRepair(@RequestBody @Valid RepairDTO repairDTO) {
        repairService.createVehicleRepair(repairDTO);
    }

    @PutMapping
    public void updateRepair(@RequestBody @Valid RepairDTO repairDTO) {
        repairService.updateVehicleRepair(repairDTO);
    }

    @GetMapping
    public List<RepairView> getRepairsByUser() {
        return repairService.getRepairsByUser();
    }

    @GetMapping("/v/{id}")
    public List<RepairView> getRepairsByVehicle(@PathVariable Long id) {
        return repairService.getRepairsByVehicle(id);
    }

    @GetMapping("/{id}")
    public RepairView getRepairById(@PathVariable Long id) {
        return repairService.getRepairById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRepair(@PathVariable Long id) {
        repairService.deleteRepairById(id);
    }

}