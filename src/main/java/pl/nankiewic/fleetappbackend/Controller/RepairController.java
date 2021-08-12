package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.RepairService;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('SUPERUSER')")
@RequestMapping("/repair")
@CrossOrigin(origins = "http://localhost:4200")
public class RepairController {

    private final CheckService checkService;
    private final RepairService repairService;
    @Autowired
    public RepairController(CheckService checkService, RepairService repairService) {
        this.checkService = checkService;
        this.repairService = repairService;
    }
    /*
     add repair
    */
   @PostMapping
    public void addRepair(Authentication authentication, @RequestBody @Valid RepairDTO repairDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), repairDTO.getVehicleId())) {
            repairService.save(repairDTO);
        } else throw new PermissionDeniedException();
    }
    /*
    get by user
    */
    @GetMapping
    public Iterable<RepairDTO> getRepairsByUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repairService.getRepairsByUser(userDetails.getUsername());
    }
    /*
    get by vehicle id
     */
    @GetMapping("/v/{id}")
    public Iterable<RepairDTO> getRepairsByVehicle(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return repairService.getRepairsByVehicle(id);
        } else throw new PermissionDeniedException();
    }
    /*
    get by repair id
    */
    @GetMapping("/{id}")
    public RepairDTO getRepairById(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRepair(userDetails.getUsername(), id)) {
            return repairService.getRepairById(id);
        } else throw new PermissionDeniedException();
    }
    /*
    repair update
     */
    @PutMapping
    public void updateRepair(Authentication authentication, @RequestBody @Valid RepairDTO repairDTO){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRepair(userDetails.getUsername(), repairDTO.getId())) {
            repairService.save(repairDTO);
        } else throw new PermissionDeniedException();
    }
    @DeleteMapping("/{id}")
    public void deleteRepair(Authentication authentication, @PathVariable Long id){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRepair(userDetails.getUsername(), id)) {
            repairService.deleteRepairById(id);
        } else throw new PermissionDeniedException();
    }
}
