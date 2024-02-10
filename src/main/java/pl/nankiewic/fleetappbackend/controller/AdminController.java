package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.user.UserView;
import pl.nankiewic.fleetappbackend.service.AccountService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;

    @GetMapping
    public List<UserView> getAllUser() {
        return accountService.getAllUser();
    }

    @GetMapping("user/{id}")
    public UserView getUserById(@PathVariable Long id) {
        return accountService.getUserById(id);
    }


    //FIXME
    /*
    @PostMapping("/status")
    public void blockOrUnblockUserById(@RequestBody @Valid BlockOrUnblock blockOrUnblock) {
        accountService.blockOrUnblockUser(blockOrUnblock);
    }
    */
}
