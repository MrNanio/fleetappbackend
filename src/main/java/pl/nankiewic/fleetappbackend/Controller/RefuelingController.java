package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Service.RefuelingService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/refueling")
public class RefuelingController {

    private final RefuelingService refuelingService;

    @PostMapping
    public void addRefueling(@RequestBody @Valid RefuelingDTO refuelingDTO,
                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        refuelingService.createVehicleRefueling(refuelingDTO, userDetails.getUsername());
    }

    @PutMapping
    public void updateRefueling(@RequestBody @Valid RefuelingDTO refuelingDTO,
                                Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        refuelingService.updateVehicleRefueling(refuelingDTO, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public RefuelingDTO getRefuelingById(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return refuelingService.getRefuelingById(id, userDetails.getUsername());
    }

    @GetMapping("/v/{id}")
    public Iterable<RefuelingDTO> getRefuelingByVehicle(@PathVariable Long id,
                                                        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return refuelingService.getRefuelingByVehicle(id, userDetails.getUsername());
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
        return refuelingService.getRefuelingByUserAndVehicle(userId, vehicleId, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteRefueling(Authentication authentication, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        refuelingService.deleteRefuelingById(id, userDetails.getUsername());
    }

}
