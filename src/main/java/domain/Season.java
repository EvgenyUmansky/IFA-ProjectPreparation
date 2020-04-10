package domain;

import service.Controller;

import java.util.*;


public class Season {
    private Set<LeaguePerSeason> leaguePerSeasons;

    private int year;

    public Season(int year) {
        this.year = year;
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
    }


    // Constructor
    public Season() {
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
        this.year = Calendar.getInstance().get(Calendar.YEAR);
    }












    //Getters

    public Set<LeaguePerSeason> getLeaguePerSeasons() {
        return leaguePerSeasons;
    }

    public int getYear() {
        return year;
    }


}
