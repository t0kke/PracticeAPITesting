package tests;

import config.Endpoints;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.*;
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

    @DisplayName("Изменяем поле пользователя")
    @Test
    void updateUser() {
        given()
                .body("{ \"job\": \"programmer\" }")
                .when()
                .patch(Endpoints.USERS.addPath("/2"))
                .then()
                .statusCode(HTTP_OK)
                .body("job", is("programmer"))
                .log().body();

    }

    @DisplayName("Успешная регистрация нового пользователя")
    @Test
    void successfulUserRegistration() {
        given()
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }")
                .when()
                .post(Endpoints.REGISTER.getPath())
                .then()
                .statusCode(HTTP_OK)
                .body("id", is(4))
                .body("token", is(notNullValue()))
                .log().body();
    }

    @DisplayName("Неуспешная регистрация нового пользователя.  Отправка только email")
    @Test
    void unsuccessfulUserRegistration() {
        given()
                .body("{ \"email\": \"eve.holt@reqres.in\"}")
                .when()
                .post(Endpoints.REGISTER.getPath())
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("error", is("Missing password"))
                .log().body();
    }

    @DisplayName("Успешный логин пользователем")
    @Test
    void successfulUserLogin() {
        given()
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .when()
                .post(Endpoints.LOGIN.getPath())
                .then()
                .statusCode(HTTP_OK)
                .body("token", is(notNullValue()))
                .log().body();
    }
}
