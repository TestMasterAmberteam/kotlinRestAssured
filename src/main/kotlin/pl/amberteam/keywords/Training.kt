package pl.amberteam.keywords

import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import pl.amberteam.data.TrainingModel

class Training {
    lateinit var response: Response

    fun add(trainingModel: TrainingModel): Training {
        response = Given {
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
        return this
    }

    fun checkByQueryParam(trainingId: String): Training {
        response = Given {
            param("id", trainingId)
        } When {
            get("/training")
        } Then {
            statusCode(200)
            log().ifValidationFails()
        } Extract {
            response()
        }
        return this
    }

    fun edit(trainingId: String, trainingModel: TrainingModel): Training {
        response = Given {
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
        return this
    }

    fun delete(trainingId: String): Training {
        Given {
            pathParam("id", trainingId)
        } When {
            delete("training/{id}")
        } Then {
            statusCode(200)
        }
        return this
    }

    fun checkDeletedPathParams(trainingId: String): Training {
        Given {
            pathParam("id", trainingId)
            config(RestAssuredConfig.config().logConfig(LogConfig.logConfig().blacklistHeader("Content-Type")))
        } When {
            get("/training/{id}")
        } Then {
            statusCode(404)
            log().all()
        }

        return this
    }
}