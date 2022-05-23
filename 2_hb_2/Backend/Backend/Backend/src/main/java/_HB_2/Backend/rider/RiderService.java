package _HB_2.Backend.rider;

import _HB_2.Backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiderService {

    @Autowired
    RiderRepository riderRepository;

    public void createRider(Rider rider) {

        //set the rider flag
        rider.setARider(true);
        riderRepository.save(rider);
    }

    public User getRiderbyEmail(String email){
        return riderRepository.findByEmail(email);
    }

    public User getRiderById(int id) {
        return riderRepository.findById(id);
    }

}
