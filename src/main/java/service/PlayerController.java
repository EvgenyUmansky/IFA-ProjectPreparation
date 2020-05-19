package service;
import domain.TeamPlayer;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

public class PlayerController {
    private final domain.controllers.PlayerController controller;

    public PlayerController(){
        controller = new domain.controllers.PlayerController();
    }

    @GetMapping("/users")
    public ArrayList<TeamPlayer> getPlayers(){
        return controller.getPlayers();
    }

    // This will get players by user in the following way: /players?username=<username>
    @GetMapping("/users")
    public TeamPlayer getPlayersDetails(@RequestParam("username") String userName){
        return controller.getPlayersDetails(userName);
    }

    // this will update (PUT request) the players who named <playerName> with the following info in body
    @PutMapping("/users/{userName}")
    public void updatePlayerDetails(@PathVariable String username, @RequestParam String playerName, @RequestParam String birthDate, @RequestParam String position, @RequestParam String squadNumber) throws ParseException {
        controller.updatePlayerDetails(username, playerName, birthDate, position, squadNumber);
    }
}
