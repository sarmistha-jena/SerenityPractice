package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class CartPage extends PageObject {
    @FindBy (xpath = "//button[text()='Place Order']")
    WebElementFacade placeOrderButton;

    public boolean isCartPage(){
        return placeOrderButton.isPresent();
    }
}
