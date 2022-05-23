package coms309.players;


import org.springframework.web.bind.annotation.*;


import java.util.HashMap;

@RestController
public class PlayersController {

    HashMap<Integer, Players> playerList = new  HashMap<>();

    Players mj = new Players("Michael", "Jordan", "Basketball", 1 );
    Players phelps = new Players("Michael", "Phelps", "Swimming", 2 );
    Players tiger = new Players("Tiger", "Woods", "Golf", 3 );

//    playerList.put(mj.getId(), mj);
//    playerList.put(phelps.getId(), phelps);
//    playerList.put(tiger.getId(), tiger);
    @GetMapping("/yaaseen")
    public @ResponseBody String gradeForDemo1() {
        return "All members in 2_HB_2 should get an A for Demo 1 :)";
    }

    @PostMapping("/players")
    public @ResponseBody String createPlayer(@RequestBody Players player) {
        playerList.put(mj.getId(), mj);
        playerList.put(phelps.getId(), phelps);
        playerList.put(tiger.getId(), tiger);

        System.out.println(player);
        playerList.put(player.getId(), player);
        return "New player "+ player.getFirstName() + " Saved";
    }

    @GetMapping("/players")
    public @ResponseBody HashMap<Integer,Players> getAllPlayers() {
        return playerList;
    }

    @GetMapping("/player/{id}")
    public @ResponseBody Players getPlayer(@PathVariable Integer id) {
        Players p = playerList.get(id);
        return p;
    }

    @PutMapping("/player/{id}")
    public @ResponseBody Players updatePlayer(@PathVariable Integer id, @RequestBody Players p) {
        playerList.replace(id, p);
        return playerList.get(id);
    }

    @DeleteMapping("/player/{id}")
    public @ResponseBody HashMap<Integer, Players> deletePlayer(@PathVariable Integer  id) {
        playerList.remove(id);
        return playerList;
    }

    //testing with request parameter!
    @PutMapping("/secondPlayer/{id}")
    public @ResponseBody Players putInfo(@PathVariable Integer id, @RequestParam String fName) {
        Players p = playerList.get(id);
        p.setFirstName(fName);
        return p;
    }

}
