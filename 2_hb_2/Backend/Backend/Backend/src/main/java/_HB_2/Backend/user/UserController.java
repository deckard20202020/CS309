package _HB_2.Backend.user;

import _HB_2.Backend.driver.Driver;
import _HB_2.Backend.rider.Rider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "UserController", description = "REST APIs related to user")
@RestController
@RequestMapping( "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "Get a user by id", response = User.class)
    @GetMapping("/getUser")
    User getUserById(@RequestParam int id) {
        User u = userService.getUserById(id);
        return u;
    }

    @ApiOperation(value = "Get a user by email", response = User.class)
    @GetMapping("/getUserByEmail")
    User getUserByEmail(@RequestParam String email) {
        User u = userService.getUserByEmail(email);
        return u;
    }

    @ApiOperation(value = "Delete a user by Id", response = String.class)
    @DeleteMapping("/deleteUser")
    String deleteUserById(
            @RequestParam int id) {
        userService.deleteUserById(id);
        return "You have deleted User " + id;
    }

    //Not sure why this doesn't work
    //Either delete this delete the function in the UserService, and create matching enpoints for Rider and Admin
    //Or fix and delete endpoint in DriverController and function in UserService
    @ApiOperation(value = "Edit a user", response = User.class)
    @PutMapping("/editUser")
    User editUser(
            @RequestParam int id,
            @RequestBody User u) {
        return userService.editUser(id, u);
    }

    //SIGN IN CHECKPOINT!!!

    @ApiOperation(value = "Get a user by sign in info", response = User.class)
    @GetMapping("/getUserSignIn")
    User getUserBySignIn(@RequestParam String email, String password) {
        User u = userService.getUserByEmail(email);
        if(u == null){
            User empty = new Driver(); //If driver flag is set, you know that there is no user w that email
            return empty;
        }
        else{
            if(u.getPassword().equals(password)){
                return u;
            }
            else{
                User empty = new Rider(); //If rider flag is set, you know that the password is incorrect for that user
                return empty;
            }
        }
    }

    @ApiOperation(value = "Get a list of all users", response = Iterable.class)
    @GetMapping("/getAllUsers")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Set user's profile picture", response = String.class)
    @PutMapping("/setProfilePicture")
    String setProfilePicture(@RequestParam int userId, String path) {
        return userService.setProfilePicture(userId, path);
    }

    @ApiOperation(value = "Delete a user's profile picture", response = String.class)
    @PutMapping("deleteProfilePicture")
    String deleteProfilePicture(@RequestParam int userId) {
        return userService.deleteProfilePicture(userId);
    }

    @ApiOperation(value = "Ban a user by user id", response = boolean.class)
    @PutMapping("banUserById")
    boolean banUserById(@RequestParam int userId) {
        return userService.banUserById(userId);
    }

    @ApiOperation(value = "Unban a user by user id", response = boolean.class)
    @PutMapping("unBanUserById")
    boolean unBanUserById(@RequestParam int userId) {
        return userService.unBanUserById(userId);
    }

    @ApiOperation(value = "Get the banned status of user by user id", response = boolean.class)
    @GetMapping("getBannedStatusById")
    boolean getBannedStatusById(@RequestParam int userId) {
        return userService.getBannedStatusById(userId);
    }

}