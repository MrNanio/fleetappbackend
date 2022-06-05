package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.BlockOrUnblock;
import pl.nankiewic.fleetappbackend.DTO.UserDTO;
import pl.nankiewic.fleetappbackend.service.AccountService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;

    @GetMapping
    public Iterable<UserDTO> getAllUser() {
        return accountService.getAllUser();
    }

    @GetMapping("user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return accountService.getUserById(id);
    }

    @PostMapping("/status")
    public void blockOrUnblockUserById(@RequestBody @Valid BlockOrUnblock blockOrUnblock) {
        accountService.blockOrUnblockUser(blockOrUnblock);
    }
}
