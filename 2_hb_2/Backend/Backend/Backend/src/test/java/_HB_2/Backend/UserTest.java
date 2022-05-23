package _HB_2.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import _HB_2.Backend.driver.Driver;
import _HB_2.Backend.driver.DriverRepository;
import _HB_2.Backend.user.User;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DriverRepository driverRepository;

    private int driverId;
    private String profilePicture;

    private User user;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createUser() {
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

        //make a rider

        driverId = driverRepository.findByEmail("EmailTest").getId();
        profilePicture = "ProfilePictureTest";
        this.restTemplate
                .put("http://localhost:" + port + "/user/setProfilePicture?userId=" + driverId + "&path=" + profilePicture, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //test to see if we have set the correct profile picture
        User user = driverRepository.findById(driverId);
        assertEquals(profilePicture, user.getProfilePicture());

        //delete profile picture test
        this.restTemplate
                .put("http://localhost:" + port + "/user/deleteProfilePicture?userId=" + driverId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //ban a user
        this.restTemplate
                .put("http://localhost:" + port + "/user/banUserById?userId=" + driverId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //get banned status
        Response response = RestAssured.given().
                when().
                get("/user/getBannedStatusById?userId=" + driverId);
        //        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //unban a user
        this.restTemplate
                .put("http://localhost:" + port + "/user/unBanUserById?userId=" + driverId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //test editing the user
        driver = new Driver("FirstNameTest",
                "LastNameTest",
                "AddressTest",
                "CityTest",
                "StateTest",
                "ZipTest",
                "EmailChangedTest",
                "PhoneNumberTest",
                "PasswordTest");
        this.restTemplate
                .put("http://localhost:" + port + "/user/editUser?id=" + driverId, driver, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //check to see that the edit of our user took effect
        user = driverRepository.findById(driverId);
        assertEquals("EmailChangedTest", user.getEmail());

        //delete the user we just created
        String string = "/user/deleteUser?id=" + driverId;
        response = RestAssured.given().
                when().
                delete(string);

        // Check status code
        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

    }

}

