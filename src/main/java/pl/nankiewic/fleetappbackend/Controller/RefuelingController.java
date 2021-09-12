package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.RefuelingService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/refueling")
public class RefuelingController {

    private final RefuelingService refuelingService;
    private final CheckService checkService;

    @PostMapping
    public void addRefueling(@RequestBody @Valid RefuelingDTO refuelingDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), refuelingDTO.getVehicleId())) {
            refuelingService.createVehicleRefueling(refuelingDTO, userDetails.getUsername());
        } else throw new PermissionDeniedException();
    }

    @PutMapping
    public void updateRefueling(@RequestBody @Valid RefuelingDTO refuelingDTO,
                                Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRefueling(userDetails.getUsername(), refuelingDTO.getId())) {
            refuelingService.updateVehicleRefueling(refuelingDTO);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/{id}")
    public RefuelingDTO getRefuelingById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRefueling(userDetails.getUsername(), id)) {
            return refuelingService.getRefuelingById(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping("/v/{id}")
    public Iterable<RefuelingDTO> getRefuelingByVehicle(@PathVariable Long id,
                                                        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), id)) {
            return refuelingService.getRefuelingByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    @GetMapping
    public Iterable<RefuelingDTO> getRefuelingByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return refuelingService.getRefuelingByUser(userDetails.getUsername());
    }

    @GetMapping("/my")
    public Iterable<RefuelingDTO> getRefuelingByAuthor(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return refuelingService.getRefuelingByAuthor(userDetails.getUsername());
    }

    @GetMapping("/list")
    public Iterable<RefuelingDTO> getRefuelingByUserIdAndVehicleId(@RequestParam(name = "u") Long userId,
                                                                   @RequestParam(name = "v") Long vehicleId,
                                                                   Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), vehicleId)) {
            return refuelingService.getRefuelingByUserAndVehicle(userId, vehicleId);
        } else throw new PermissionDeniedException();
    }

    @DeleteMapping("/{id}")
    public void deleteRefueling(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToRefueling(userDetails.getUsername(), id)) {
            refuelingService.deleteRefuelingById(id);
        } else throw new PermissionDeniedException();
    }

}
