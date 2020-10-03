package br.com.beveragesuggester.boundary;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

/**
 * @author fmoraes
 */
@RunWith(Arquillian.class)
public class BeverageServiceIT {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, BeverageServiceIT.class.getSimpleName() + ".war")
                .addPackages(true, "br/com/beveragesuggester")
                .addAsResource("META-INF/beans.xml")
                .addAsResource("META-INF/microprofile-config.properties");

        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @ArquillianResource
    private URL baseURL;

    WireMockServer mockServer = new WireMockServer(
            WireMockConfiguration.options().port(9999).extensions(new ResponseTemplateTransformer(false)));

    @Before
    public void setup() {
        mockServer.start();
    }

    @After
    public void teardown() {
        mockServer.stop();
    }

    @Test
    public void shouldReceiveCorrectForecast() {
        RestAssured
                .given().log().all()
                .baseUri(baseURL.toString())
                .basePath("resources")
                .queryParam("useTemperature", "true")
                .when()
                .get("beverages")
                .then().log().all()
                .assertThat()
                .statusCode(200);

        mockServer.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo("/data/2.5/weather"))
                .withQueryParam("q", WireMock.equalTo("Sao Paulo, BR"))
                .withQueryParam("units", WireMock.equalTo("metric"))
                .withQueryParam("appid", WireMock.equalTo("testkey")));
    }

    @Test
    public void shouldFailWithInvalidKey() {
        RestAssured
                .given().log().all()
                .baseUri("http://localhost:" + mockServer.port())
                .queryParam("q", "Sao Paulo,br")
                .queryParam("units", "metric")
                .when()
                .get("/data/2.5/weather")
                .then().log().all()
                .assertThat()
                .statusCode(401);
    }

}
