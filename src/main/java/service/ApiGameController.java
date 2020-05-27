package service;

import domain.*;
import org.springframework.web.bind.annotation.*;
import service.pojos.GameDTO;

import java.util.ArrayList;

@RestController
public class ApiGameController {

    private final domain.controllers.GameController controller;

    public ApiGameController(){
        controller = new domain.controllers.GameController();
    }

    @GetMapping("/games")
    // This will get games by referee user in the following way: /games?referee=<username>
    public ArrayList<GameDTO> getRefereeGames(@RequestParam("referee") String userName){
        return controller.getRefereeGames(userName);
    }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable String gameId){
        return controller.getGame(gameId);
    }

    @PostMapping("/games/{gameId}")
    public void addFanSubscriptionToGame(@PathVariable String gameId, @RequestParam String gameMinute, @RequestParam String eventName, @RequestParam String description) throws Exception {
        controller.addGameEventToGame(gameId, gameMinute, eventName, description);
    }

    // This will update event by referee user in the following way: /games/{gameId}?eventId=<eventId>
    @PutMapping("/games/{gameId}")
    public void changeGameEvent(@PathVariable String gameId, @RequestParam("eventId") String eventId, @RequestParam String dateTimeStr, @RequestParam String gameMinutes, @RequestParam String eventName, @RequestParam String description) throws Exception {
        controller.changeGameEvent(gameId, eventId,  dateTimeStr, gameMinutes, eventName, description);
    }


}
