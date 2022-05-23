package com.isu.cs309;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
class FirstController {

    User user1 = new User(
            "Matt",
            "Sinnwell",
            "615 10th St. Ames, IA 50010",
            "mattds@iastate.edu",
            "505-400-5981");

    User user2 = new User(
            "John",
            "Doe",
            "615 10th St. Ames, IA 50010",
            "jdoe@iastate.edu",
            "123-456-7890");

    List<String> names = new ArrayList<>();

    @GetMapping("/")
    public String welcome() {
        return "The TA should give Matt an A for this demo, it's awesome";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "The person who made this endpoint is: " + name;
    }

    @GetMapping("/object")
    public User returnObject() {
        return user1;
    }

    @GetMapping("/objects")
    public List<User> returnObjects() {
        ArrayList<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        return list;
    }

    @GetMapping("/parameter")
    public User getParameter(@RequestParam Long id){
        user1.setId(1L);
        user2.setId(2L);
        HashMap<Long,User > mapOfUsers = new HashMap<>();
        mapOfUsers.put(1L, user1);
        mapOfUsers.put(2L, user2);
        return mapOfUsers.get(id);
    }

    @PutMapping("/post")
    List<String> addAName(@RequestBody String name) {

        names.add(name);
        for (String n : names) {
            System.out.println(n);
        }

        return names;
    }

    //inserts a new object
    @PostMapping("/postObject")
    public @ResponseBody String addAnObject(@RequestBody User u) {

        HashMap<Long, User> mapOfUsers = new HashMap<>();
        System.out.println(u);
        User u1 = new User(u.getFirstName(), u.getLastName(), u.getAddress(), u.getEmail(), u.getPhoneNumber());
        mapOfUsers.put(u1.getId(), u1);

        return "You have just created a user for " + u.getFirstName();
    }

    @DeleteMapping("/delete/{id}")
    HashMap<Long, User> deleteSomething(@PathVariable Long id) {
        user1.setId(1L);
        user2.setId(2L);
        HashMap<Long, User> mapOfUsers = new HashMap<>();
        mapOfUsers.put(1L, user1);
        mapOfUsers.put(2L, user2);
        mapOfUsers.remove(id);
        System.out.println(mapOfUsers.get(2L));
        return mapOfUsers;

    }

    //updates an object
    @PutMapping("/put/{id}")
    public @ResponseBody User putInfo(@PathVariable Long id, @RequestBody User userInput) {
        HashMap<Long, User> mapOfUsers = new HashMap<>();
        user1.setId(1L);
        user2.setId(2L);
        mapOfUsers.put(1L, user1);
        mapOfUsers.put(2L, user2);
        User user = mapOfUsers.get(id);
        user.setFirstName(userInput.getFirstName());
        return user;
    }

    //updates an object
    @PutMapping("/secondput/{id}")
    public @ResponseBody User putInfo(@PathVariable Long id, @RequestBody String name) {
        HashMap<Long, User> mapOfUsers = new HashMap<>();
        user1.setId(1L);
        user2.setId(2L);
        mapOfUsers.put(1L, user1);
        mapOfUsers.put(2L, user2);
        User user = mapOfUsers.get(id);
        user.setFirstName(name);
        return user;
    }
}
