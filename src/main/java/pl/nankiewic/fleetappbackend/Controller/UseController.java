package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.UseService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/use")
@CrossOrigin(origins = "http://localhost:4200")
public class UseController {

    private final CheckService checkService;
    private final UseService useService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERUSER')")
    public void createVehicleUse(Authentication authentication,
                                 @RequestBody UseDTO useDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), useDTO.getVehicleId())) {
            useService.createVehicleUse(useDTO, userDetails.getUsername());
        } else throw new PermissionDeniedException();
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPERUSER')")
    public void updateVehicleUse(Authentication authentication,
                                 @RequestBody @Valid UseDTO useDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToUse(userDetails.getUsername(), useDTO.getId())) {
            useService.updateVehicleUse(useDTO);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/v/{id}")
    public Iterable<UseDTO> getUseByVehicleId(@PathVariable Long id,
                                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return useService.getUseByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/{id}")
    public UseDTO getUseByUseId(@PathVariable Long id,
                                Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToUse(userDetails.getUsername(), id)) {
            return useService.getUseByUseId(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping
    public Iterable<UseDTO> getUseByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return useService.getUseByUser(userDetails.getUsername());
    }

    @GetMapping("/list")
    public Iterable<UseDTO> getUseByUserIdAndVehicleId(@RequestParam(name = "u") Long userId,
                                                       @RequestParam(name = "v") Long vehicleId,
                                                       Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), vehicleId)) {
            return useService.getUseByUserAndVehicle(userId, vehicleId);
        } else throw new PermissionDeniedException();
    }

    @DeleteMapping("/{id}")
    public void deleteUse(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToUse(userDetails.getUsername(), id)) {
            useService.deleteUseById(id);
        } else throw new PermissionDeniedException();
    }
}
