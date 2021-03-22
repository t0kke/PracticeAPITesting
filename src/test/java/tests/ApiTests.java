package tests;

import config.Endpoints;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTests extends BaseTest {
    @DisplayName("Сверяем Json схему сервера с эталонной")
    @Test
    void checkJsonSchemaOfSingleUser() {
        given()
                .when()
                .get(Endpoints.USERS.addPath("/3"))
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
                .post(Endpoints.USERS.getPath())
                .then()
                .statusCode(HTTP_CREATED)
                .body("name", is("George Clooney"))
                .body("id", is(notNullValue()))
                .log().body();

    }
}
