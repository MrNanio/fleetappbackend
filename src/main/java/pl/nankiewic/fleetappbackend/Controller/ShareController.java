package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
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
/*
    @GetMapping("/share/{id}")
    public ShareVehicleRespond shareMyVehicle(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shareService.shareOption(id, userDetails.getUsername());
    }

    @PostMapping("/share")
    public ResponseEntity<MessageResponse> userSet(@RequestBody ShareDTO shareDTO, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (checkService.accessToVehicle(userDetails.getUsername(), shareDTO.getVehicleId())){
            shareService.setCurrentVehicleUserToVehicle(shareDTO);
        }//exception permission denied
        return ResponseEntity.ok(new MessageResponse("OK", LocalDateTime.now()));
    }*/

}
