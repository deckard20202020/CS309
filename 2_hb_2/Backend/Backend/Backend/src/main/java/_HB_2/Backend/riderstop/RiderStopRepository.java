package _HB_2.Backend.riderstop;

import _HB_2.Backend.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface RiderStopRepository extends JpaRepository<RiderStop, Integer> {

    RiderStop findById(int id);

    @Transactional
    void deleteById(int id);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rider_stop r where r.trip_id = :tripId and r.rider_id = :riderId", nativeQuery = true)
    void deleteByTripIdAndRiderId(int tripId, int riderId);

    @Query("SELECT r FROM RiderStop r WHERE r.tripId = :tripId")
    List<RiderStop> getRiderStopsByTripId(int tripId);

}
