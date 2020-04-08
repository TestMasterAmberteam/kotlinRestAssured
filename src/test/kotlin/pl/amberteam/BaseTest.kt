package pl.amberteam

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll


open class BaseTest {


    companion object {
        protected val apiVersion = "1"

        @BeforeAll
        @JvmStatic
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.basePath = "/api/rest/v${apiVersion}/"
            RestAssured.port = 9999

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


}