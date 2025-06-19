package com.gevernova.petvacination.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService implements Notifications{

        private final JavaMailSender emailSender;

        public boolean sendRegistrationEmail(String to,String name){
                SimpleMailMessage message=new SimpleMailMessage();
                message.setTo(to);
                message.setSubject("Your Pet has been registered");
                message.setText(name+" your Pet has been registered for Vaccination");
                try{
                        emailSender.send(message);
                }catch(Exception e){
                        return false;
                }
                return true;
        }

}
