package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.RepairDTO;
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
    public void addRepair(Authentication authentication, @RequestBody @Valid RepairDTO repairDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        repairService.createVehicleRepair(repairDTO, userDetails.getUsername());
    }

    @PutMapping
    public void updateRepair(Authentication authentication, @RequestBody @Valid RepairDTO repairDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        repairService.updateVehicleRepair(repairDTO, userDetails.getUsername());
    }

    @GetMapping
    public List<RepairDTO> getRepairsByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repairService.getRepairsByUser(userDetails.getUsername());
    }

    @GetMapping("/v/{id}")
    public List<RepairDTO> getRepairsByVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repairService.getRepairsByVehicle(id, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public RepairDTO getRepairById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repairService.getRepairById(id, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteRepair(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        repairService.deleteRepairById(id, userDetails.getUsername());
    }
}
