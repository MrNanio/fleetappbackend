package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;

import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.ShareService;


@RestController
@PreAuthorize("hasRole('SUPERUSER')")
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
    public void addShare(Authentication authentication, @RequestBody ShareDTO shareDTO){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        shareService.setCurrentVehicleUserToVehicle(shareDTO, userDetails.getUsername());
    }
}
