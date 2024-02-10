package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.UseDTO;
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
    public void createVehicleUse(Authentication authentication,
                                 @RequestBody UseDTO useDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        useService.createVehicleUse(useDTO, userDetails.getUsername());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPERUSER')")
    public void updateVehicleUse(Authentication authentication,
                                 @RequestBody @Valid UseDTO useDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        useService.updateVehicleUse(useDTO, userDetails.getUsername());
    }

    @GetMapping("/v/{id}")
    public List<UseDTO> getUseByVehicleId(@PathVariable Long id,
                                          Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return useService.getUseByVehicle(id, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public UseDTO getUseByUseId(@PathVariable Long id,
                                Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return useService.getUseByUseId(id, userDetails.getUsername());
    }

    @GetMapping
    public Iterable<UseDTO> getUseByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return useService.getUseByUser(userDetails.getUsername());
    }

    @GetMapping("/list")
    public List<UseDTO> getUseByUserIdAndVehicleId(@RequestParam(name = "u") Long userId,
                                                       @RequestParam(name = "v") Long vehicleId,
                                                       Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return useService.getUseByUserAndVehicle(userId, vehicleId, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteUse(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        useService.deleteUseById(id, userDetails.getUsername());
    }
}
