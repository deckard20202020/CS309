package _HB_2.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RiderStopControllerTest {

    @LocalServerPort
        int port;

        @Before
        public void setUp() {
            RestAssured.port = port;
            RestAssured.baseURI = "http://localhost";
        }

        @Test
        public void getRiderStopsByTripId() {
            //send request and receive response
            Response response = RestAssured.given().
                    when().
                    get("/riderStop/getRiderStopsByTripId?tripId=24");

            // Check status code
            int statusCode = response.getStatusCode();
            assertEquals(200, statusCode);

        }

}
