//package tehama.society.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//    public static final String NEW_ACCOUNT_CREATED = "New Account Created.";
//
//    private final JavaMailSender emailSender;
//
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    @Async
//    public void sendNewAccountMessage(String firstName, String to) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setSubject(NEW_ACCOUNT_CREATED);
//            message.setFrom(fromEmail);
//            message.setTo(to);
////            message.setText("Hey, It worked!!! Haha!");
//            message.setText(getEmailMessage(firstName));
//            emailSender.send(message);
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            throw new RuntimeException(exception.getMessage());
//        }
//    }
//
//    private String getEmailMessage(String firstName) {
//        return "Hello " + firstName + ",\n\nYour account has been created.\n\nThe support team.\n\nThanks.";
//    }
//
//
//}
package tehama.society.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    public static final String NEW_ACCOUNT_CREATED = "New Account Created.";

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendNewAccountMessage(String firstName, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_ACCOUNT_CREATED);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getEmailMessage(firstName));
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private String getEmailMessage(String firstName) {
        return "Hello " + firstName + ",\n\nYour account has been created.\n\nThe support team.\n\nThanks.";
    }
}

