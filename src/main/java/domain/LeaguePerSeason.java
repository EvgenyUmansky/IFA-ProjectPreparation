package domain;

import java.util.*;

public class LeaguePerSeason {

    private int season;
    private SchedulingMethod schedulingMethod;
    private RankingMethod rankingMethod;
    /**
     * saves each team in a season and its points
     */
    private HashMap<Team, Integer> teamsInLeaguePerSeasonTable;
    private Set<Game> gamesInLeaguePerSeason;

    /**
     *
     * @param season
     * @param schedulingMethod
     * @param rankingMethod
     */
    public LeaguePerSeason(int season, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsInLeaguePerSeasonTable = new LinkedHashMap<>();
        this.gamesInLeaguePerSeason = new HashSet<Game>();
    }

    /**
     * initialized the table of season so every team have 0 points
     * @param teamsInLeaguePerSeason
     */
    public void initializedTeamsInLeaguePerSeason(Set<Team> teamsInLeaguePerSeason) {
        this.teamsInLeaguePerSeasonTable.keySet().addAll( teamsInLeaguePerSeason);
        for(Team team : this.teamsInLeaguePerSeasonTable.keySet()){
            teamsInLeaguePerSeasonTable.put(team, 0);
        }
    }


    /**
     * With the teams in the constructor
     * @param season
     * @param teamsInLeaguePerSeason
     * @param schedulingMethod
     * @param rankingMethod
     */
    public LeaguePerSeason(int season, HashMap<Team, Integer> teamsInLeaguePerSeason, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsInLeaguePerSeasonTable = teamsInLeaguePerSeason;
        this.gamesInLeaguePerSeason = new HashSet<Game>();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return the list of games according to the scheduling method choosen
     */
    public List<Game> scheduledGames(){
        int i = 0;
        Team [] teamArray = new Team[this.teamsInLeaguePerSeasonTable.size()];
        for(Team team : this.teamsInLeaguePerSeasonTable.keySet()){
            teamArray[i] = team;
        }
        return this.schedulingMethod.scheduleGamePolicy(this, teamArray);
    }


    //TODO: 2 - ranking method rely on team
    public  HashMap<Team, Integer> tableOfTheLeague(){
        HashMap<Team, Integer> tableLeague = new HashMap<>();

        return tableLeague;
    }

    //Setters

    public void setSchedulingMethod(SchedulingMethod schedulingMethod) {
        this.schedulingMethod = schedulingMethod;
    }



    //Getters


    public int getSeason() {
        return season;
    }

    public SchedulingMethod getSchedulingMethod() {
        return schedulingMethod;
    }

    public RankingMethod getRankingMethod() {
        return rankingMethod;
    }

    public HashMap<Team, Integer> getTeamsInLeaguePerSeason() {
        return teamsInLeaguePerSeasonTable;
    }

    public Set<Game> getGamesInLeaguePerSeason() {
        return gamesInLeaguePerSeason;
    }
}
