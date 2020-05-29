package service;

import org.springframework.web.bind.annotation.*;
import service.pojos.FollowDTO;
import service.pojos.GameDTO;
import service.pojos.GameEventDTO;

import java.util.ArrayList;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiGameController {

    private final domain.controllers.GameController controller;

    public ApiGameController(){
        controller = new domain.controllers.GameController();
    }

    @GetMapping("/games/all_games")
    // This will get games by referee user in the following way: /games?referee=<username>
    public ArrayList<GameDTO> getGames(){
        return controller.getGames();
    }

    @GetMapping("/games")
    // This will get games by referee user in the following way: /games?referee=<username>
    public ArrayList<GameDTO> getRefereeGames(@RequestParam(value = "referee", required = false) String userName){
        if(userName == null){
            return controller.getGames();
        }
        return controller.getRefereeGames(userName);
    }

    @GetMapping("/games/{gameId}")
    public GameDTO getGame(@PathVariable String gameId){
        return controller.getGame(gameId);
    }

    @PostMapping("/games/{gameId}")
    public GameDTO addGameEventToGame(@RequestBody GameEventDTO event) {
        return controller.addGameEventToGame(event.getGameId(), event.getMinutes(), event.getEvent(), event.getDescription());
    }

    @PostMapping("/games/{gameId}/{userName}")
    public void addFanSubscriptionToGame(@RequestBody FollowDTO follow) {
        controller.addFanSubscriptionToGame(follow.getGameId(), follow.getUserName());
    }

    // This will update event by referee user in the following way: /games/{gameId}?eventId=<eventId>
    @PutMapping("/games/{gameId}")
    public void changeGameEvent(@PathVariable String gameId, @RequestParam("eventId") String eventId, @RequestParam String dateTimeStr, @RequestParam String gameMinutes, @RequestParam String eventName, @RequestParam String description) throws Exception {
        controller.changeGameEvent(gameId, eventId,  dateTimeStr, gameMinutes, eventName, description);
    }
}
