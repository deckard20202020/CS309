package _HB_2.Backend.review.driverReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DriverReviewRepository extends JpaRepository<DriverReview, Integer> {

    DriverReview findById(int id);

    @Transactional
    void deleteById(int id);
}
