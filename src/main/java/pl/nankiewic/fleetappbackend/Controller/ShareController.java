package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;

import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.ShareService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ShareController {

    ShareService shareService;
    CheckService checkService;
    @Autowired
    public ShareController(ShareService shareService, CheckService checkService) {
        this.shareService = shareService;
        this.checkService = checkService;
    }
    @PostMapping("/share")
    public void addShare(Authentication authentication, @RequestBody @Valid ShareDTO shareDTO){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        shareService.setCurrentVehicleUserToVehicle(shareDTO, userDetails.getUsername());
    }
    @GetMapping("/share/user/{id}")
    public Iterable<VehicleDTO> getShareVehicleByIdUser(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shareService.getShareVehicleListByUserId(id, userDetails.getUsername());
    }
    @GetMapping("/share/vehicles")
    public Iterable<VehicleDTO> getPossibleVehicleListToShare(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shareService.getPossibleVehiclesList(userDetails.getUsername());
    }
    @DeleteMapping("/share/vehicle/{id}")
    public void deleteShareVehicleByIdVehicle(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(checkService.accessToVehicle(userDetails.getUsername(), id)){
            shareService.deleteShareVehicleListByVehicleId(id);
        } else throw new PermissionDeniedException();
    }
}
