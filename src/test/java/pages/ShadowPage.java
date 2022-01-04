package pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

@DefaultUrl("https://selectorshub.com/xpath-practice-page/")

public class ShadowPage extends PageObject {
//    @Managed
//    WebDriver driver;

    public void setCoffeeTime(String s){

        waitABit(5000);
        getDriver().switchTo().frame("pact");

        WebElement element = (WebElement) (evaluateJavascript
               ("return document.querySelector('div#snacktime').shadowRoot.querySelector('input#tea')"));
        //WebElement element = (WebElement) evaluateJavascript("return document.querySelector('#jest').shadowRoot=shadowRootClosed.activeElement");
       element.sendKeys(s);
        //System.out.println(s1);
        waitABit(5000);
    }
}
