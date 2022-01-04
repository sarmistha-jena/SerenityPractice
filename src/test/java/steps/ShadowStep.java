package steps;

import net.thucydides.core.annotations.Step;
import pages.ShadowPage;

public class ShadowStep {
    private ShadowPage shadowPage;

    @Step("Navigate to the shadow page")
    public void launchApplication() {
        shadowPage.open();
    }

    @Step
    public void addCoffee(String drink) {
        shadowPage.setCoffeeTime(drink);
    }
}
