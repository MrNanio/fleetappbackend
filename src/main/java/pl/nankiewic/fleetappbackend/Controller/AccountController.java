package pl.nankiewic.fleetappbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.*;
import pl.nankiewic.fleetappbackend.Service.AccountService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/activation-account")
    public @ResponseBody
    void activationAccount(@RequestParam String activation_token) {
        accountService.getAccountActivation(activation_token);
    }

    @PostMapping("/reset-password")
    public void resetPasswordEmailRequest(@RequestBody @Valid EmailDTO emailDTO) {
        accountService.postResetPassword(emailDTO);
    }

    @GetMapping("/reset-password")
    public ResetChangePasswordDTO getResetPasswordAuth(@RequestParam(name = "u") String userToken,
                                                       @RequestParam(name = "c") String userCode) {
        return accountService.getResetPassword(userToken, userCode);
    }

    @PostMapping("/reset-password/new")
    public void createNewPassword(@RequestBody @Valid ResetChangePasswordDTO resetChangePasswordDTO) {
        accountService.postNewPassword(resetChangePasswordDTO);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid PasswordDTO passwordDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.changePassword(passwordDTO, userDetails.getUsername());
    }

    @GetMapping("/userdata/{id}")
    public ResponseEntity<UserDataDTO> getUserData(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(accountService.getUserData(userDetails.getUsername(), id));
    }

    @PostMapping("/userdata")
    public ResponseEntity<MessageResponse> addUserData(@RequestBody @Valid UserDataDTO userDataDTO,
                                                       Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.createUserData(userDataDTO, userDetails.getUsername());
        return ResponseEntity.ok().body(new MessageResponse("ok", LocalDateTime.now()));
    }

    @PutMapping("/userdata")
    public ResponseEntity<MessageResponse> updateUserData(@RequestBody @Valid UserDataDTO userDataDTO,
                                                          Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.updateUserData(userDataDTO, userDetails.getUsername());
        return ResponseEntity.ok().body(new MessageResponse("ok", LocalDateTime.now()));
    }

    @DeleteMapping("/userdata")
    public ResponseEntity<MessageResponse> deleteUserData(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.deleteUserData(userDetails.getUsername());
        return ResponseEntity.ok().body(new MessageResponse("ok", LocalDateTime.now()));
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @PostMapping("/new-account")
    public void postInviteToNewUser(@RequestBody @Valid EmailDTO emailDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.addNewUser(emailDTO, userDetails.getUsername());
    }

    @GetMapping("/new-account")
    public IdDTO getUserInvite(@RequestParam(name = "u") String token) {
        return accountService.getUserInvite(token);
    }

    @PostMapping("/new-account/password")
    public void setNewPasswordForUser(@RequestBody @Valid IdDTO idDTO) {
        accountService.newPasswordForNewUser(idDTO);
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping("/show-all-account")
    Iterable<UserDTO> getAllUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return accountService.getUserByManager(userDetails.getUsername());
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping("/get-user-by-id/{id}")
    EmailDTO getUserEmail(@PathVariable Long id) {
        return accountService.getUserEmail(id);
    }
}

