package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeaguePerSeason {

    private League league;
    private Season season;
    private SchedulingMethod schedulingMethod;
    private RankingMethod rankingMethod;
    /**
     * saves each team in a season and its points
     */
    private HashMap<Team, Integer> teamsInLeaguePerSeason;
    private Set<Game> gamesInLeaguePerSeason;

    /**
     *
     * @param league
     * @param season
     * @param schedulingMethod
     * @param rankingMethod
     */
    public LeaguePerSeason(League league, Season season, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        this.league = league;
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsInLeaguePerSeason = new HashMap<Team, Integer>();
        this.gamesInLeaguePerSeason = new HashSet<Game>();
    }

    public void setTeamsInLeaguePerSeason(HashMap<Team, Integer> teamsInLeaguePerSeason) {
        this.teamsInLeaguePerSeason = teamsInLeaguePerSeason;
    }


    /**
     * With the teams in the constructor
     * @param league
     * @param season
     * @param teamsInLeaguePerSeason
     * @param schedulingMethod
     * @param rankingMethod
     */
    public LeaguePerSeason(League league, Season season, HashMap<Team, Integer> teamsInLeaguePerSeason, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        this.league = league;
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsInLeaguePerSeason = teamsInLeaguePerSeason;
        this.gamesInLeaguePerSeason = new HashSet<Game>();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return the list of games according to the scheduling method choosen
     */
    public List<Game> scheduledGames(){
        int i = 0;
        Team [] teamArray = new Team[this.teamsInLeaguePerSeason.size()];
        for(Team team : this.teamsInLeaguePerSeason.keySet()){
            teamArray[i] = team;
        }
        return this.schedulingMethod.scheduleGamePolicy(this, teamArray);
    }


    //TODO: 2 - ranking method
//    public Set<Game> scheduledGames(){
//
//
//        return this.gamesInLeaguePerSeason;
//    }


    //Getters

    public League getLeague() {
        return league;
    }

    public Season getSeason() {
        return season;
    }

    public SchedulingMethod getSchedulingMethod() {
        return schedulingMethod;
    }

    public RankingMethod getRankingMethod() {
        return rankingMethod;
    }

    public HashMap<Team, Integer> getTeamsInLeaguePerSeason() {
        return teamsInLeaguePerSeason;
    }

    public Set<Game> getGamesInLeaguePerSeason() {
        return gamesInLeaguePerSeason;
    }
}
