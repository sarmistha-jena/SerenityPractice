package steps;

import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import pages.CartPage;

public class CartStep {
    private CartPage cartPage;

    @Step
    public void verifyCartPage(){
        Assert.assertTrue(cartPage.isCartPage());
    }
}
