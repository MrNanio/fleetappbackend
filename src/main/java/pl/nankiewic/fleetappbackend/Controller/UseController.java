package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.UseService;

import javax.validation.Valid;

@RestController
@RequestMapping("use")
@CrossOrigin(origins = "http://localhost:4200")
public class UseController {

    private final CheckService checkService;
    private final UseService useService;
    @Autowired
    public UseController(CheckService checkService, UseService useService) {
        this.checkService = checkService;
        this.useService = useService;
    }
    /*
    add use
     */
    @PostMapping
    public void addUse(Authentication authentication, @RequestBody @Valid UseDTO useDTO) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (useDTO.getVehicleId()!=null && checkService.accessToVehicle(userDetails.getUsername(), useDTO.getVehicleId())) {
            useService.save(useDTO, userDetails.getUsername());
        } else throw new PermissionDeniedException();
    }
    /*
    get by vehicle
    for vehicle owner
     */
    @GetMapping("/v/{id}")
    public Iterable<UseDTO> getUseByVehicle(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return useService.getUseByVehicle(id);
        } else throw new PermissionDeniedException();
    }
    /*
    get by user
    for all
     */
    @GetMapping
    public Iterable<UseDTO> getUseByUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return useService.getUseByUser(userDetails.getUsername());
    }
    /*
    get by id
    for all
     */
    @GetMapping("/{id}")
    public UseDTO getUseById(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToUse(userDetails.getUsername(), id)) {
            return useService.getUseById(id);
        } else throw new PermissionDeniedException();
    }
    /*
    use update
    */
    @PutMapping
    public void updateUse(Authentication authentication, @RequestBody @Valid UseDTO useDTO){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToUse(userDetails.getUsername(), useDTO.getId())) {
            useService.save(useDTO, userDetails.getUsername());
        } else throw new PermissionDeniedException();
    }
    @DeleteMapping("/{id}")
    public void deleteUse(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToUse(userDetails.getUsername(), id)) {
            useService.deleteUseById(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/list")
    public Iterable<UseDTO> getUseByUserIdAndVehicleId(@RequestParam(name = "u") Long userId,
                                                       @RequestParam(name = "v") Long vehicleId,
                                                       Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(),vehicleId)) {
            return useService.getUseByUserAndVehicle(userId, vehicleId);
        } else throw new PermissionDeniedException();
    }
}
