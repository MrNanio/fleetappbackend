package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.*;
import pl.nankiewic.fleetappbackend.dto.user.UserDataDTO;
import pl.nankiewic.fleetappbackend.dto.user.UserView;
import pl.nankiewic.fleetappbackend.service.AccountService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/account")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final AccountService accountService;

    /**
     * manage account status and availability - admin/superuser
     */
    @PostMapping("/block/{id}")
    public void blockUserAccount(@PathVariable Long id) {
        accountService.blockUserAccountByUserId(id);
    }

    @PostMapping("/activate/{id}")
    public void activateUserAccount(@PathVariable Long id) {
        accountService.activateUserAccountByUserId(id);
    }

    @PostMapping("/deactivate/{id}")
    public void deactivateUserAccount(@PathVariable Long id) {
        accountService.deactivateUserAccountByUserId(id);
    }

    /**
     * first account activation, password recovery, password change,
     */

    @GetMapping("/activation")
    public void activationAccount(@RequestParam String token) {
        accountService.accountActivationByToken(token);
    }

    @PostMapping("/recovery-password")
    public void recoveryPasswordByEmailAddress(@RequestBody @Valid PasswordRecoveryDTO passwordRecoveryDTO) {
        accountService.recoveryPasswordByEmailAddress(passwordRecoveryDTO);
    }

    @PostMapping("/change-forgotten-password")
    public void changeForgottenPassword(@RequestBody @Valid PasswordRecoveryDTO passwordRecoveryDTO) {
        accountService.changeForgottenPassword(passwordRecoveryDTO);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid PasswordDTO passwordDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.changePassword(passwordDTO, userDetails.getUsername());
    }

    /**
     *  create, update, get, delete user data
     */

    @PostMapping("/userdata")
    public ResponseEntity<MessageResponse> createUserData(@RequestBody @Valid UserDataDTO userDataDTO,
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

    @GetMapping("/userdata/{id}")
    public ResponseEntity<UserDataDTO> getUserData(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(accountService.getUserData(userDetails.getUsername(), id));
    }

    @DeleteMapping("/userdata")
    public ResponseEntity<MessageResponse> deleteUserData(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.deleteUserData(userDetails.getUsername());
        return ResponseEntity.ok().body(new MessageResponse("ok", LocalDateTime.now()));
    }

    /**
     * invite user - USER, activate, only for account with role USER
     */

    @PreAuthorize("hasRole('SUPERUSER')")
    @PostMapping("/user")
    public void inviteUser(@RequestBody @Valid PasswordRecoveryDTO passwordRecoveryDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.addNewUser(passwordRecoveryDTO, userDetails.getUsername());
    }

    @GetMapping("/user")
    public IdDTO inviteUser(@RequestParam(name = "u") String token) {
        return accountService.getUserInvite(token);
    }

    @PostMapping("/user/password")
    public void setNewPasswordForUser(@RequestBody @Valid IdDTO idDTO) {
        accountService.newPasswordForNewUser(idDTO);
    }

    /**
     * SUPERUSER (manger) - features
     */

    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping("/all")
    List<UserView> getAllUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return accountService.getUserByManager(userDetails.getUsername());
    }

    //fixme: logika
    @PreAuthorize("hasRole('SUPERUSER')")
    @GetMapping("/get-user-by-id/{id}")
    PasswordRecoveryDTO getUserEmail(@PathVariable Long id) {
        return accountService.getUserEmail(id);
    }

    @GetMapping
    public List<UserView> getAllUser() {
        return accountService.getAllUser();
    }

    @GetMapping("user/{id}")
    public UserView getUserById(@PathVariable Long id) {
        return accountService.getUserById(id);
    }

}