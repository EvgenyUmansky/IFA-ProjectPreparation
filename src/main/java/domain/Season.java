package domain;

import service.Controller;

import java.util.*;


//TODO : fix new class Diagram!
public class Season {
    private Controller controller;
    private Set<LeaguePerSeason> leaguePerSeasons;

    private int year;

    public Season(Controller controller, Set<LeaguePerSeason> leaguePerSeasons, int year) {
        this.controller = controller;
        this.leaguePerSeasons = leaguePerSeasons;
        this.year = year;
    }

    // Constructor
    public Season() {
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
        this.controller = new Controller();
        this.year = Calendar.getInstance().get(Calendar.YEAR);
    }












    //Getters


    public Controller getController() {
        return controller;
    }

    public Set<LeaguePerSeason> getLeaguePerSeasons() {
        return leaguePerSeasons;
    }

    public int getYear() {
        return year;
    }


}
