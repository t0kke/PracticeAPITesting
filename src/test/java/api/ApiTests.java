package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @DisplayName("Сверяем Json схему сервера с эталонной")
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

    @DisplayName("Создаем нового пользователя")
    @Test
    void createNewUser() {
        given()
                .body("{ \"name\": \"George Clooney\", \"job\": \"actor\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(HTTP_CREATED)
                .body("name", is("George Clooney"))
                .body("id", is(notNullValue()))
                .log().body();

    }
}
