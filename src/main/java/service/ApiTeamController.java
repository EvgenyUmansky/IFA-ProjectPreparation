package service;

import domain.Team;
import org.springframework.web.bind.annotation.*;
import service.pojos.TeamDTO;

import java.util.ArrayList;


@RestController
public class ApiTeamController {

    private final domain.controllers.TeamController controller;
    public ApiTeamController(){
        controller = new domain.controllers.TeamController();
    }

    @GetMapping("/teams")
    public ArrayList<Team> getTeams(){
        return controller.getTeams();
    }

    @GetMapping("/teams/{teamName}")
    public Team getTeamDetails(@PathVariable String teamName){
        return controller.getTeamDetails(teamName);
    }

    @PutMapping("/teams")
    public void updateTeam(@RequestBody TeamDTO team) throws Exception {
        controller.updateTeam(team.getTeamName(), team.getStadium(), team.getFields(), team.getPlayers(), team.getCoaches(), team.getManagers(), team.getOwners(), team.getTeamStatus());
    }

//    @PostMapping("/teams/{teamName}")
//    public void addCoach(@PathVariable String teamName, String userName) throws Exception {
//        controller.addCoach(teamName, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void addField(@PathVariable String teamName, String fieldName) throws Exception {
//        controller.addField(teamName, fieldName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void removePlayer(@PathVariable String teamName, String userName) throws Exception {
//        controller.removePlayer(teamName, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void removeCoach(@PathVariable String teamName, String userName) throws Exception {
//        controller.removeCoach(teamName, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void removeField(@PathVariable String teamName, String fieldName) throws Exception {
//        controller.removeField(teamName, fieldName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void addOwner(@PathVariable String teamName, String userNameNewTeamOwner, String userName) throws Exception {
//        controller.addOwner(teamName, userNameNewTeamOwner, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void removeOwner(@PathVariable String teamName, String userNameNewTeamOwner, String userName) throws Exception {
//        controller.removeOwner(teamName, userNameNewTeamOwner, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void addManager(@PathVariable String teamName, String userNameNewTeamOwner, String userName) throws Exception {
//        controller.addOwner(teamName, userNameNewTeamOwner, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void removeManager(@PathVariable String teamName, String userNameNewTeamOwner, String userName) throws Exception {
//        controller.removeOwner(teamName, userNameNewTeamOwner, userName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void closeTeam(@PathVariable String teamName, String userName) {
//        controller.closeTeam(userName, teamName);
//    }
//
//    @PostMapping("/teams/{teamName}")
//    public void openTeam(@PathVariable String teamName) {
//        controller.openTeam(teamName);
//    }
//

    @PostMapping("/teams")
    // Create new team to specific user ...? -> should Add user validation
    public void createTeam(){

    }


}
