package tests;

import config.Endpoints;
import model.CreateUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.Steps;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests extends BaseTest {
    private CreateUser createUser;

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

    @Test
    @DisplayName("Создаем нового пользователя")
    void createUser() {
        String name = "Anthony Hopkins";
        String job = "Actor";

        step("Регистрация нового пользователя", () -> {
            step("Регистрация нового пользователя", () -> {
                createUser = Steps.registerNewUser(name, job);
            });
            step("Проверяем, что с сервера приходит верное имя ", () -> {
                assertThat(createUser.getName(), is(equalTo(name)));
            });
            step("Проверяем, что поле id не путое", () -> {
                assertThat(createUser.getJob(), is(notNullValue()));
            });
        });
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
