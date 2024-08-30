package com.eNach_Cancellation.Utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUtility {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.reciver}")
    private String reciver;

    private final Logger logger = LoggerFactory.getLogger(SendEmailUtility.class);


    public void sendEmailWithAttachment(byte[] excelData) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sender);
        helper.setTo(reciver);
        helper.setText("Dear Sir, \n\n\n Please find the below attached sheet. \n\n\n\n Regards\n It Support.");
        helper.setSubject("Cancellation mail");

        InputStreamSource attachmentSource = new ByteArrayResource(excelData);
        helper.addAttachment("Cancellation-status-report.xlsx", attachmentSource);
        logger.info("Generate excel and send to mail {}", reciver);
        mailSender.send(message);
    }
}