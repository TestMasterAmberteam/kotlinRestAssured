package pl.amberteam.keywords

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import pl.amberteam.data.TrainingModel

class Training {
    fun add(trainingModel: TrainingModel): Response {
        return Given {
            contentType(ContentType.JSON)
            body(trainingModel)
        } When {
            post("/training")
        } Then {
            log().body()
            statusCode(201)
        } Extract {
            response()
        }

    }

    fun checkByQueryParam(trainingId: String): Response {
        return Given {
            param("id", trainingId)
        } When {
            get("/training")
        } Then {
            statusCode(200)
            log().ifValidationFails()
        } Extract {
            response()
        }

    }

    fun edit(trainingId: String, trainingModel: TrainingModel): Response {
        return Given {
            pathParam("id", trainingId)
            contentType(ContentType.JSON)
            body(trainingModel)
            log().uri()
        } When {
            put("/training/{id}")
        } Then {
            statusCode(200)
            log().body()
        } Extract {
            response()
        }
    }
}