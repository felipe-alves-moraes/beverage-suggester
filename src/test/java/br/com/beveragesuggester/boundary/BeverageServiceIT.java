package br.com.beveragesuggester.boundary;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

/**
 *
 * @author fmoraes
 */
public class BeverageServiceIT {

    WireMockServer mockServer = new WireMockServer(
            WireMockConfiguration.options().extensions(new ResponseTemplateTransformer(false)));

    @BeforeEach
    public void setup() {
        mockServer.start();
    }

    @AfterEach
    public void teardown() {
        mockServer.stop();
    }

    @Test
    public void shouldReceiveCorrectForecast() {
        RestAssured
        .given().log().all()
            .queryParam("q", "Sao Paulo,br")
            .queryParam("units", "metric")
            .queryParam("appid", "appIdKey")
        .when()
            .get("http://localhost:" + mockServer.port() + "/data/2.5/weather")
        .then().log().all()
            .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldFailWithInvalidKey() {
        RestAssured
        .given().log().all()
            .queryParam("q", "Sao Paulo,br")
            .queryParam("units", "metric")
        .when()
            .get("http://localhost:" + mockServer.port() + "/data/2.5/weather")
        .then().log().all()
            .assertThat()
                .statusCode(401);
    }

}
