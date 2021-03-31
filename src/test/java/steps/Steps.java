package steps;

import config.Endpoints;
import model.CreateUser;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;

public class Steps {
    public static CreateUser registerNewUser(String name, String job) {
        CreateUser createUser = new CreateUser();
        createUser.setName(name);
        createUser.setJob(job);

        return given()
                .body(createUser)
                .log().uri()
                .log().body()
                .when()
                .post(Endpoints.USERS.getPath())
                .then()
                .statusCode(HTTP_CREATED)
                .log().body()
                .extract()
                .as(CreateUser.class);
    }
}
