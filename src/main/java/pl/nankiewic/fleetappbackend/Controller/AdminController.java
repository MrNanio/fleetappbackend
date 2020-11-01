package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    public void getAllSuperUser(Authentication authentication) {

    }

    public void getAllUser(Authentication authentication) {

    }

    public void blockUserById(RequestParam id, Authentication authentication) {

    }

    public void unblockUserById(RequestParam id, Authentication authentication) {

    }
    public void deleteSuperUserById(RequestParam id, Authentication authentication){

    }
    public void deleteUserById(RequestParam id, Authentication authentication){

    }
}
