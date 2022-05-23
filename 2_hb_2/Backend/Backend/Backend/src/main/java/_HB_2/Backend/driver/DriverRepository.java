package _HB_2.Backend.driver;

import _HB_2.Backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DriverRepository extends JpaRepository<User, Integer> {

        @Query("SELECT u FROM User u WHERE u.email = ?1")
        User findByEmail(String email);

        User findById(int id);

        @Transactional
        void deleteById(int id);
}
