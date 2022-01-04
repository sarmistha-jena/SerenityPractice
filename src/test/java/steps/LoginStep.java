package steps;

import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import pages.LoginPage;

public class LoginStep  {
    private LoginPage loginPage;

    @Step("Navigate to the home page")
    public void launchApplication(){
        loginPage.open();
    }
    @Step
    public void loginAsUser() {
        loginPage.doLogin();
    }
    @Step
    public void userShouldBeAbleToLogin(){
        Assert.assertEquals("Welcome test",loginPage.getWelcomeMsg());
    }
    @Step
    public void clickOnProduct(){
        loginPage.clickOnProduct();
    }

}
