package domain;

import java.util.HashSet;
import java.util.Set;

public class LeaguePerSeason {

    private League league;
    private Season season;
    private SchedulingMethod schedulingMethod;
    private RankingMethod rankingMethod;
    private Set<Team> teamsInLeaguePerSeason;
    private Set<Game> gamesInLeaguePerSeason;

    public LeaguePerSeason(League league, Season season, SchedulingMethod schedulingMethod, RankingMethod rankingMethod, Set<Team> teamsInLeaguePerSeason, Set<Game> gamesInLeaguePerSeason) {
        this.league = league;
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsInLeaguePerSeason = teamsInLeaguePerSeason;
        this.gamesInLeaguePerSeason = gamesInLeaguePerSeason;
    }

    public LeaguePerSeason() {
        this.league = new League("");
        this.season = new Season();
        this.rankingMethod = new RankingMethod();
        this.schedulingMethod = new SchedulingMethod();
        this.teamsInLeaguePerSeason = new HashSet<Team>();
        this.gamesInLeaguePerSeason = new HashSet<Game>();
    }







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

    public Set<Team> getTeamsInLeaguePerSeason() {
        return teamsInLeaguePerSeason;
    }

    public Set<Game> getGamesInLeaguePerSeason() {
        return gamesInLeaguePerSeason;
    }
}
