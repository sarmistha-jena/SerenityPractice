package steps;

import net.thucydides.core.annotations.Step;
import pages.SigningPage;

public class SigningSteps {

    private SigningPage signingPage;
    @Step
    public void openTheDocuSignPage(String url){
        signingPage.getDriver().get(url);
       // Assert.assertTrue(signingPage.getDriver().getTitle().contains("Review and sign"));
    }

    @Step
    public void signOnTheDocument(){
        signingPage.checkDisclosureBox();
        signingPage.clickContinueButton();
        signingPage.clickSignButton();
        signingPage.clickSignAndAdoptButton();
    }
}
