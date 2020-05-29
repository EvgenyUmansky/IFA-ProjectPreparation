package service;

import domain.TeamPlayer;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiPlayerController {
    private final domain.controllers.PlayerController controller;

    public ApiPlayerController() {
        controller = new domain.controllers.PlayerController();
    }

    @GetMapping("/players")
    public ArrayList<TeamPlayer> getPlayers(@RequestParam(required = false) boolean available) {
        if (available) {
            return controller.getAvailablePlayers();
        }
        return controller.getPlayers();
    }
}
