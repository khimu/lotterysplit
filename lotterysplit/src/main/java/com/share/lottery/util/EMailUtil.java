package com.share.lottery.util;

import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.share.lottery.model.User;
import com.share.lottery.service.MailEngine;

public class EMailUtil {

    public static void sendMessage(String templateName, SimpleMailMessage message, MailEngine mailEngine, User user, String msg) {

        message.setTo("Split Lottery <info.splitlotto@gmail.com>");

        Map<String, String> model = new HashMap<String, String>();
        model.put("email", user.getEmail());
        model.put("name", user.getFirstName() + " " + user.getLastName());
        model.put("facebookid", user.getFacebookId());
        model.put("message", msg);
        mailEngine.sendMessage(message, templateName, model);
    }
    
    public static void sendJobEmail(String templateName, String posterEmail, String link, String subject, String email, MailEngine mailEngine, SimpleMailMessage simpleMailMessage) {
        try {
        	ClassPathResource resource = new ClassPathResource("/khimung-resume.doc");
            
        	Map<String, String> model = new HashMap<String, String>();
            model.put("link", link);
            model.put("email", posterEmail);
            
        	mailEngine.setMessageTemplate(simpleMailMessage, templateName, model);

            mailEngine.sendMessage(new String[] {email}, simpleMailMessage.getFrom(), 
                     resource, simpleMailMessage.getText(),
                    subject, "khimung-resume.doc");
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    public static void sendJobListings(String templateName, String email, SimpleMailMessage message, String jobListings, MailEngine mailEngine) {
        message.setTo(email);

        Map<String, String> model = new HashMap<String, String>();
        model.put("message", jobListings);
        mailEngine.sendMessage(message, templateName, model);
    }    
 
    public static void sendSpam(String templateName, String email, SimpleMailMessage message, String name, MailEngine mailEngine) {
        message.setTo(email);

        Map<String, String> model = new HashMap<String, String>();
        model.put("message", "Lots of freebies here:  http://www.splitlottery.com");
        model.put("name", name);
        model.put("email", email);
        mailEngine.sendMessage(message, templateName, model);
    }
    
    public static void sendOrderNotification(String templateName, SimpleMailMessage message, MailEngine mailEngine, User user, String msg) {

        message.setTo("Split Lottery <info.splitlotto@gmail.com>");

        Map<String, String> model = new HashMap<String, String>();
        model.put("email", user.getEmail());
        model.put("name", user.getFirstName() + " " + user.getLastName());
        model.put("facebookid", user.getFacebookId());
        model.put("message", msg);
        mailEngine.sendMessage(message, templateName, model);
    }
    
    public static void orderComplete(String templateName, SimpleMailMessage message, MailEngine mailEngine, User user, String msg) {

        message.setTo(user.getEmail());

        Map<String, String> model = new HashMap<String, String>();
        model.put("email", "info.splitlotto@gmail.com");
        model.put("user.username", user.getUsername());
        model.put("applicationURL", "http://www.splitlottery.com");
        model.put("message", msg);
        mailEngine.sendMessage(message, templateName, model);
    }
	
    public static void inviteToPool(User user, String templateName, SimpleMailMessage message, MailEngine mailEngine, String email, String msg) {

        message.setTo(email);

        Map<String, String> model = new HashMap<String, String>();
        model.put("email", "info.splitlotto@gmail.com");
        model.put("name", user.getFirstName() + " " + user.getLastName());
        model.put("facebookid", user.getFacebookId());
        model.put("message", msg);
        mailEngine.sendMessage(message, templateName, model);
    }
}
