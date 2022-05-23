package _HB_2.Backend.review.driverReview;

import _HB_2.Backend.driver.DriverRepository;
import _HB_2.Backend.rider.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverReviewService {

    @Autowired
    DriverReviewRepository driverReviewRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    RiderRepository riderRepository;

    public DriverReview addDriverReview(int driverId, int riderId, DriverReview review) {

        review.setReviewDriver(driverRepository.findById(driverId));
        review.setReviewer(riderId);
        driverReviewRepository.save(review);

        return review;
    }

    public DriverReview getReview(int reviewId) {
        return driverReviewRepository.findById(reviewId);
    }

    public void deleteReview(int reviewId) {
        driverReviewRepository.deleteById(reviewId);
    }

    //We can probably speed this up by writing a sql query to make it run faster
    //rather than sorting through them on this end.
    public List<DriverReview> getAllReviewsByDriverid(int driverId) {

        List<DriverReview> driverReviews = new ArrayList<>();
        List<DriverReview> allDriverReviews = driverReviewRepository.findAll();

        for(DriverReview driverReview : allDriverReviews) {
            if (driverReview.getReviewDriver() != null) {
                if ( driverReview.getReviewDriver().getId() == driverId) {
                    driverReviews.add(driverReview);
                }
//            if ( driverReview.getReviewDriver().getId() == driverId) {
//                driverReviews.add(driverReview);
            }
        }

        return driverReviews;
    }
}
