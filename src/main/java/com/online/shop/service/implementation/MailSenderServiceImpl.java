package com.online.shop.service.implementation;

import com.online.shop.domain.User;
import com.online.shop.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${server.port}")
    private int port;
    @Value("${server.hostname}")
    private String hostname;
    @Value("${mail.server.username}")
    private String username;




    @Override
    public void sendActivateCode(User user) {
        String subject = "please activate your account";
        String content = "Please activate your, go to the link: \n"
                + "http://" + hostname + ":" + port + "/users/activate/" + user.getActiveCode();

        sendMail(user.getEmail(), subject, content);
    }

    private void sendMail(String email, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

}
