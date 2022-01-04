package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.SampleAPISteps;


@RunWith(SerenityRunner.class)
public class SampleAPITest {

    @Steps
    SampleAPISteps sampleAPISteps;

    @Test
    @WithTag("smoke")
    public void firstAPITest() {
        sampleAPISteps.getListOfUsers();
    }

    @Test
    @WithTag("smoke")
    public void addUserTest() {
        sampleAPISteps.addUser();
    }

    @Test
    @WithTag("p1")
    public void registerUserTest() {
        sampleAPISteps.registerUser();
    }

    @Test
    public void addUserPojoTest() {
        sampleAPISteps.addUserUsingPOJOClass();
    }
    @Test
    @WithTagValuesOf({"smoke","reggression"})
    public void addUserWithFileTest() {
        sampleAPISteps.addUserUsingFile();
    }

    @Test
    public void firstNameInListTest() {
        sampleAPISteps.verifyFirstNameListOfUsers("1lindsay.ferguson@reqres.in");
    }

    @Test
    public void firstAuthTest() {
        sampleAPISteps.verifyAuthToken1();
    }
    @Test
    public void loggerTest() {
        sampleAPISteps.verifyLoggerInRestAssured();
    }
    @Test
    public void failureEmailTest() {
        sampleAPISteps.verifyOnFailureInRestAssured();
    }
    @Test
    @WithTag("smoke")
    public void queryParamAPITest() {
        sampleAPISteps.getListOfUsersQueryParam();
    }
}
