package service;


import domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
public class GameController {

    @GetMapping("/games")
    // Need to get games for specific User / filters ..?
    public Game[] getGames(){
        Field field = new Field("Test Field", 1.0);
        return new Game[]{new Game(
                new League("Test league"),
                new Team("test", field, new TeamOwner("Test owner", "test@gmail.com")),
                new Team("test2", field, new TeamOwner("Test owner", "test@gmail.com")),
                field,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                new ArrayList<>()
        )};
    }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable String gameId){

        // Mock data. need to be changes after we Set DB and split controller
        Field field = new Field("Test Field", 1.0);
        return new Game(
                new League("Test league: " + gameId),
                new Team("test", field, new TeamOwner("Test owner", "test@gmail.com")),
                new Team("test2", field, new TeamOwner("Test owner", "test@gmail.com")),
                field,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                new ArrayList<>()
        );
    }
}
