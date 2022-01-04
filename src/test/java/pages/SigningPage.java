package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SigningPage extends PageObject {
    @FindBy(xpath = "//label[contains(@for,'disclosureAccepted')]")
    WebElement disclosureCheckBox;

    @FindBy(xpath = "//button[@id='action-bar-btn-continue']")
    WebElement continueButton;
    @FindBy(id = "//button[@id='action-bar-btn-finish']")
    WebElement finishButton;

    @FindBy(xpath = "//button[@class='tab-button' and contains(@id,'tab-form-element')]")
    WebElement signButton;

    @FindBy(xpath = "//button[contains(@data-qa,'adopt-submit')and contains(@data-group-item,'signature')]")
    WebElement signAndAdoptButton;


    public void checkDisclosureBox() {
        waitABit(5000);
        disclosureCheckBox.click();
        waitABit(2000);
    }

    public void clickContinueButton() {

        continueButton.click();
        waitABit(3000);
    }

    public void clickFinishButton() {
        finishButton.click();
        waitABit(3000);
    }

    public void clickSignButton() {
        signButton.click();
        waitABit(3000);
    }

    public void clickSignAndAdoptButton() {
        signAndAdoptButton.click();
        waitABit(3000);
    }

}
