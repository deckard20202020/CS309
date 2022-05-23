package _HB_2.Backend.review.driverReview;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "DriverReviewController", description = "REST APIs related to driverReview")
@RestController
@RequestMapping( "/driverReview")
public class DriverReviewController {

    @Autowired
    DriverReviewService driverReviewService;

    @ApiOperation(value = "Enter a new Diver Review", response = DriverReview.class)
    @PostMapping("/postDriverReview")
    DriverReview createDriverReview(
            @RequestParam int driverId, int riderId,
            @RequestBody DriverReview review) {

        return driverReviewService.addDriverReview(driverId, riderId, review);
    }

    @ApiOperation(value = "Get a particular driver review", response = DriverReview.class)
    @GetMapping("/getDriverReviewByReviewId")
    DriverReview getDriverReview(@RequestParam int reviewId) {
        return driverReviewService.getReview(reviewId);
    }

    @ApiOperation(value = "Delete a driver review", response = String.class)
    @DeleteMapping("/deleteDriverReviewByReviewId")
    String deleteDriverReview(@RequestParam int reviewId) {
        driverReviewService.deleteReview(reviewId);
        return "You have deleted driver review " + reviewId;
    }

    @ApiOperation(value = "Get list of all driver reviews for a driver", response = Iterable.class)
    @GetMapping("getAllDriverReviewsByDriverId")
    List<DriverReview> getAllDriverReviewsByDriverid(@RequestParam int driverId) {
        return driverReviewService.getAllReviewsByDriverid(driverId);
    }
}
