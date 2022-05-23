package _HB_2.Backend.admin;

import _HB_2.Backend.user.User;
import _HB_2.Backend.user.UserRepository;
import _HB_2.Backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public Admin createAdmin(Admin admin) {

        //set the Admin flag
        admin.setAnAdmin(true);
        Admin a = adminRepository.save(admin);
        return a;
    }

    public User getAdminById(int id) {
        return adminRepository.findById(id);
    }

    public User getAdminbyEmail(String email){
        return adminRepository.findByEmail(email);
    }

    public User addAsRider(int id){
        User newUser = userRepository.findById(id);
        if(newUser.getARider() == null || newUser.getARider() == false){
            newUser.setARider(true);
        }
        userRepository.save(newUser);

        return userService.getUserById(id);
    }

    public User addAsDriver(int id){
        User newUser = userRepository.findById(id);
        if(newUser.getADriver() == null || newUser.getADriver() == false){
            newUser.setADriver(true);
        }
        userRepository.save(newUser);

        return userService.getUserById(id);
    }

    public User removeAsRider(int id){
        User newUser = userRepository.findById(id);
        newUser.setARider(false);
        if ((newUser.getADriver() == null || newUser.getADriver() == false) & (newUser.getAnAdmin() == null || newUser.getAnAdmin() == false)){
            userRepository.deleteById(id);
        }
        else {
            userRepository.save(newUser);
        }

        return userService.getUserById(id);
    }

    public User removeAsDriver(int id){
        User newUser = userRepository.findById(id);
        newUser.setADriver(false);
        if ((newUser.getARider() == null || newUser.getARider() == false) & (newUser.getAnAdmin() == null || newUser.getAnAdmin() ==false)){
            userRepository.deleteById(id);
        }
        else {
            userRepository.save(newUser);
        }

        return userService.getUserById(id);
    }
}
