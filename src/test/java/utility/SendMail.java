package utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    public static void main(String[] args) {

        // change accordingly
        final String username = "Sarmistha.Jena@outlook.com";

        // change accordingly
        final String password = "*******";

        // or IP address
        final String host = "localhost";

        // Get system properties
        Properties props = new Properties();

        // enable authentication
        props.put("mail.smtp.auth", host);

        // enable STARTTLS
        props.put("mail.smtp.starttls.enable", "true");

        // Setup mail server
        props.put("mail.smtp.host", "outlook.office365.com");

        // TLS Port
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        // creating Session instance referenced to
        // Authenticator object to pass in
        // Session.getInstance argument
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {

                    //override the getPasswordAuthentication method
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(username,
                                password);
                    }
                });

        try {

            // compose the message
            // javax.mail.internet.MimeMessage class is
            // mostly used for abstraction.
            Message message = new MimeMessage(session);

            // header field of the header.
            message.setFrom(new InternetAddress("Sarmistha.Jena@outlook.com"));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("Sarmistha_Jena@epam.com"));
            message.setSubject("hello");
            message.setText("Yo it has been sent");

            Transport.send(message);		 //send Message

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}