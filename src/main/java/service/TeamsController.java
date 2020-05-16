package service;

import domain.Field;
import domain.Team;
import domain.TeamOwner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TeamsController {

    @GetMapping("/teams")
    // Get all teams by user -> should Add user validation
    public Team[] getTeams(){
        // Mock data until we split controller
        return new Team[]{new Team("test", new Field("Test Field", 1.0), new TeamOwner("Test owner", "test@gmail.com"))};
    }

    @GetMapping("/teams/{teamName}")
    public  Team getTeam(@PathVariable String teamName){
        // Should be bound to getTeamDetails
        return null;
    }

    @PostMapping("/teams")
    // Create new team to specific user ...? -> should Add user validation
    public void createTeam(){

    }


}
