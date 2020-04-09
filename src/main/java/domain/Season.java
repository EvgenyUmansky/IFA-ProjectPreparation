package domain;

import service.Controller;

import java.util.*;


public class Season {
    private Controller controller;
    private Set<LeaguePerSeason> leaguePerSeasons;

    private int year;

    public Season(Controller controller, int year) {
        this.controller = controller;
        this.year = year;
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
    }

    // Constructor
    public Season(Controller controller) {
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
        this.controller = controller;
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
