package tests;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailAccess {

    public static void check(String host, String storeType, String user,
                             String password)
    {
        try {

            //create properties field
            Properties properties = new Properties();

//            properties.put("mail.imap.host",host);
//            properties.put("mail.imap.port", "993");

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", 993);

            properties.put("mail.imap.starttls.enable", "true");
            properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.imap.socketFactory.fallback", "false");
            properties.setProperty("mail.imap.socketFactory.port",String.valueOf(993));
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imap");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            int n=messages.length;
            for (int i = 0; i<n; i++) {
                Message message = messages[i];
                ArrayList<String> links = new ArrayList<String>();
                if(message.getSubject().contains("ENGX - Call for Nominations As a Sensei")){
                    String desc=message.getContent().toString();

                    // System.out.println(desc);
                    Pattern linkPattern = Pattern.compile(" <a\\b[^>]*href=\"([^\"]*)[^>]*>(.*?)</a>",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
                    Matcher pageMatcher = linkPattern.matcher(desc);
                    while(pageMatcher.find()){
                        links.add(pageMatcher.group(1));
                    }

                }else{
                    System.out.println("Email:"+ i + " is not a wanted email");
                }
                for(String temp:links){
                    if(temp.contains("user-register")){
                        System.out.println(temp);
                    }
                }

                /*System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());*/

            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

//        String host = "imap.gmail.com";
//        String mailStoreType = "imap";
//        String username = "rameshakur@gmail.com";
//        String password = "*****";
        String host = "smtp.office365.com";
        String mailStoreType = "smtp";
        String username = "Sarmistha_Jena@epam.com";
        String password = "******";

        check(host, mailStoreType, username, password);


    }

}
