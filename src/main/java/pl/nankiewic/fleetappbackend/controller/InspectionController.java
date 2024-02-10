package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.InspectionDTO;
import pl.nankiewic.fleetappbackend.service.InspectionService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/inspection")
@CrossOrigin(origins = "http://localhost:4200")
public class InspectionController {

    private final InspectionService inspectionService;

    @PostMapping
    public void addInspection(@RequestBody @Valid InspectionDTO inspectionDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        inspectionService.createInspection(inspectionDTO, userDetails.getUsername());
    }

    @PutMapping
    public void updateInspection(@RequestBody @Valid InspectionDTO inspectionDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        inspectionService.updateInspection(inspectionDTO, userDetails.getUsername());
    }

    @GetMapping("/v/{id}")
    public Iterable<InspectionDTO> getInspectionsByVehicle(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return inspectionService.getInspectionByVehicle(id, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public InspectionDTO getInspectionById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return inspectionService.getInspectionById(id, userDetails.getUsername());
    }

    @GetMapping
    Iterable<InspectionDTO> getInspectionsByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return inspectionService.getInspectionsByUser(userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteInspection(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        inspectionService.deleteInspectionById(id, userDetails.getUsername());
    }
}
