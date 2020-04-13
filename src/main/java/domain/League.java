package domain;


import service.Controller;

import java.util.HashSet;
import java.util.Set;

public class League {
    private Set<LeaguePerSeason> leaguePerSeasons;
    private String leagueName;
    private int leagueQualification; // From 1 to 5 (5 is the best league....)
    //TODO: referees?

    // Constructor
    public League( String leagueName, int leagueQualification) {
        this.leagueName = leagueName;
        this.leaguePerSeasons = new HashSet<LeaguePerSeason>();
        this.leagueQualification = leagueQualification;

    }



    //Setters
    public boolean setLeaguePerSeasons(Set<LeaguePerSeason> leaguePerSeasons) {
        if(leaguePerSeasons != null){
            this.leaguePerSeasons = leaguePerSeasons;
            return true;
        }
        return false;
    }

    //add league per season
    public boolean addLeaguePerSeason(LeaguePerSeason leaguePerSeason) {
        if(leaguePerSeason != null){
            this.leaguePerSeasons.add(leaguePerSeason);
            return true;
        }
        return false;
    }

    public boolean setLeagueQualification(int leagueQualification) {
        if(leagueQualification >= 1 && leagueQualification <=5){
            this.leagueQualification = leagueQualification;
            return true;
        }
        return false;
    }

    //Getters

    public Set<LeaguePerSeason> getLeagueSeasons() {
        return leaguePerSeasons;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public int getLeagueQualification() {
        return leagueQualification;
    }
}
