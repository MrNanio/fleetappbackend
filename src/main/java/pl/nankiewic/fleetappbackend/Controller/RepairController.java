package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.RepairService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/repair")
@CrossOrigin(origins = "http://localhost:4200")
public class RepairController {

    private final CheckService checkService;
    private final RepairService repairService;

    @PostMapping
    public void addRepair(Authentication authentication, @RequestBody @Valid RepairDTO repairDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), repairDTO.getVehicleId())) {
            repairService.createVehicleRepair(repairDTO);
        } else throw new PermissionDeniedException();
    }

    @PutMapping
    public void updateRepair(Authentication authentication, @RequestBody @Valid RepairDTO repairDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRepair(userDetails.getUsername(), repairDTO.getId())) {
            repairService.updateVehicleRepair(repairDTO);
        } else throw new PermissionDeniedException();
    }

    @GetMapping
    public Iterable<RepairDTO> getRepairsByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repairService.getRepairsByUser(userDetails.getUsername());
    }

    @GetMapping("/v/{id}")
    public Iterable<RepairDTO> getRepairsByVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return repairService.getRepairsByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/{id}")
    public RepairDTO getRepairById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRepair(userDetails.getUsername(), id)) {
            return repairService.getRepairById(id);
        } else throw new PermissionDeniedException();
    }

    @DeleteMapping("/{id}")
    public void deleteRepair(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRepair(userDetails.getUsername(), id)) {
            repairService.deleteRepairById(id);
        } else throw new PermissionDeniedException();
    }
}
