package tests;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMail {

    public static void check(String host, String storeType, String user,
                        String password) {
        try {
            String emailLink = "";
            //create properties field
            Properties properties = new Properties();

            properties.put("mail.imap.host",host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.imap.socketFactory.fallback", "false");
            properties.setProperty("mail.imap.socketFactory.port",String.valueOf(993));
            Session emailSession = Session.getDefaultInstance(properties);

            //create the imap store object and connect with the pop server
            Store store = emailSession.getStore("imap");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            MimeMessage[] messages = (MimeMessage[]) emailFolder.getMessages();
            //System.out.println("messages.length---" + messages.length);
            int n=messages.length;

            //change n- to number of emails you want to dig through
            for (int i = n-5; i<n; i++) {
                MimeMessage message = messages[i];
                ArrayList<String> links = new ArrayList<String>();

                if(message.getSubject().contains("Verify your email for project") || message.getSubject().contains("Sign in to project")){
                    //System.out.println("Subject: " + message.getSubject());
                    MimeMultipart messageBody = (MimeMultipart) message.getContent();

                    String desc = messageBody.getBodyPart(1).getContent().toString();

                    //System.out.println("Description: " + desc);
                    Pattern linkPattern = Pattern.compile("href=\"(.*?)\"",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
                    Matcher pageMatcher = linkPattern.matcher(desc);
                    while(pageMatcher.find()){
                        links.add(pageMatcher.group(1));
                    }
                }else{
                    System.out.println("Email:"+ i + " is not a wanted email");
                }
                for(String temp:links){
                    if(temp.contains("emailSubjectHere")){
                        //System.out.println(temp);
                    }
                }


            }
//            emailLink = Jsoup.parse((emailLink).toString()).text();
//            System.out.println(emailLink);
//            return emailLink;

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String host = "imap.gmail.com";
        String mailStoreType = "imap";
        String username = "user@gmail.com";
        String password = "*****";

        check(host, mailStoreType, username, password);


    }

}
