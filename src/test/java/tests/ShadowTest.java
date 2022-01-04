package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.ShadowStep;

@RunWith(SerenityRunner.class)
public class ShadowTest {
    @Steps
    ShadowStep shadowStep;

    @Test
    @WithTag("smoke")
    public void testShadow(){
        shadowStep.launchApplication();
        shadowStep.addCoffee("Tea time :)");
    }
}
