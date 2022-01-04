package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.TableStep;

@RunWith(SerenityRunner.class)
public class TableTest {

    @Steps
    TableStep tableStep;

    @Test
    public void tableTest(){
        tableStep.launchApplication();
        tableStep.verifyTableIsPresentOnWebPage();
        tableStep.verifyRowsInTable();
    }

}
