package _HB_2.Backend.review.riderReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RiderReviewRepository extends JpaRepository<RiderReview, Integer> {

    RiderReview findById(int id);

    @Transactional
    void deleteById(int id);
}
