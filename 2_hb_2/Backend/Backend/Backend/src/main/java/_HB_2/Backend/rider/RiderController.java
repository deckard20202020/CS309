package _HB_2.Backend.rider;

import _HB_2.Backend.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "riderController", description = "REST APIs related to rider")
@RestController
@RequestMapping( "/rider")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @ApiOperation(value = "Register a rider", response = User.class)
    @PostMapping("/registerRider")
    User createRiderWithBody(@RequestBody Rider r) {
        User u = riderService.getRiderbyEmail(r.getEmail());
        if (u == null){
            riderService.createRider(r);
            return r;
        }
        else{
            User empty = new Rider();
            return empty;
        }
    }

    @ApiOperation(value = "Get a rider by id", response = User.class)
    @GetMapping("/getRider")
    User getRiderById(@RequestParam int id) {
        User u = riderService.getRiderById(id);
        return u;
    }

    @ApiOperation(value = "Get a rider by email", response = User.class)
    @GetMapping("/getRiderByEmail")
    User getRiderByEmail(@RequestParam String email) {
        User u = riderService.getRiderbyEmail(email);
        return u;
    }
    
}