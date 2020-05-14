package service;

import domain.Team;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TeamsController {

    @GetMapping("/teams")
    public Team[] getTeams(){
        // Mock data until we split controller
        return new Team[]{new Team("test", null, null)};
    }
}
