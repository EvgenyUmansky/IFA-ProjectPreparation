package service;

import domain.Team;
import org.springframework.web.bind.annotation.*;
import service.pojos.LoginDTO;
import service.pojos.TeamDTO;
import service.pojos.TeamStatusDTO;
import service.pojos.newTeamDTO;

import java.util.ArrayList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiTeamController {

    private final domain.controllers.TeamController controller;

    public ApiTeamController() {
        controller = new domain.controllers.TeamController();
    }

    @GetMapping("/teams")
    public ArrayList<TeamDTO> getTeams() {
        return controller.getTeams();
    }

    @PutMapping ("/teams")
    public void changeStatus(@RequestBody TeamStatusDTO status) {
        controller.changeStatus(status.getTeamName(), status.getStatus());
    }

    @GetMapping("/teams/{teamName}")
    public Team getTeamDetails(@PathVariable String teamName) {
        return controller.getTeamDetails(teamName);
    }

    @PutMapping("/teams/{teamName}")
    public void updateTeam(@PathVariable String teamName, @RequestBody TeamDTO team, @RequestHeader String username) throws Exception {
        System.out.println(team.toString());
        String status = team.getTeamStatus();
        if (status != null) {
            if (status.equals("close")) {  // TODO: Check if this is the real name
                controller.closeTeam(teamName, username);
            } else {
                controller.openTeam(teamName);
            }
        }

        //String[] players = team.getPlayers();
/*        if (players != null) {
            for (String player : players) {
                controller.addPlayer(teamName, player);
            }
        }*/
        // TODO: Add all fields
    }

    @PostMapping("/teams")
    public void createTeam(@RequestBody newTeamDTO newTeam, @RequestHeader String username) throws Exception {
        // Create new team to specific user ...? -> should Add user validation
        controller.createTeam(username, newTeam.getTeamName(), newTeam.getStadium(), newTeam.getCoach(), newTeam.getPlayers());
    }
}
