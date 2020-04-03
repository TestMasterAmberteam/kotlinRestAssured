package pl.amberteam;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class TrainingJavaTest {
    private final static String API_VERSION = "1";

    @BeforeAll
    static void setup() {
        baseURI = "http://localhost";
        basePath = "/api/rest/v" + API_VERSION + "/";
        port = 9999;
    }

    @Test
    void getAllTrainings() {
        given()
                .contentType(ContentType.JSON)

                .when()
                .get("/trainings/all")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    @DisplayName("Test in tha jafa")
    void makeNewTraining() {
        TrainingJavaModel newTraining = new TrainingJavaModel().setName("test java").setTrainer("Grzesiek")
                .setMaxParticipants(10).setPrice(0).setPlace("online");

        Response newTrainingResponse = given()
                .contentType(ContentType.JSON)
                .body(newTraining)
                .when()
                .post("/training")
                .then()
                .log().body()
                .statusCode(201)
                .extract()
                .response();

        String newTrainingId = newTrainingResponse.jsonPath().getString("id");

        given()
                .param("id", newTrainingId)
                .log().uri()
                .when()
                .get("training")
                .then()
                .statusCode(200);
    }
}
