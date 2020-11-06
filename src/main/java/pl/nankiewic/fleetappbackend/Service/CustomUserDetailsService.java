package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.EmailDTO;
import pl.nankiewic.fleetappbackend.Entity.Role;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.VerificationToken;
import pl.nankiewic.fleetappbackend.Exception.UsernameAlreadyTakenException;
import pl.nankiewic.fleetappbackend.Repository.RoleRepository;
import pl.nankiewic.fleetappbackend.Repository.UserAccountStatusRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VerificationTokenRepository;
import pl.nankiewic.fleetappbackend.Security.AuthenticationRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    UserAccountStatusRepository userAccountStatusRepository;
    VerificationTokenRepository verificationTokenRepository;
    MailService mailService;
    PasswordEncoder passwordEncoder;
    @Autowired
    public CustomUserDetailsService(RoleRepository roleRepository, UserRepository userRepository,
                                    UserAccountStatusRepository userAccountStatusRepository,
                                    VerificationTokenRepository verificationTokenRepository, MailService mailService,
                                    PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userAccountStatusRepository = userAccountStatusRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(" ",
                    " ", true, true, true, true,
            getAuthorities(roleRepository.findRoleByRoleName("ROLE_USER")));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(
            Role roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles.getRoleName()));
        roles.getPrivileges().stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .forEach(authorities::add);

        return authorities;
    }

    public void save(AuthenticationRequest authenticationRequest) {
        User user = new User();
        user.setEmail(authenticationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
        superuserRegister(user);
    }
    public void superuserRegister(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(roleRepository.findRoleByRoleName("ROLE_SUPERUSER"));
        user.setUserAccountStatus(userAccountStatusRepository.findByUserAccountStatusName("INACTIVE"));
        user.setEnabled(true);
        userRepository.save(user);
        activationToken(userRepository.findUserByEmail(user.getEmail()));
    }
    public void userRegister(User user){
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(roleRepository.findRoleByRoleName("ROLE_USER"));
        user.setUserAccountStatus(userAccountStatusRepository.findByUserAccountStatusName("INACTIVE"));
        user.setEnabled(false);
        userRepository.save(user);
    }
    public void activationToken(User user){
        VerificationToken verificationToken=new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        mailService.sendActivationEmail(user.getEmail(),verificationTokenRepository.findByUser(user).getToken());
    }
    public String createPasswordCode() {
        int lLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = lLimit + (int) (random.nextFloat() * (rightLimit - lLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
    public void addNewUser(EmailDTO emailDTO, String email){
        if(userRepository.existsByEmail(emailDTO.getEmail())){
            User user=userRepository.findUserByEmail(emailDTO.getEmail());
            if(!user.isEnabled() && user.getVerificationToken().getExpiryDate().isAfter(LocalDateTime.now())){
                mailService.sendInviteMail(emailDTO.getEmail(), user.getVerificationToken().getToken());
            } else if(!user.isEnabled() && user.getVerificationToken().getExpiryDate().isBefore(LocalDateTime.now())) {
               VerificationToken verificationToken=verificationTokenRepository.findByUser(user);
                VerificationToken verificationTokenv2=new VerificationToken(user);
               verificationToken.setToken(verificationTokenv2.getToken());
               verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(120));
               verificationTokenRepository.save(verificationToken);
            } else  throw new UsernameAlreadyTakenException("mail już ma aktywne konto");
        } else if(userRepository.existsByEmail(email) && !userRepository.existsByEmail(emailDTO.getEmail())){
            User manager= userRepository.findUserByEmail(email);
            User user=new User();
            user.setUser(manager);
            user.setPassword(passwordEncoder.encode(createPasswordCode()));
            user.setEmail(emailDTO.getEmail());
            userRegister(user);
            VerificationToken verificationToken=new VerificationToken(user);
            verificationTokenRepository.save(verificationToken);
            mailService.sendInviteMail(emailDTO.getEmail(), verificationToken.getToken());
        }
    }
    public List<User> listAll() {
        return userRepository.findAll();
    }
}
