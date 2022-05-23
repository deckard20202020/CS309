package _HB_2.Backend.review.riderReview;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "RiderReviewController", description = "REST APIs related to riderReview")
@RestController
@RequestMapping( "/riderReview")
public class RiderReviewController {

    @Autowired
    RiderReviewService riderReviewService;

    @ApiOperation(value = "Enter a new Rider Review", response = RiderReview.class)
    @PostMapping("/postRiderReview")
    RiderReview createRiderReview(
            @RequestParam int driverId, int riderId,
            @RequestBody RiderReview review) {

        return riderReviewService.addRiderReview(driverId, riderId, review);
    }

    @ApiOperation(value = "Get a particular rider review", response = RiderReview.class)
    @GetMapping("/getRiderReviewByReviewId")
    RiderReview getRiderReview(@RequestParam int reviewId) {
        return riderReviewService.getReview(reviewId);
    }

    @ApiOperation(value = "Delete a rider review", response = String.class)
    @DeleteMapping("/deleteRiderReviewByReviewId")
    String deleteRiderReview(@RequestParam int reviewId) {
        riderReviewService.deleteReview(reviewId);
        return "You have deleted rider review " + reviewId;
    }

    @ApiOperation(value = "Get list of all rider reviews for a driver", response = Iterable.class)
    @GetMapping("getAllRiderReviewsByRiderId")
    List<RiderReview> getAllRiderReviewsByRiderid(@RequestParam int riderId) {
        return riderReviewService.getAllReviewsByRiderid(riderId);
    }
}

