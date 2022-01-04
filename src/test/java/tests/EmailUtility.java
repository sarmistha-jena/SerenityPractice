package tests;

import utility.PropertyReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtility {
    private static final String SERVER_SMTP = "smtp.office365.com";
    private static final int PORT_SERVER_SMTP = 587;
    private static final String USER_EMAIL = PropertyReader.getEmailUser();
    private static final String USER_PASSWORD = PropertyReader.getEmailPassword(); // Password

    private static final String FROM = "Sarmistha.Jena@outlook.com";
    private static final String TO = "Sarmistha.Jena@outlook.com";

    private static final String SUBJECT = "Test";
    private static final String MESSAGE_CONTENT = "Test THE AUTOMATED MESSAGE";


    public static void main(final String[] args) {
        ArrayList<String> emailLinks = (ArrayList<String>) new EmailUtility().getEmbeddedUrlsFromEmail();
        Set<String> uniqueLinks = new HashSet<>(emailLinks);
        for (String temp : uniqueLinks) {
            if (temp.contains("now.eloqua.com") && !(temp.contains("image"))) {
                System.out.println("************LINKS FROM EMAIL*****************");
                System.out.println(temp);
            }
        }
    }

    public List<String> getEmbeddedUrlsFromEmail() {
        ArrayList<String> links = new ArrayList<String>();
        try {

            Properties props = new Properties();
            props.setProperty("mail.imap.ssl.enable", "true");
            Session mailSession = Session.getInstance(props);
            mailSession.setDebug(true);
            Store mailStore = mailSession.getStore("imap");
            mailStore.connect("outlook.office365.com", USER_EMAIL, USER_PASSWORD);

            //create the folder object and open it
            Folder emailFolder = mailStore.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

            //search for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message[] messages = emailFolder.search(unseenFlagTerm);

            System.out.println("messages.length---" + messages.length);
            int n = messages.length;
            for (int i = n-1; i < n; i++) {
                Message message = messages[i];

                if (message.getSubject().contains("Please DocuSign:")) {
                    MimeMultipart messageBody = (MimeMultipart) message.getContent();
                    String desc = messageBody.getBodyPart(0).getContent().toString();
                    /*System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    System.out.println(desc);*/
                    //String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                    //String urlRegex = "<(.*)>";
                    String urlRegex = "((https?|ftp|gopher|telnet|file):((\\/\\/))+[\\w\\d=\\/?\\-&.]+)";
                    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
                    Matcher urlMatcher = pattern.matcher(desc);
                    //Pattern linkPattern = Pattern.compile("href=\"(.*?)\"",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
                    /*Pattern linkPattern = Pattern.compile("<a\\b[^>]*href=\"([^\"]*)[^>]*>(.*?)</a>",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
                    Matcher urlMatcher = linkPattern.matcher(desc);*/


                    while (urlMatcher.find()) {
                        links.add(desc.substring(urlMatcher.start(1),
                                urlMatcher.end(1)));
                    }
                    /*System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("Text: " + message.getContent().toString());
                    System.out.println("Text: " + this.getTextFromMessage(message));*/
                } else {
                    System.out.println("########################Email:" + i + " is not a wanted email");
                }


            }
            //close the store and folder objects
            emailFolder.close(false);
            mailStore.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }

    public void sendEmail(String to,String subject) {
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_EMAIL, USER_PASSWORD);
            }

        });

        try {
            final Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(FROM));
            message.setSubject(subject);
            message.setText(MESSAGE_CONTENT);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (final MessagingException ex) {
            System.out.println(" " + ex);
        }
    }

    public Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVER_SMTP);
        config.put("mail.smtp.port", PORT_SERVER_SMTP);
        return config;
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

}
