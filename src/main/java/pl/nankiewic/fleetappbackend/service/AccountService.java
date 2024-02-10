package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.dto.*;
import pl.nankiewic.fleetappbackend.dto.user.UserDataDTO;
import pl.nankiewic.fleetappbackend.dto.user.UserView;
import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.entity.enums.UserAccountStatus;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.UserData;
import pl.nankiewic.fleetappbackend.entity.VerificationToken;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.exceptions.TokenException;
import pl.nankiewic.fleetappbackend.exceptions.UsernameAlreadyTakenException;
import pl.nankiewic.fleetappbackend.exceptions.WrongOldPasswordException;
import pl.nankiewic.fleetappbackend.mapper.UserDataMapper;
import pl.nankiewic.fleetappbackend.repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {

    private static final long EXPIRE_TOKEN_TIME = 120;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final MailService mailService;

    public void accountActivationByToken(String activationToken) {
        verificationTokenRepository.findByToken(activationToken)
                .map(VerificationToken::getUser)
                .ifPresent(u -> changeStatusAndSave(u, UserAccountStatus.ACTIVE));
    }

    public void blockUserAccountByUserId(Long userId) {
        userRepository.findById(userId)
                .ifPresent(u -> changeStatusAndSave(u, UserAccountStatus.BLOCKED, false));
    }

    public void activateUserAccountByUserId(Long userId) {
        userRepository.findById(userId)
                .ifPresent(u -> changeStatusAndSave(u, UserAccountStatus.ACTIVE, true));
    }

    public void deactivateUserAccountByUserId(Long userId) {
        userRepository.findById(userId)
                .ifPresent(u -> changeStatusAndSave(u, UserAccountStatus.INACTIVE, true));
    }

    public void deleteUserData(String email) {
        userRepository.findUserByEmail(email)
                .map(User::getUserData)
                .ifPresent(userDataRepository::delete);
    }

    public void postResetPassword(EmailDTO emailDTO) {
        var user = userRepository.findUserByEmail(emailDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(""));
        user.setResetCode(createMultitaskingCode());
        user.setResetAt(LocalDateTime.now());
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(EXPIRE_TOKEN_TIME));
        verificationTokenRepository.save(verificationToken);
        userRepository.save(user);
        //mailService.sendResetPassword(user.getEmail(), user.getResetCode(), verificationTokenRepository.findByUser(user).getToken());
    }

    public ResetChangePasswordDTO getResetPassword(String userToken, String userCode) {
        var token = verificationTokenRepository.findByToken(userToken)
                .orElseThrow(() -> new TokenException("token nie istnieje"));
        checkIfTokenDateIsValid(token);
        var user = userRepository.findUserByEmail(token.getUser().getEmail()).orElseThrow();
        checkIfTokenDataIsValid(userToken, user);
        return new ResetChangePasswordDTO(user.getEmail(), null, user.getId(), userToken, userCode);
    }

    public void postNewPassword(ResetChangePasswordDTO resetChangePasswordDTO) {
        var requestUser = userRepository.findUserByEmail(resetChangePasswordDTO.getUser())
                .orElseThrow(() -> new UsernameNotFoundException(""));
        var tokenUser = verificationTokenRepository.findByToken(resetChangePasswordDTO.getVer_token())
                .map(VerificationToken::getUser)
                .orElseThrow(() -> new TokenException("twoja tożsamość nie została potwierdzona"));
        checkIfTokenEmailIsEqualDbEmail(requestUser, tokenUser);
        requestUser.setPassword(passwordEncoder.encode(resetChangePasswordDTO.getNew_password()));
        userRepository.save(requestUser);
        //mailService.sendPasswordInfo(requestUser.getEmail());
    }

    public void changePassword(PasswordDTO passwordDTO, String email) {
        var user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        checkIfPasswordMatches(passwordDTO.getOldPassword(), user.getPassword());
        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(user);
        //mailService.sendPasswordInfo(user.getEmail());
    }

    public IdDTO getUserInvite(String userToken) {
        var token = verificationTokenRepository.findByToken(userToken)
                .orElseThrow(() -> new TokenException("token nie istnieje"));
        checkIfTokenDateIsValid(token);
        var user = userRepository.findUserByEmail(token.getUser().getEmail()).orElseThrow();
        return new IdDTO(user.getEmail(), null, user.getId(), userToken);
    }

    public void newPasswordForNewUser(IdDTO idDTO) {
        var requestUser = userRepository.findUserByEmail(idDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(""));
        var tokenUser = verificationTokenRepository.findByToken(idDTO.getToken())
                .map(VerificationToken::getUser)
                .orElseThrow(() -> new TokenException("token nie istnieje"));
        checkIfEmailsMatches(tokenUser.getEmail(), requestUser.getEmail());
        setAndSaveUser(requestUser, idDTO);
    }

    public List<UserView> getUserByManager(String email) {
        return userRepository.getUserViewsByManagerEmail(email);
    }

    public List<UserView> getAllUser() {
        return userRepository.findUserViewsWithoutRole(Role.ADMIN);
    }

    public void createUserData(UserDataDTO userDataDTO, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow();
        if (userDataRepository.existsByUser(user)) {
            throw new EntityNotFoundException("Dane istnieją");
        }
        UserData userData = userDataMapper.userDataDTOtoUserData(userDataDTO);
        userData.setUser(user);
        userDataRepository.save(userData);
    }

    public void updateUserData(UserDataDTO userDataDTO, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow();
        if (!userDataRepository.existsByUser(user)) {
            throw new EntityNotFoundException("Dane nie istnieją");
        }
        UserData userData = userDataMapper.userDataDTOtoUserData(userDataDTO);
        userData.setUser(user);
        userDataRepository.save(userData);
    }

    public UserDataDTO getUserData(String email, Long id) {
        //todo
        if (userRepository.existsByEmail(email) && userRepository.existsById(id)) {
            User user = userRepository.findUserByEmail(email).orElseThrow();
            User user1 = userRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
            if ((user1.getParentUser() != null && user1.getParentUser() == user) || user.getEmail().equals(user1.getEmail())) {
                if (userDataRepository.existsByUser(user1)) {
                    return userDataMapper.userDataToUserDataDTO(userDataRepository.findByUser(user1));
                } else throw new EntityNotFoundException("Dane nie istnieją");
            } else throw new PermissionDeniedException("brak dostepu");
        } else throw new EntityNotFoundException("Nie znaleziono użytkownika");
    }

    public UserView getUserById(Long id) {
        return userRepository.findUserViewByUserId(id)
                .orElseThrow();
    }

    public void addNewUser(EmailDTO emailDTO, String email) {
        if (userRepository.existsByEmail(emailDTO.getEmail())) {
            User user = userRepository.findUserByEmail(emailDTO.getEmail()).orElseThrow();
            if (!user.isEnabled() && user.getVerificationToken().getExpiryDate().isAfter(LocalDateTime.now())) {
                // mailService.sendInviteMail(emailDTO.getEmail(), user.getVerificationToken().getToken());
            } else if (!user.isEnabled() && user.getVerificationToken().getExpiryDate().isBefore(LocalDateTime.now())) {
                VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
                VerificationToken verificationTokenv2 = new VerificationToken(user);
                verificationToken.setToken(verificationTokenv2.getToken());
                verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(120));
                verificationTokenRepository.save(verificationToken);
            } else throw new UsernameAlreadyTakenException("mail już ma aktywne konto");
        } else if (userRepository.existsByEmail(email) && !userRepository.existsByEmail(emailDTO.getEmail())) {
            User manager = userRepository.findUserByEmail(email).orElseThrow();
            User user = new User();
            user.setParentUser(manager);
            user.setPassword(passwordEncoder.encode(createMultitaskingCode()));
            user.setEmail(emailDTO.getEmail());
            userRegister(user);
            VerificationToken verificationToken = new VerificationToken(user);
            verificationTokenRepository.save(verificationToken);
            //mailService.sendInviteMail(emailDTO.getEmail(), verificationToken.getToken());
        }

    }

    public EmailDTO getUserEmail(Long id) {
        return userRepository.findUserEmailByUserId(id);
    }

    private void userRegister(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setUserAccountStatus(UserAccountStatus.INACTIVE);
        user.setEnabled(false);
        userRepository.save(user);
    }

    private String createMultitaskingCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    private void changeStatusAndSave(User user, UserAccountStatus status) {
        user.setUserAccountStatus(status);
        userRepository.save(user);
    }

    private void changeStatusAndSave(User user, UserAccountStatus status, boolean enabled) {
        user.setUserAccountStatus(status);
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    private void setAndSaveUser(User user, IdDTO idDTO) {
        user.setPassword(passwordEncoder.encode(idDTO.getNewPassword()));
        user.setEnabled(true);
        user.setUserAccountStatus(UserAccountStatus.ACTIVE);
        userRepository.save(user);
        //mailService.sendSuccessInfo(user.getEmail());
    }

    private void checkIfTokenEmailIsEqualDbEmail(User requestUser, User tokenUser) {
        if (!tokenUser.getEmail().equals(requestUser.getEmail())) {
            throw new TokenException("coś poszło nie tak");
        }
    }

    private void checkIfTokenDateIsValid(VerificationToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenException("nie ważny token");
        }
    }

    private void checkIfTokenDataIsValid(String userDbToken, User user) {
        if (!user.getResetCode().equals(userDbToken)) {
            throw new TokenException("błąd tokena");
        }
    }

    private void checkIfPasswordMatches(String oldPassword, String password) {
        if (!passwordEncoder.matches(oldPassword, password)) {
            throw new WrongOldPasswordException();
        }
    }

    private void checkIfEmailsMatches(String tokenEmail, String requestEmail) {
        if (!tokenEmail.equals(requestEmail)) {
            throw new TokenException("błąd danych autoryzacyjnych: email");
        }
    }

}
