package steps;

import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import pages.TablePage;

public class TableStep {
    private TablePage tablePage;

    @Step("Navigate to the home page")
    public void launchApplication(){
        tablePage.open();
    }
    @Step
    public void verifyTableIsPresentOnWebPage(){
        Assert.assertTrue(tablePage.isTableVisible());
    }
    @Step
    public void verifyRowsInTable(){
        tablePage.getSearchResults();
    }
}
