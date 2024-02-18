package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.use.UseDTO;
import pl.nankiewic.fleetappbackend.dto.use.UseView;
import pl.nankiewic.fleetappbackend.service.UseService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/use")
@CrossOrigin(origins = "http://localhost:4200")
public class UseController {

    private final UseService useService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERUSER')")
    public void createVehicleUse(@RequestBody UseDTO useDTO) {
        useService.createVehicleUse(useDTO);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPERUSER')")
    public void updateVehicleUse(@RequestBody @Valid UseDTO useDTO) {
        useService.updateVehicleUse(useDTO);
    }

    @GetMapping("/v/{id}")
    public List<UseView> getUseByVehicleId(@PathVariable Long id) {
        return useService.getUseByVehicle(id);
    }

    @GetMapping("/{id}")
    public UseView getUseByUseId(@PathVariable Long id) {
        return useService.getUseByUseId(id);
    }

    @GetMapping
    public List<UseView> getUseByUser() {
        return useService.getUseByUser();
    }

    @GetMapping("/list")
    public List<UseView> getUseByUserIdAndVehicleId(@RequestParam(name = "u") Long userId,
                                                    @RequestParam(name = "v") Long vehicleId) {
        return useService.getUseByUserAndVehicle(userId, vehicleId);
    }

    @DeleteMapping("/{id}")
    public void deleteUse(@PathVariable Long id) {
        useService.deleteUseById(id);
    }

}