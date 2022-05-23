package _HB_2.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import _HB_2.Backend.driver.Driver;
import _HB_2.Backend.driver.DriverRepository;
import _HB_2.Backend.trip.Trip;
import _HB_2.Backend.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TripTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DriverRepository driverRepository;

    private int driverId;

    private User user;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createUserAndTrip() {
        //send request and receive response
        Driver driver = new Driver("FirstNameTest",
                "LastNameTest",
                "AddressTest",
                "CityTest",
                "StateTest",
                "ZipTest",
                "EmailTest",
                "PhoneNumberTest",
                "PasswordTest");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/driver/registerDriver", driver, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //find the id of the user we just created
        user = driverRepository.findByEmail("EmailTest");
        driverId = user.getId();

        //create a trip
        Trip trip = new Trip();
        trip.setScheduledStartDate(LocalDateTime.parse("2022-04-05T17:09:42"));
        trip.setScheduledEndDate(LocalDateTime.parse("2022-04-05T17:10:23"));
        trip.setOriginAddress("615 10th St Ames IA, 50010");
        trip.setDestAddress("DS Airport, Des Moines, IA 50131");
        trip.setRadius(10);
        trip.setRatePerMin(3.5);

        ResponseEntity<String> responseEntity2 = this.restTemplate
                .postForEntity("http://localhost:" + port + "/trip/createTripByDriver", trip, String.class, driverId, trip);

        int tripId = trip.getId();

        //check the different values of the trip:
        assertEquals(LocalDateTime.parse("2022-04-05T17:09:42"), trip.getScheduledStartDate());
        assertEquals(LocalDateTime.parse("2022-04-05T17:10:23"), trip.getScheduledEndDate());
        assertEquals("615 10th St Ames IA, 50010", trip.getOriginAddress());
        assertEquals("DS Airport, Des Moines, IA 50131", trip.getDestAddress());
        assertEquals(10, trip.getRadius());


        //delete the trip we just created
        this.restTemplate
                .put("http://localhost:" + port + "/trip/deleteTripById?id=" + tripId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //delete the user we just created
        String string = "/user/deleteUser?id=" + driverId;
        Response response = RestAssured.given().
                when().
                delete(string);

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

    }

}

