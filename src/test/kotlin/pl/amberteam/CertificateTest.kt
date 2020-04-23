package pl.amberteam

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pl.amberteam.data.CertificateModel
import kotlin.test.assertEquals

class CertificateTest : BaseTest() {
    @Test
    @DisplayName("Pobierz certyfikaty")
    fun getAllCertificates() {
        Given {
            given()
        } When {
            get("certificates/all")
        } Then {
            statusCode(200)
            log().all()
        }
    }

    @Test
    @DisplayName("dodawanie certyfikatu")
    fun addCertificate() {
        val certyfikat = CertificateModel(
            name = "Quality 3D webinar",
            organization = "SJSI / ATT",
            period = "2h",
            trade = "QA"
        )
        val certificateId: String = Given {
            body(certyfikat)
            contentType(ContentType.JSON)
            header("content-type", "application/json")
        } When {
            post("/certificate")
        } Then {
            statusCode(201)
            log().ifValidationFails()
        } Extract {
            response().jsonPath().getString("id")
        }

        val nameOnResponse: String = Given {
            pathParam("id", certificateId)
        } When {
            get("/certificate/{id}")
        } Then {
            statusCode(200)
            log().body()
        } Extract {
            response().jsonPath().getString("name")
        }

        assertEquals(certyfikat.name, nameOnResponse)

    }

    fun nicNieRobie() {
        println("siema")
    }

}