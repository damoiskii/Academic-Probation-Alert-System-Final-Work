/*
----------------------------------------------------------------------------------
                    Module: Artificial Intelligence (CMP4011)
                    Lab Tutor: Mr. Howard James
                    Class Group: Tuesdays @6pm
                    Year: 2023/2024 Semester 2
                    Assessment: Programming Group Project
                    Group Members:
                        Damoi Myers - 1703236
----------------------------------------------------------------------------------
*/

package com.app.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${DEFAULT_EMAIL_FROM}")
    private String sender;

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();

        sender = email.getSender() != null ? email.getSender() : sender;

        message.setFrom(sender);
        message.setTo(email.getRecipient());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        try{
            emailSender.send(message);

            logger.info("Email sent!");
        }
        catch (MailException e){
            logger.error("Error sending email: " + e.getMessage());
        }
    }

    @Override
    public void sendAlertEmail(Email email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(email.getSender() != null ? email.getSender() : sender);
        helper.setSubject(email.getSubject());

        helper.setText(email.getContent(), false);

        // Send to
        InternetAddress[] recipientAddresses = getRecipients(email.getCc().replace("[", "").replace("]", "").split(","));
        for(InternetAddress recipientAddress : recipientAddresses) {
            if(recipientAddress != null) helper.addTo(recipientAddress);
        }

        // Copy
        recipientAddresses = getRecipients(email.getCc().replace("[", "").replace("]", "").split(","));
        for(InternetAddress recipientAddress : recipientAddresses) {
            if(recipientAddress != null) helper.addTo(recipientAddress);
        }

        emailSender.send(message);
    }

    private InternetAddress[] getRecipients(String[] emailList) {
        InternetAddress[] recipientAddresses = new InternetAddress[emailList.length];

        int counter = 0;
        for (String recipient : emailList) {
            try {
                recipientAddresses[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            catch (AddressException e) {
                logger.error("Error parsing email address [" + recipient + "]: " + e.getMessage());
            }
            catch (Exception e) {
                logger.error("Error parsing email address [Exception] [" + recipient + "]: " + e.getMessage());
            }
        }

        return recipientAddresses;
    }
}
