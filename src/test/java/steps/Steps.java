package steps;

import config.Endpoints;
import model.CreateUser;

import static io.restassured.RestAssured.given;

public class Steps {
    public static CreateUser registerNewUser() {
        CreateUser createUser = new CreateUser();
        createUser.setName("Anthony Hopkins");
        createUser.setJob("actor");

        return given()
                .body(createUser)
                .log().uri()
                .log().body()
                .when()
                .post(Endpoints.USERS.getPath())
                .then()
                .log().body()
                .extract()
                .as(CreateUser.class);
    }
}
