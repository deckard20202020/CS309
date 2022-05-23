package _HB_2.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import _HB_2.Backend.driver.Driver;
import _HB_2.Backend.driver.DriverRepository;
import _HB_2.Backend.rating.Rating;
import _HB_2.Backend.review.driverReview.DriverReview;
import _HB_2.Backend.review.riderReview.RiderReview;
import _HB_2.Backend.rider.Rider;
import _HB_2.Backend.rider.RiderRepository;
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



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RatingAndReviewTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RiderRepository riderRepository;

    private int driverId;
    private int riderId;

    private User user1;

    private User user2;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createUsersAndRatings() {
        //make a driver
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
        user1 = driverRepository.findByEmail("EmailTest");
        driverId = user1.getId();

        //make a rider
        Rider rider = new Rider("FirstName",
                "LastName",
                "Address",
                "City",
                "State",
                "Zip",
                "Email",
                "PhoneNumber",
                "Password");
        ResponseEntity<String> responseEntity2 = this.restTemplate
                .postForEntity("http://localhost:" + port + "/rider/registerRider", rider, String.class);
        assertEquals(200, responseEntity2.getStatusCodeValue());

        //find the id of the user we just created
        user2 = riderRepository.findByEmail("Email");
        riderId = user2.getId();


        //create a rating for both users:
        Rating ratingForDriver = new Rating();
        ratingForDriver.setRater(user2);
        ratingForDriver.setRated(user1);
        ratingForDriver.setRating(3);
        ResponseEntity<String> responseEntity3 = this.restTemplate
                .postForEntity("http://localhost:" + port + "/rating/createRating", ratingForDriver, String.class);

        assertEquals(3, ratingForDriver.getRating());

        Rating ratingForRider = new Rating();
        ratingForRider.setRater(user1);
        ratingForRider.setRated(user2);
        ratingForRider.setRating(5);
        ResponseEntity<String> responseEntity4 = this.restTemplate
                .postForEntity("http://localhost:" + port + "/rating/createRating", ratingForRider, String.class);

        assertEquals(5, ratingForRider.getRating());


        //create a review for both users:
        DriverReview driverReview = new DriverReview();
        driverReview.setReviewDriver(user1);
        driverReview.setReviewer(riderId);
        driverReview.setReview("He is amazing!");


        int driverReviewId = driverReview.getId();
        assertEquals("He is amazing!", driverReview.getReview());
        //delete review
        this.restTemplate
                .put("http://localhost:" + port + "/driverReview/deleteDriverReviewByReviewId?reviewId=" + driverReviewId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        RiderReview riderReview = new RiderReview();
        riderReview.setReviewRider(user2);
        riderReview.setReviewer(driverId);
        riderReview.setReview("She is amazing!");


        int riderReviewId = riderReview.getId();
        assertEquals("She is amazing!", riderReview.getReview());
        //delete review
        this.restTemplate
                .put("http://localhost:" + port + "/riderReview/deleteRiderReviewByReviewId?reviewId=" + riderReviewId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //delete the user we just created
        String string = "/user/deleteUser?id=" + driverId;
        String string2 = "/user/deleteUser?id=" + riderId;
        Response response = RestAssured.given().
                when().
                delete(string);
        Response response2 = RestAssured.given().
                when().
                delete(string2);

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

    }

}

