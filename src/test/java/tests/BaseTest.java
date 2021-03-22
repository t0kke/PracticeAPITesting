package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }
}
