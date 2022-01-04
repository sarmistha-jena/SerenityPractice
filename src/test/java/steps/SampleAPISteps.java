package steps;

import io.restassured.RestAssured;
import io.restassured.config.FailureConfig;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.ProductPage;
import pages.UserDetailsDAO;
import tests.EmailUtility;
import utility.RequestBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SampleAPISteps {
    private static final String BASE_URL = "https://reqres.in/api";
    private static final String CONTENT_TYPE = "application/json";
    private static final Logger logger = LoggerFactory.getLogger(ProductPage.class);
    private EnvironmentVariables environmentVariables;

    @Before
    public void configureBaseUrl() {
        String theRestApiBaseUrl = environmentVariables.optionalProperty("restapi.baseurl")
                .orElse(BASE_URL);

    }

    @Step
    public void getListOfUsers() {
        Response res = SerenityRest.given().contentType(CONTENT_TYPE)
                .baseUri(BASE_URL).get("/users?page=2");
        Assert.assertEquals(200, res.getStatusCode());
        String jsonObject = res.body().asString();
        logger.info(jsonObject);
        JsonPath evaluate = res.jsonPath();
        logger.info(String.valueOf((Integer) evaluate.get("total_pages")));
    }

    @Step
    public void addUser() {
        RequestSpecification request = SerenityRest.given();
        JSONObject requestParameter = new JSONObject();
        request.contentType(CONTENT_TYPE);
        requestParameter.put("name", "morpheus");
        requestParameter.put("job", "leader");
        request.body(requestParameter.toString());
        UserDetailsDAO userDetailsResponse = request.baseUri(BASE_URL).post("/users").as(UserDetailsDAO.class);
        System.out.println("ID  " + userDetailsResponse.getId());
        System.out.println("JOB  " + userDetailsResponse.getJob());
        System.out.println("Name  " + userDetailsResponse.getName());
        System.out.println("Created AT  " + userDetailsResponse.getCreatedAt());
        System.out.println("Created AT method " + userDetailsResponse.getCreatedAt());
    }

    @Step
    public void registerUser() {
        RequestSpecification request = SerenityRest.given();
        JSONObject requestParameter = new JSONObject();
        request.contentType(CONTENT_TYPE);
        requestParameter.put("email", "eve.holt@reqres.in");
        requestParameter.put("password", "pistol");
        request.body(requestParameter.toString());
        Response response = request.baseUri(BASE_URL).post("/register");
        System.out.println(response.body().asString());
        System.out.println(response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Step
    public void addUserUsingPOJOClass() {
        RequestSpecification request = SerenityRest.given();
        //JSONObject requestParameter = new JSONObject();
        request.contentType(CONTENT_TYPE);
        UserDetailsDAO userDetailsRequest = new UserDetailsDAO();
        userDetailsRequest.setJob("tester");
        userDetailsRequest.setName("Myra");
        request.body(userDetailsRequest);
        Response response = request.baseUri(BASE_URL).post("/users");
        System.out.println(response.body().asString());
        System.out.println(response.getStatusCode());
        Assert.assertEquals(201, response.getStatusCode());

        UserDetailsDAO userDetailsResponse = response.as(UserDetailsDAO.class);
        System.out.println("ID  " + userDetailsResponse.getId());
        System.out.println("JOB  " + userDetailsResponse.getJob());
        System.out.println("Name  " + userDetailsResponse.getName());
        System.out.println("Created AT  " + userDetailsResponse.getCreatedAt());
        System.out.println("Created AT method " + userDetailsResponse.getCreatedAt());
    }

    @Step
    public void addUserUsingFile() {
        RequestSpecification request = SerenityRest.given();
        request.contentType(CONTENT_TYPE);
        String jsonBody = "";
        try {
            jsonBody = RequestBuilder.generateStringFromResource("src/test/resources/requestJson/userAddRequest.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.body(jsonBody);
        Response response = request.baseUri(BASE_URL).post("/users");
        System.out.println(response.body().asString());
        System.out.println(response.getStatusCode());
        Assert.assertEquals(201, response.getStatusCode());
        JsonPath responsePath = response.jsonPath();
        Assert.assertEquals("Name not correct", "Mark", responsePath.getString("name"));
        Assert.assertEquals("Job not correct", "leader", responsePath.getString("job"));
    }

    @Step
    public void verifyFirstNameListOfUsers(String email) {
        Response res = SerenityRest.given().contentType(CONTENT_TYPE)
                .baseUri(BASE_URL).get("/users?page=2");
        Assert.assertEquals(200, res.getStatusCode());
        String jsonObject = res.body().asString();
        System.out.println(jsonObject);
        JsonPath evaluate = res.jsonPath();
        System.out.println((Integer) evaluate.get("total_pages"));
        List<String> firstNameList = evaluate.getList("data.first_name");
        List<String> emailList = evaluate.getList("data.email");
        for (String d : firstNameList) {
            System.out.println("First name::: " + d);
        }
        Assert.assertTrue("Email id not found in the response body!!", emailList.contains(email));
    }

    @Step
    public void verifyAuthToken1() {
        String emailId = "test" + (int) (Math.random() * 1000) + "@qa.in";
        System.out.println(emailId);
        JSONObject requestParameter = new JSONObject();
        requestParameter.put("name", "morpheus");
        requestParameter.put("gender", "male");
        requestParameter.put("email", emailId);
        requestParameter.put("status", "active");
        Response responsePostCall = SerenityRest.given().log().body().auth().preemptive()
                .oauth2("3e4b6090242750fd70b1644856b22abbe0a8ff7e7acc3f18103567331908258a")
                .contentType(CONTENT_TYPE).body(requestParameter.toString())
                .post("https://gorest.co.in/public/v1/users");
        System.out.println("*****************************\n" + responsePostCall.body().asString());
        System.out.println(responsePostCall.statusCode());
        Assert.assertEquals("Status code is wrong!!", 201, responsePostCall.getStatusCode());
        JsonPath reponsePath = responsePostCall.jsonPath();
        String userIdNumber = reponsePath.getString("data.id");
        System.out.println(userIdNumber);
        System.out.println("*******************************");
        //Response responseDeleteCall=
        SerenityRest.given().log().body().auth().preemptive()
                .oauth2("3e4b6090242750fd70b1644856b22abbe0a8ff7e7acc3f18103567331908258a")
                .contentType(CONTENT_TYPE).delete("https://gorest.co.in/public/v1/users/" + userIdNumber)
                .then().log().all().assertThat().statusCode(204);
        /*System.out.println(responseDeleteCall.body().asString());
        System.out.println(responseDeleteCall.statusCode());
        Assert.assertEquals("Status code is wrong!!",204,responseDeleteCall.getStatusCode());*/
    }

    @Step
    public void verifyLoggerInRestAssured() {
        SerenityRest.given().log().ifValidationFails().contentType(CONTENT_TYPE)
                .baseUri(BASE_URL).get("/users?page=2")
                .then().assertThat().statusCode(200)
                .body("page", Matchers.notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/requestJson/jsonGetUserSchema.json")));
    }

    @Step
    public void verifyOnFailureInRestAssured() {
        EmailUtility emailUtility = new EmailUtility();
        ResponseValidationFailureListener emailOnFailure = (reqSpec, respSpec, resp) ->
                emailUtility.sendEmail("sarmistha_jena@epam.com", "Important test \"GET user API\" failed! Status code was: " + resp.statusCode());

        SerenityRest.given().
                config(RestAssured.config().failureConfig(FailureConfig.failureConfig().with().failureListeners(emailOnFailure))).
                when().
                baseUri(BASE_URL).get("/users?page=2").
                then().
                statusCode(2001);
    }

    @Step
    public void getListOfUsersQueryParam() {
        Response res = SerenityRest.given().contentType(CONTENT_TYPE)
                .queryParam("page",2)
                .baseUri(BASE_URL).get("/users");
        Assert.assertEquals(200, res.getStatusCode());
        String jsonObject = res.body().asString();
        logger.info(jsonObject);
        JsonPath evaluate = res.jsonPath();
        logger.info(String.valueOf((Integer) evaluate.get("total_pages")));
    }
}
