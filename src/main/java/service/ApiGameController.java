package service;

import domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ApiGameController {

    private final domain.controllers.GameController controller;

    public ApiGameController(){
        controller = new domain.controllers.GameController();
    }

    @GetMapping("/games")
    public ArrayList<Game> getGames(){
        return controller.getGames();
    }

    @GetMapping("/games")
    // This will get games by referee user in the following way: /games?username=<username>
    public ArrayList<Game> getRefereeGames(@RequestParam("username") String userName){

        return controller.getRefereeGames(userName);
//        Field field = new Field("Test Field", 1.0);
//        return new Game[]{new Game(
//                new League("Test league"),
//                new Team("test", field, new TeamOwner("Test owner", "test@gmail.com")),
//                new Team("test2", field, new TeamOwner("Test owner", "test@gmail.com")),
//                field,
//                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
//                new ArrayList<>()
//        )};


    }

    @PostMapping("/games/{gameId}")
    public void addFanSubscriptionToGame(String username, @PathVariable String gameId){
        controller.addFanSubscriptionToGame(gameId, username);
    }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable String gameId){
        return controller.getGame(gameId);
        // Mock data. need to be changes after we Set DB and split controller
//        Field field = new Field("Test Field", 1.0);
//        return new Game(
//                new League("Test league: " + gameId),
//                new Team("test", field, new TeamOwner("Test owner", "test@gmail.com")),
//                new Team("test2", field, new TeamOwner("Test owner", "test@gmail.com")),
//                field,
//                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
//                new ArrayList<>()
//        );
    }

    @PostMapping("/games/{gameId}")
    public void addFanSubscriptionToGame(String username, @PathVariable String gameId, @RequestParam String dateTimeStr, @RequestParam String gameMinutes, @RequestParam String eventName, @RequestParam String description) throws Exception {
        controller.addGameEventToGame(username, gameId, dateTimeStr, gameMinutes, eventName, description);
    }

    // This will update event by referee user in the following way: /games/{gameId}?eventId=<eventId>
    @PutMapping("/games/{gameId}")
    public void changeGameEvent(String username, @PathVariable String gameId, @RequestParam("eventId") String eventId, @RequestParam String dateTimeStr, @RequestParam String gameMinutes, @RequestParam String eventName, @RequestParam String description) throws Exception {
        controller.changeGameEvent(username, gameId, eventId,  dateTimeStr, gameMinutes, eventName, description);
    }


}
