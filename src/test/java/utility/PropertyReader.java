package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    public static String getEmailUser()  {
        String emailId = null;
        try {
            emailId= readPropertiesFile("src/test/resources/user.properties").getProperty("USER_EMAIL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emailId;
    }
    public static String getEmailPassword() {
        String password = null;
        try {
            password = readPropertiesFile("src/test/resources/user.properties").getProperty("USER_PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }
    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            assert fis != null;
            fis.close();
        }
        return prop;
    }
}
