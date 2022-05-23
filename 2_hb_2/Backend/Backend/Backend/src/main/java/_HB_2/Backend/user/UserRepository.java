package _HB_2.Backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);


    @Transactional
    void deleteById(int id);
}
