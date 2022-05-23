package _HB_2.Backend.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    Trip findById(int id);

    @Query("SELECT t FROM Trip t WHERE t.tripDriver.id = ?1 AND t.isCompleted = false")
    List<Trip> getAllUncompletedTripsByDriverId(int driverId);

    @Query(value = "SELECT t.* from trip t, trip_riders tr where tr.rider_id = ?1 and tr.trip_id = t.id and t.is_completed = false", nativeQuery = true)
    List<Trip> getAllUncompletedTripsByRiderId(int riderId);

    @Transactional
    void deleteById(int id);
}
