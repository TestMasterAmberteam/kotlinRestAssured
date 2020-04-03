package pl.amberteam



import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test



class TrainingKotlinTest {
    companion object {
        private const val apiVersion = "1"

        @BeforeAll
        @JvmStatic
        fun setup() {
            baseURI = "http://localhost"
            basePath = "/api/rest/v${apiVersion}/"
            port = 9999

        }
    }


//    private val apiVersion = "1"
//
//    @BeforeEach
//    fun setup() {
//        baseURI = "http://localhost"
//        basePath = "/api/rest/v${apiVersion}/"
//        port = 9999
//    }

    @Test
    fun getAllTrainings() {
        Given{
           contentType(ContentType.JSON)
        } When {
            get("/trainings/all")
        } Then {
            statusCode(200)
            log().all()
        }
    }

    @Test
    @DisplayName("Keczupowo i smakowo")
    fun makeNewTraining() {
        val newTraining =
            TrainingModel(name = "test", trainer = "Grzesiek", maxParticipants = 10, price = 0, place = "web")

        val newTrainingResponse = Given {
            contentType(ContentType.JSON)
            body(newTraining)
        } When {
            post("/training")
        } Then{
            log().body()
            statusCode(201)
        } Extract {
            response()
        }

        val newTrainingId = newTrainingResponse.jsonPath().getString("id")
        Given {
            param("id", newTrainingId)
            log().uri()
        } When {
            get("/training")
        } Then {
            statusCode(200)
        }
    }

}