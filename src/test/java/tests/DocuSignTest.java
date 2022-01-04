package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.SigningSteps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RunWith(SerenityRunner.class)
public class DocuSignTest {

    @Steps
    SigningSteps signingSteps;

    @Pending
    @Test
    public void signingInDocuSignTest() throws InterruptedException {
        String urlFromMail = "";
        ArrayList<String> emailLinks = (ArrayList<String>) new EmailUtility().getEmbeddedUrlsFromEmail();
        Set<String> uniqueLinks = new HashSet<>(emailLinks);
        for (String temp : uniqueLinks) {
            if (temp.contains("na4")) {
                System.out.println("************LINKS FROM EMAIL*****************");
                System.out.println(temp);
                urlFromMail = temp;
            }
        }
        signingSteps.openTheDocuSignPage(urlFromMail);
        Thread.sleep(5000);
        signingSteps.signOnTheDocument();
        Thread.sleep(5000);
    }

}
