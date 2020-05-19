package service;

import domain.League;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

public class ApiLeagueController {
    private final domain.controllers.LeagueController controller;

    public ApiLeagueController(){
        controller = new domain.controllers.LeagueController();
    }

    @GetMapping("/leagues")
    public ArrayList<League> getLeagueDetails(){
        return controller.getLeagues();
    }

    // This will get league by league name in the following way: /leagues?leagueName=<leagueName>
    @GetMapping("/leagues")
    public League getLeagueDetails(@RequestParam("leagueName") String leagueName){
        return controller.getLeagueDetails(leagueName);
    }

    // This will get leagues by season id in the following way: /seasons?seasonId=<seasonId>
    @GetMapping("/seasons")
    public ArrayList<League> getSeasonDetails(@RequestParam("seasonId") String seasonId){
        return controller.getSeasonDetails(seasonId);
    }

    @PostMapping("/leagues/{leaguesName}")
    public League updatePageInfo(@PathVariable String leaguesName){
        return controller.setLeague(leaguesName);
    }

    @PutMapping("/leagues/{leaguesName}")
    public League updateSeasonForLeague(@PathVariable String leaguesName, @RequestParam String season){
        return controller.updateSeasonForLeague(leaguesName, season);
    }

    @PutMapping("/leagues/{leaguesName}")
    public void addRefereeToLeaguePerSeason(@PathVariable String leaguesName, @RequestParam String userName){
        controller.addRefereeToLeaguePerSeason(leaguesName, userName);
    }

    @PutMapping("/leagues/{leaguesName}")
    public void setRankingMethod(@PathVariable String leaguesName, @RequestParam String winP, @RequestParam String drawP, @RequestParam String loseP){
        controller.setRankingMethod(leaguesName, winP, drawP, loseP);
    }

    @PutMapping("/leagues/{leaguesName}")
    public void setSchedulingMethod(@PathVariable String leaguesName, @RequestParam String schedulingMethodName){
        controller.setSchedulingMethod(leaguesName, schedulingMethodName);
    }
}
