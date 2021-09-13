package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.InspectionService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/inspection")
@CrossOrigin(origins = "http://localhost:4200")
public class InspectionController {

    private final CheckService checkService;
    private final InspectionService inspectionService;

    @PostMapping
    public void addInspection(@RequestBody @Valid InspectionDTO inspectionDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), inspectionDTO.getVehicleId())) {
            inspectionService.createInspection(inspectionDTO);
        } else throw new PermissionDeniedException();
    }

    @PutMapping
    public void updateInspection(@RequestBody @Valid InspectionDTO inspectionDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), inspectionDTO.getVehicleId())) {
            inspectionService.updateInspection(inspectionDTO);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/v/{id}")
    public Iterable<InspectionDTO> getInspectionsByVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return inspectionService.getInspectionByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/{id}")
    public InspectionDTO getInspectionById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToInspection(userDetails.getUsername(), id)) {
            return inspectionService.getInspectionById(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping
    Iterable<InspectionDTO> getInspectionsByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return inspectionService.getInspectionsByUser(userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteInspection(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToInspection(userDetails.getUsername(), id)) {
            inspectionService.deleteInspectionById(id);
        } else throw new PermissionDeniedException();
    }
}
