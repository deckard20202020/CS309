package _HB_2.Backend.review.riderReview;

import _HB_2.Backend.driver.DriverRepository;
import _HB_2.Backend.review.driverReview.DriverReview;
import _HB_2.Backend.rider.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RiderReviewService {

    @Autowired
    RiderReviewRepository riderReviewRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    RiderRepository riderRepository;

    public RiderReview addRiderReview(int driverId, int riderId, RiderReview review) {

        review.setReviewRider(riderRepository.findById(riderId));
        review.setReviewer(driverId);
        riderReviewRepository.save(review);

        return review;
    }

    public RiderReview getReview(int reviewId) {
        return riderReviewRepository.findById(reviewId);
    }

    public void deleteReview(int reviewId) {
        riderReviewRepository.deleteById(reviewId);
    }

    //We can probably speed this up by writing a sql query to make it run faster
    //rather than sorting through them on this end.
    public List<RiderReview> getAllReviewsByRiderid(int riderId) {

        List<RiderReview> riderReviews = new ArrayList<>();
        List<RiderReview> allRiderReviews = riderReviewRepository.findAll();

        for(RiderReview riderReview : allRiderReviews) {
            if (riderReview.getReviewRider() != null) {
                if (riderReview.getReviewRider().getId() == riderId) {
                    riderReviews.add(riderReview);
                }
            }
        }

        return riderReviews;
    }
}

