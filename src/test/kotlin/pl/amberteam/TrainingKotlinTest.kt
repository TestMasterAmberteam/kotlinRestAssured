package pl.amberteam


import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When


import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pl.amberteam.data.TrainingModel
import pl.amberteam.keywords.Training
import kotlin.test.assertEquals


class TrainingKotlinTest : BaseTest() {
    private val training: Training = Training()


    @Test
    @DisplayName("Test pobierania szkoleń")
    fun getAllTrainings() {
        Given {
            given()
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
        val trainingId = training.add(newTraining).response.jsonPath().getString("id")
        val nameOnResponse = training.checkByQueryParam(trainingId).response.jsonPath().getString("name")
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
        val trainingId = training.add(newTraining).response.jsonPath().getString("id")
        val nameOnResponse = training
            .checkByQueryParam(trainingId)
            .edit(trainingId, editedTraining)
            .checkByQueryParam(trainingId)
            .response.jsonPath().getString("name")

        assertEquals(editedTraining.name, nameOnResponse)

    }

    @Test
    @DisplayName("usuwamy keczupem")
    fun deleteTraining() {
        val trainingModel = TrainingModel(
            name = "usuwanie",
            trainer = "GH",
            maxParticipants = 100,
            price = 0,
            place = "amberteamtesting online"
        )

        val trainingId = training.add(trainingModel).response.jsonPath().getString("id")

        training
            .checkByQueryParam(trainingId)
            .delete(trainingId)
            .checkDeletedPathParams(trainingId)


    }
}