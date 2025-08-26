package com.iassure.util;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;


@Component
@Log4j2
public class EmailUtil {
	
	@Value("${spring.mail.host}")
	private String mailhost;
	
	@Value("${spring.mail.username}")
	private String mailSenderUserName;
	
	@Value("${spring.mail.password}")
	private String mailPassword;
	
	@Value("${spring.mail.port}")
	private String mailPort;
	
	@Value("${spring.mail.protocol}")
	private String mailProtocol;
	
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String mailSmtpAuth;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String starttlsEnabled;
	
	@Value("${email.isEnabled}")
	private String isMailEnabled;


    public void sendEmail(String bodyText, List<String> toMails, String subject) throws Exception {

        if (isMailEnabled.equalsIgnoreCase("Y")) {
            final String fromMail = mailSenderUserName.trim();
            final String password = mailPassword.trim();

            Properties props = getProperties();

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(fromMail, password);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromMail));

            // Set recipients from the list of toMails
            if (toMails != null && !toMails.isEmpty()) {
                InternetAddress[] toAddresses = toMails.stream()
                        .map(email -> {
                            try {
                                return new InternetAddress(email);
                            } catch (Exception e) {
                                log.info("Invalid email address: " + email);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .toArray(InternetAddress[]::new);

                message.setRecipients(Message.RecipientType.TO, toAddresses);
            } else {
                throw new Exception("Recipient email list is empty.");
            }

            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(bodyText, "text/html");
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);

            // Send email in a new thread
            new Thread(() -> {
                try {
                    Transport.send(message);
                } catch (Exception e) {
                    log.info("Exception in sendEmail: " + e);
                }
            }).start();
        }
    }

    private @NotNull Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.host", mailhost);
        props.put("mail.smtp.port", mailPort);
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.socketFactory.port", mailPort);
        props.put("mail.smtp.startssl.enable", starttlsEnabled);
        return props;
    }


}
