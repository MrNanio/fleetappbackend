package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.BlockOrUnblock;
import pl.nankiewic.fleetappbackend.DTO.UserDTO;
import pl.nankiewic.fleetappbackend.Service.AccountService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AccountService accountService;
    @GetMapping()
    public Iterable<UserDTO> getAllUser() {
        return accountService.getAllUser();
    }
    @GetMapping("user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return accountService.getUserById(id);
    }
    @PostMapping("/status")
    public void blockOrUnblockUserById(@RequestBody BlockOrUnblock blockOrUnblock) {
       accountService.blockOrUnblockUser(blockOrUnblock);
    }
}
