package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        String url1 = "REVIEW DOCUMENT\n" +
                "\n" +
                "https://na4=2Edocusign=2Enet/Member/EmailStart=2Easpx?a=3D032f71ec-0d53=-4bf8-83fa-1ca4de3e0d37&acct=3D14e4b069-489e-47a0-a164-c558df3dc14d&er=3D=5422dea2-4f30-4689-a252-cc783d0acba6\n" +
                "\n" +
                "If clicking the link does not work, you can highlight and copy the enti=\n" +
                "re line above and paste it into your browser to get started=2E";
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((\\/\\/))+[\\w\\d=\\/?\\-&]+)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(url1);

        while (urlMatcher.find()) {
            containedUrls.add(url1.substring(urlMatcher.start(1),
                    urlMatcher.end(1)));
        }


        for (String temp : containedUrls) {
            System.out.println("************************************************************");
            System.out.println(temp);
        }
    }
}
