package domain;

import service.Controller;

import java.util.HashSet;
import java.util.Set;

public class League {
    private Controller controller;
    private Set<LeaguePerSeason> leaguePerSeasons;
    private String leagueName;
    //TODO: referees?

    // Constructor
    public League(Controller controller, String leagueName) {
        this.controller = controller;
        this.leagueName = leagueName;
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();

    }

//    public League(String leagueName) {
//        this.controller = new Controller();
//        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
//        this.leagueName = leagueName;
//    }










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
