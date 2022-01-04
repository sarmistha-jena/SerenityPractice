package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://www.demoblaze.com/")
public class LoginPage extends PageObject {
    @FindBy(css = "#nameofuser")
    WebElementFacade userName;

    public void doLogin(){
        $("#login2").click();
        $("#loginusername").sendKeys("test");
        $("#loginpassword").sendKeys("test");
        $("//button[text()='Log in']").click();
    }
    public String getWelcomeMsg(){
        waitABit(3000);
        //userName.waitUntilVisible();
       // withTimeoutOf(Duration.ofSeconds(15) ).waitForPresenceOf(By.xpath("//a[@id='nameofuser']"));
        return $("#nameofuser").getText();
    }
    public void clickOnProduct(){
        shouldBeVisible(By.xpath("//*[@id='tbodyid']/div"));
        $("//*[@id='tbodyid']/div//a").click();
    }

}
