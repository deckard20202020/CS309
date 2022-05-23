package _HB_2.Backend.websockets.chat;

import _HB_2.Backend.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{

    @Transactional
    void deleteById(int id);

//    @Query(value = "SELECT m.* from trip t, trip_riders tr where tr.rider_id = ?1 and tr.trip_id = t.id and t.is_completed = false", nativeQuery = true)
//    List<Trip> getAllUncompletedTripsByRiderId(int riderId);

    @Query(value = "Select * from messages where (user_sent_id = user1Id and user_received_id = user2Id) or  (user_sent_id = user2Id and user_received_id = user1Id)", nativeQuery = true)
    List<Message> findMessagesForPairOfUsers(int user1Id, int user2Id);
}
