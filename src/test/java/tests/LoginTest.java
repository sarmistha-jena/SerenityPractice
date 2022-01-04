package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.CartStep;
import steps.LoginStep;
import steps.ProductStep;


@RunWith(SerenityRunner.class)
public class LoginTest {
    @Steps
    LoginStep loginStep;
    @Steps
    ProductStep productStep;
    @Steps
    CartStep cartStep;

    @Test
    public void applicationLoginTest() {
        loginStep.launchApplication();
        loginStep.loginAsUser();
        loginStep.userShouldBeAbleToLogin();
        loginStep.clickOnProduct();
        productStep.addToCart();
        productStep.userConfirmProductAddition();
        productStep.userNavigatesToCartPage();
        cartStep.verifyCartPage();
    }
}
