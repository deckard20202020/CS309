package _HB_2.Backend.reports;

import _HB_2.Backend.rating.Rating;
import _HB_2.Backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Integer>  {

    @Transactional
    void deleteById(int id);

    @Query("SELECT r FROM Reports r WHERE r.reported = ?1")
    List<Reports> getAllReportsOfUser(User reported);
}
