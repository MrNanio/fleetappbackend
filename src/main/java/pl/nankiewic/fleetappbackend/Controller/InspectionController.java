package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.InspectionService;

@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/inspection")
@CrossOrigin(origins = "http://localhost:4200")
public class InspectionController {

    CheckService checkService;
    InspectionService inspectionService;
    @Autowired
    public InspectionController(CheckService checkService, InspectionService inspectionService) {
        this.checkService = checkService;
        this.inspectionService = inspectionService;
    }
    /*
     add vehicle inspection
     only owner
     */
    @PostMapping
    public void addInspection(@RequestBody InspectionDTO inspectionDTO, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), inspectionDTO.getVehicleId())) {
            inspectionService.saveInspection(inspectionDTO);
        } else throw new PermissionDeniedException();
    }
    /*
    get inspections by vehicle
     */
    @GetMapping("/v/{id}")
    public Iterable<InspectionDTO> getInspectionsByVehicle(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return inspectionService.getInspectionByVehicle(id);
        } else throw new PermissionDeniedException();
    }
    /*
    get inspection by id
     */
    @GetMapping("/{id}")
    public InspectionDTO getInspectionById(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToInspection(userDetails.getUsername(), id)) {
            return inspectionService.getInspectionById(id);
        } else throw new PermissionDeniedException();
    }
    /*
    all inspection by vehicle user
     */
    @GetMapping
    Iterable<InspectionDTO> getInspectionsByUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return inspectionService.getInspectionsByUser(userDetails.getUsername());
    }
    /*
    update inspection
     */
    @PutMapping
    public void updateInspection(@RequestBody InspectionDTO inspectionDTO, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), inspectionDTO.getVehicleId())) {
            inspectionService.saveInspection(inspectionDTO);
        } else throw new PermissionDeniedException();
    }
    @DeleteMapping("/{id}")
    public void deleteInspection(Authentication authentication, @PathVariable Long id){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToInspection(userDetails.getUsername(), id)) {
            inspectionService.deleteInspectionById(id);
        } else throw new PermissionDeniedException();
    }
}
