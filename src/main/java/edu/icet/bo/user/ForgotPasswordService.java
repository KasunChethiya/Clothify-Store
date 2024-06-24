package edu.icet.bo.user;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

public class ForgotPasswordService {


    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 4;
    private static final SecureRandom random = new SecureRandom();

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "trishanereid@gmail.com";
    private static final String PASSWORD = "xcnnufmefoytwdfz";

    public static String otpCode;

    public void sendOtp(String email) {
        otpCode = genarateOtp();

        try {
            sendEmail(email,otpCode);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public static String genarateOtp(){
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }

    public static void sendEmail(String recipientEmail, String otp) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host",SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME,PASSWORD);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(USERNAME));
        mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEmail));
        mimeMessage.setSubject("Email Confirmation for Password Reset");
        mimeMessage.setText("your OTP code is "+otp);
        Transport.send(mimeMessage);
    }
}
