package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.*;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserAccountStatus;
import pl.nankiewic.fleetappbackend.Entity.UserData;
import pl.nankiewic.fleetappbackend.Entity.VerificationToken;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Exception.TokenException;
import pl.nankiewic.fleetappbackend.Exception.WrongOldPasswordException;
import pl.nankiewic.fleetappbackend.Mapper.UserDataMapper;
import pl.nankiewic.fleetappbackend.Mapper.UserMapper;
import pl.nankiewic.fleetappbackend.Repository.*;
import pl.nankiewic.fleetappbackend.Security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.Security.JWTokenUtility;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountService {
    private final UserAccountStatusRepository userAccountStatusRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTokenUtility tokenUtility;
    private final UserDataMapper mapper;
    private final UserMapper userMapper;
    private final MailService mailService;
    @Autowired
    public AccountService(UserAccountStatusRepository userAccountStatusRepository,
                          VerificationTokenRepository verificationTokenRepository,
                          UserDataRepository userDataRepository, PasswordEncoder passwordEncoder,
                          UserRepository userRepository, RoleRepository roleRepository,
                          JWTokenUtility tokenUtility, UserDataMapper mapper,
                          UserMapper userMapper, MailService mailService) {
        this.userAccountStatusRepository = userAccountStatusRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenUtility = tokenUtility;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    public AuthenticationResponse login(Authentication authentication){
        String jwt=tokenUtility.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user=userRepository.findUserByEmail(userDetails.getUsername());
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        return new AuthenticationResponse(jwt, userDetails.getUsername(), user.getId(), user.getRole().getRoleName());
    }
    public void saveUserData(UserDataDTO userDataDTO, String email){
        User user= userRepository.findUserByEmail(email);
        if(userDataRepository.existsByUser(user)){
            throw new EntityNotFoundException("Dane istnieją");
        }
        UserData userData=mapper.userDataDTOtoUserData(userDataDTO);
        userData.setUser(user);
        userDataRepository.save(userData);

    }

    public void updateUserData(UserDataDTO userDataDTO, String email) {
        User user = userRepository.findUserByEmail(email);
        if (!userDataRepository.existsByUser(user)) {
            throw new EntityNotFoundException("Dane nie istnieją");
        }
        UserData userData = mapper.userDataDTOtoUserData(userDataDTO);
        userData.setUser(user);
        userDataRepository.save(userData);
    }

    public UserDataDTO getUserData(String email, Long id) {
        if (userRepository.existsByEmail(email) && userRepository.existsById(id)) {
            User user = userRepository.findUserByEmail(email);
            User user1 = userRepository.findUserById(id);
            if ((user1.getUser() != null && user1.getUser() == user) || user.getEmail().equals(user1.getEmail())) {
                if (userDataRepository.existsByUser(user1)) {
                    return mapper.userDataToUserDataDTO(userDataRepository.findByUser(user1));
                } else throw new EntityNotFoundException("Dane nie istnieją");
            } else throw new PermissionDeniedException("brak dostepu");
        } else throw new EntityNotFoundException("Nie znaleziono użytkownika");
    }

    public void deleteUserData(String email) {
        User user = userRepository.findUserByEmail(email);
        if (!userDataRepository.existsByUser(user)) {
            throw new EntityNotFoundException("Nie znaleziono danych użytkownika");
        }
        userDataRepository.findByUser(user);
        userDataRepository.deleteById(userDataRepository.findByUser(user).getId());
    }

    public void getAccountActivation(String activation_token){
        if(activation_token!= null){
            if(verificationTokenRepository.existsVerificationTokenByTokenIs(activation_token)){
                VerificationToken token = verificationTokenRepository.findByToken(activation_token);
                User user = userRepository.findUserByEmail(token.getUser().getEmail());
                activation(user);
            }else{
                throw new TokenException("token nie istnieje");
            }
        }else {
            throw new TokenException("Pusty token");
        }
    }
    public void activation(User user){
        user.setUserAccountStatus(userAccountStatusRepository.findByUserAccountStatusName("ACTIVE"));
        userRepository.save(user);
    }

    public String createResetCode() {
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
    public void postResetPassword(EmailDTO emailDTO){
        if(userRepository.existsByEmail(emailDTO.getEmail()) && emailDTO.getEmail()!=null){
            User user=userRepository.findUserByEmail(emailDTO.getEmail());
            user.setResetCode(createResetCode());
            user.setResetAt(LocalDateTime.now());
            VerificationToken verificationToken=verificationTokenRepository.findByUser(user);
            verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(120));
            verificationTokenRepository.save(verificationToken);
            userRepository.save(user);
            mailService.sendResetPassword(user.getEmail(), user.getResetCode(), verificationTokenRepository.findByUser(user).getToken());
        } else throw new UsernameNotFoundException("nie znaleziono użytkownika");
    }
    public ResetChangePasswordDTO getResetPassword(String user_token, String user_code){
        if(user_token!= null){
            if(verificationTokenRepository.existsVerificationTokenByTokenIs(user_token)){
                VerificationToken token = verificationTokenRepository.findByToken(user_token);
                if(token.getExpiryDate().isBefore(LocalDateTime.now())){
                    throw new TokenException("nie ważny token");
                }
                User user = userRepository.findUserByEmail(token.getUser().getEmail());
                if(user.getResetCode().equals(user_code)){
                    return new ResetChangePasswordDTO(user.getEmail(),
                            null,
                            user.getId(),
                            user_token,
                            user_code);
                }else throw new TokenException("błąd ogólny");
            }else{
                throw new TokenException("token nie istnieje");
            }
        }else {
            throw new TokenException("Pusty token");
        }
    }
    public void postNewPassword(ResetChangePasswordDTO resetChangePasswordDTO){
        if(resetChangePasswordDTO.getUser()!=null && userRepository.existsByEmail(resetChangePasswordDTO.getUser())){
            User user=userRepository.findUserByEmail(resetChangePasswordDTO.getUser());
            if(verificationTokenRepository.existsByToken(resetChangePasswordDTO.getVer_token())){
                VerificationToken verificationToken= verificationTokenRepository.findByToken(resetChangePasswordDTO.getVer_token());
                User user1=verificationToken.getUser();
                if(user1.getEmail().equals(user.getEmail())){
                    user.setPassword(passwordEncoder.encode(resetChangePasswordDTO.getNew_password()));
                    userRepository.save(user);
                    mailService.sendPasswordInfo(user.getEmail());
                }else throw new TokenException("coś poszło nie tak");
            }else throw new TokenException("twoja tożsamość nie została potwierdzona");
        }else throw new TokenException("nie nadałeś nowego hasła lub twoja tożsamość nie została potwierdzona ");
    }
    //zmiana hasła
    public void changePassword(PasswordDTO passwordDTO, String email){
        User user=userRepository.findUserByEmail(email);
        if(passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword()))
        {
            user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            userRepository.save(user);
            mailService.sendPasswordInfo(user.getEmail());
        } else throw new WrongOldPasswordException();
    }
    //dodanie konta usera i show
    public IdDTO getUserInvite(String userToken){
        if(userToken!= null){
            if(verificationTokenRepository.existsVerificationTokenByTokenIs(userToken)){
                VerificationToken token = verificationTokenRepository.findByToken(userToken);
                if(token.getExpiryDate().isBefore(LocalDateTime.now())){
                    throw new TokenException("link już wygasł poproś o nowe zaproszenie");
                } else {
                    User user = userRepository.findUserByEmail(token.getUser().getEmail());
                    return new IdDTO(user.getEmail(),null, user.getId(), userToken);
                }
            } else {
                throw new TokenException("błędny link");
            }
        } else {
            throw new TokenException("brak tokena");
        }
    }
    public void newPasswordForNewUser(IdDTO idDTO){
        if(idDTO.getEmail()!=null && userRepository.existsByEmail(idDTO.getEmail())){
            User user=userRepository.findUserByEmail(idDTO.getEmail());
            if(verificationTokenRepository.existsByToken(idDTO.getToken())){
                VerificationToken verificationToken= verificationTokenRepository.findByToken(idDTO.getToken());
                User user1=verificationToken.getUser();
                if(user1.getEmail().equals(user.getEmail())){
                    user.setPassword(passwordEncoder.encode(idDTO.getNewPassword()));
                    user.setEnabled(true);
                    user.setUserAccountStatus(userAccountStatusRepository.findByUserAccountStatusName("ACTIVE"));
                    userRepository.save(user);
                    mailService.sendSuccessInfo(user.getEmail());
                }else throw new TokenException("błąd danych autoryzacyjnych: email");
            }else throw new TokenException("błąd danych autoryzacyjnych: token");
        }else throw new TokenException("twoja tożsamość nie została potwierdzona ");
    }
    public Iterable<User> getUserByManager(String email) {
        User manager= userRepository.findUserByEmail(email);
        return userRepository.findByUser(manager);
    }
    public Iterable<UserDTO> getAllUser(){
        return userMapper.userToUserDTOs(userRepository.findAllByRoleIsNot(roleRepository.findRoleByRoleName("ROLE_ADMIN")));
    }
    public void blockOrUnblockUser(BlockOrUnblock blockOrUnblock){
        if(userRepository.existsById(blockOrUnblock.getId())){
            User user=userRepository.findUserById(blockOrUnblock.getId());
            UserAccountStatus userAccountStatus= userAccountStatusRepository.findByUserAccountStatusName(blockOrUnblock.getUserStatus());
            switch (userAccountStatus.getUserAccountStatusName()) {
                case "ACTIVE":
                case "INACTIVE":
                    user.setEnabled(true);
                    user.setUserAccountStatus(userAccountStatus);
                    break;
                case "BLOCKED":
                    user.setEnabled(false);
                    user.setUserAccountStatus(userAccountStatus);
                    break;
            }
            userRepository.save(user);
        }
    }

    public UserDTO getUserById(Long id) {
        return userMapper.userToUserDTO(userRepository.findUserById(id));
    }
}
