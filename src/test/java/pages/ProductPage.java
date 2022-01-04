package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPage extends PageObject {
    private static Logger logger = LoggerFactory.getLogger(ProductPage.class);

    public void addProductToCart(){
        $("//a[text()='Add to cart']").waitUntilVisible().click();
    }
    public void confirmProductAddition(){
        if (waitFor(ExpectedConditions.alertIsPresent())==null)
            logger.warn("Alert was not present");
        else getAlert().accept();
    }
    public void goToCart(){
        $("#cartur").click();
    }
}
