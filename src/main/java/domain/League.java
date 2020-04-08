package domain;

import service.Controller;

import java.util.HashSet;
import java.util.Set;

public class League {
    private Controller controller;
    private Set<LeaguePerSeason> leaguePerSeasons;
    private String leagueName;
    //TODO: check for other variables

    public League(Controller controller, Set<LeaguePerSeason> leaguePerSeasons, String leagueName) {
        this.controller = controller;
        this.leaguePerSeasons = leaguePerSeasons;
        this.leagueName = leagueName;

    }

    // Constructor
    public League(String leagueName) {
        this.controller = new Controller();
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
        this.leagueName = leagueName;
    }










    //Getters
    public Controller getController() {
        return controller;
    }

    public Set<LeaguePerSeason> getLeagueSeasons() {
        return leaguePerSeasons;
    }

    public String getLeagueName() {
        return leagueName;
    }
}
