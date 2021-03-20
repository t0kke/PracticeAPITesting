package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.HTTP_OK;

public class ApiTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @Test
    void checkJsonSchemaOfSingleUser() {
        given()
                .when()
                .get("/users/3")
                .then()
                .statusCode(HTTP_OK)
                .body(matchesJsonSchemaInClasspath("singleUser.json"))
                .log().body();

    }
}
