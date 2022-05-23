package _HB_2.Backend.rating;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "RatingController", description = "REST APIs related to Rating Class")
@RestController
@RequestMapping( "/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @ApiOperation(value = "Submit a rating for a user ", response = Rating.class)
    @PostMapping("/createRating")
    Rating createRating(@RequestParam int raterId,
                        @RequestParam int ratedId,
                        @RequestBody Rating rating) {
        return ratingService.createRating(raterId, ratedId, rating);
    }

    @ApiOperation(value = "Get average rating of a user", response = Float.class)
    @GetMapping("/getUserRating")
    Float getUserRating(@RequestParam int userId) {
        return ratingService.getUserRating(userId);
    }
}
