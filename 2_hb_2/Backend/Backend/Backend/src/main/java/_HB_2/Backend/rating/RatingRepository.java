package _HB_2.Backend.rating;

import _HB_2.Backend.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Trip findById(int id);

    @Transactional
    void deleteById(int id);
}
