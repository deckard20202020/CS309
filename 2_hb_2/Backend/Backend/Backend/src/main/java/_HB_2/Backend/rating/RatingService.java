package _HB_2.Backend.rating;

import _HB_2.Backend.user.User;
import _HB_2.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RatingRepository ratingRepository;

    public Rating createRating(int raterId, int ratedId, Rating rating) {

        User rater = userRepository.findById(raterId);
        User rated = userRepository.findById(ratedId);

        rating.setRater(rater);
        rating.setRated(rated);

        ratingRepository.save(rating);
        return rating;
    }

    //might be able to make a sql query to make this faster
    public Float getUserRating(int userId) {

        List<Rating> allRatings = ratingRepository.findAll();
        List<Rating> userRatings = new ArrayList<>();

        for (Rating rating : allRatings) {
            if (rating.getRated() != null && rating.getRater() != null) {
                if (rating.getRated().getId() == userId) {
                    userRatings.add(rating);
                }
            }
        }

        float sum = 0;
        float averageRating = 0;
        float count = 0;

        for (Rating rating : userRatings) {
            sum = sum + rating.getRating();
            count++;
        }

        averageRating = sum / (count);
        
        //round
        DecimalFormat df = new DecimalFormat("#.0");
        averageRating = Float.valueOf(df.format(averageRating));

        return averageRating;
    }

    public void deleteRating(int ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
