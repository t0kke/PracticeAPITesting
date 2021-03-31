package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import steps.Steps;

import static io.restassured.RestAssured.given;

public class BaseTest {
    static Steps steps;

    @BeforeAll
    public static void init() {
        steps = new Steps();
    }

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.requestSpecification = given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

    }
}
