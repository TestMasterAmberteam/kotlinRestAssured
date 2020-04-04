package pl.amberteam


import io.restassured.RestAssured.*
import io.restassured.http.ContentType


import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When


import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pl.amberteam.data.TrainingModel
import pl.amberteam.keywords.Training
import kotlin.test.assertEquals


class TrainingKotlinTest {
    companion object {
        private const val apiVersion = "1"
        private val training: Training = Training()

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
        Given {
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
            TrainingModel(
                name = "test",
                trainer = "Grzesiek",
                maxParticipants = 10,
                price = 0,
                place = "web"
            )

        val newTrainingId = training.add(newTraining).jsonPath().getString("id")
        val nameOnResponse = training.checkByQueryParam(newTrainingId).jsonPath().getString("name")
        assertEquals(newTraining.name, nameOnResponse)
    }


    @Test
    @DisplayName("keczupowa edycja")
    fun editTraining() {
        val newTraining =
            TrainingModel(
                name = "przed edycją",
                trainer = "Grzesiek",
                maxParticipants = 10,
                price = 0,
                place = "web"
            )
        val editedTraining = newTraining.copy(name = "po edycji")
        val trainingId: String = training.add(newTraining).jsonPath().getString("id")
        training.checkByQueryParam(trainingId)
        training.edit(trainingId, editedTraining)
        training.checkByQueryParam(trainingId)
        val nameOnResponse = training.checkByQueryParam(trainingId).jsonPath().getString("name")
        assertEquals(editedTraining.name, nameOnResponse)

    }
}