package steps;

import net.thucydides.core.annotations.Step;
import pages.ProductPage;

public class ProductStep {
    private ProductPage productPage;

    @Step("User should be able to add product to the cart")
    public void addToCart(){
        productPage.addProductToCart();
    }
    @Step
    public void userConfirmProductAddition(){
        productPage.confirmProductAddition();
    }
    @Step
    public void userNavigatesToCartPage(){
        productPage.goToCart();
    }
}
