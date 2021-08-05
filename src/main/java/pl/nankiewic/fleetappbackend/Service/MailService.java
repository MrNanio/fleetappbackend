package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
//    private final JavaMailSender javaMailSender;
//    @Autowired
//    public MailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//    @Async
//    public void sendActivationEmail(String to, String token) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setFrom("FleetAssist <myfleetassist@gmail.com>");
//        msg.setSubject("Activation your account");
//        msg.setText("To confirm your account, please click here : "
//                +"http://localhost:8080/user/activation-account?activation_token="+token);
//        javaMailSender.send(msg);
//    }
//    @Async
//    public void sendPasswordInfo(String to) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setFrom("FleetAssist <myfleetassist@gmail.com>");
//        msg.setSubject("Your password has been changed successfully");
//        msg.setText("Your password has been changed successfully");
//        javaMailSender.send(msg);
//    }
//    @Async
//    public void sendResetPassword(String to, String password, String token) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setFrom("FleetAssist <myfleetassist@gmail.com>");
//        msg.setSubject("New password");
//        msg.setText("Reset code: "+password+" or if you want click on link and change password "+
//                "http://localhost:4200/account/setNewPassword?u="+token+"&c="+password);
//        javaMailSender.send(msg);
//    }
//    @Async
//    public void sendInviteMail(String to, String token) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setFrom("FleetAssist <myfleetassist@gmail.com>");
//        msg.setSubject("Your invite");
//        msg.setText("Your invite link: "+"http://localhost:4200/account/invite?u="+token);
//        javaMailSender.send(msg);
//    }
//    @Async
//    public void sendSuccessInfo(String to) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setFrom("FleetAssist <myfleetassist@gmail.com>");
//        msg.setSubject("Success");
//        msg.setText("Success");
//        javaMailSender.send(msg);
//    }
}
