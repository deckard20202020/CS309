package _HB_2.Backend.driver;

import _HB_2.Backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/registerDriver")
    User createDriverWithBody(
            @RequestBody Driver d) {
        User u = driverService.getDriverbyEmail(d.getEmail());

        //if there is no driver in the database
        if (u == null){
            //make a new one
            driverService.createDriver(d);
            return d;
        }
        //otherwise a driver with this email already exists
        else{
            //so just return an empty user
            User empty = new Driver();
            return empty;
        }
    }

    @GetMapping("/getDriver")
    User getDriverById(
            @RequestParam int id) {

//        if (id == null) {
//            return "no id sent";
//        }
//        try{
//            User u = driverService.getDriverById(id);
//            return u;
//        } catch (Exception e) {
//            //what should i return here?
//            //return "there was a problem";
//        }
        User u = driverService.getDriverById(id);
        return u;
    }


}