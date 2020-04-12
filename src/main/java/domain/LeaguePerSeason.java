package domain;

import java.util.*;

public class LeaguePerSeason {
    private boolean isBegin;
    private int season;
    private SchedulingMethod schedulingMethod;
    private RankingMethod rankingMethod;
    private HashSet<Referee> referees;
    /**
     * saves each team in a season and its points
     */
    private HashMap<Team, Integer> teamsInLeaguePerSeasonTable;
    private List<Game> gamesInLeaguePerSeason;

    /**
     * DONT FORGET TO UPDATE THE TEAMS OF THE LEAGUE PER SEASON
     * @param season
     * @param schedulingMethod
     * @param rankingMethod
     */
    public LeaguePerSeason(int season, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsInLeaguePerSeasonTable = new LinkedHashMap<>();
        this.gamesInLeaguePerSeason = new ArrayList<>();
        this.isBegin = false;
        this.referees = new HashSet<>();
    }


    /**
     * With the teams in the constructor
     * @param season
     * @param teamsInLeaguePerSeason
     * @param schedulingMethod
     * @param rankingMethod
     */
    public LeaguePerSeason(int season, Set<Team> teamsInLeaguePerSeason, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        initializedTeamsInLeaguePerSeason(teamsInLeaguePerSeason); //new table league of the season including all teams
        this.gamesInLeaguePerSeason = new ArrayList<>();
        this.isBegin = false;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * initialized the table of season so every team have 0 points
     * @param teamsInLeaguePerSeason
     */
    public void initializedTeamsInLeaguePerSeason(Set<Team> teamsInLeaguePerSeason) {
        for(Team team : teamsInLeaguePerSeason){
            teamsInLeaguePerSeasonTable.put(team, 0);
        }
    }


    /**
     *
     * @return true, succeed to schedule the games according to the schedule method
     */
    public boolean scheduledGames(){
        if(isBegin){
           return false;
        }
        int i = 0;
        Team [] teamArray = new Team[this.teamsInLeaguePerSeasonTable.size()];
        for(Team team : this.teamsInLeaguePerSeasonTable.keySet()){
            teamArray[i] = team;
        }
        this.gamesInLeaguePerSeason.addAll(this.schedulingMethod.scheduleGamePolicy(this, teamArray));
        return true;
    }


    /**
     * UPDATE the table league after each game
     * @param game
     * @return tableOfTheLeague UPDATED
     */
    public  HashMap<Team, Integer> updateTableOfTheLeague(Game game){
        int hostTeamScore = 0; Team homeTeam = null;
        int awayTeamScore = 0; Team awayTeam = null;
        Team homeGameTeam = game.getHostTeam(); Team awayGameTeam = game.getGuestTeam();
        for(Team team : teamsInLeaguePerSeasonTable.keySet()){
            if(team.equals(homeGameTeam)){
                hostTeamScore = game.getHostTeamScore();
                homeTeam = team;
            }
            if (team.equals(awayGameTeam)){
                awayTeamScore = game.getHostTeamScore();
                awayTeam = team;
            }
        }

        if(hostTeamScore > awayTeamScore){
            teamsInLeaguePerSeasonTable.put(homeTeam, teamsInLeaguePerSeasonTable.get(homeTeam) + rankingMethod.getWinPoints());
            teamsInLeaguePerSeasonTable.put(awayTeam, teamsInLeaguePerSeasonTable.get(awayTeam) + rankingMethod.getLoosPoints());
        }else if(awayTeamScore > hostTeamScore){
            teamsInLeaguePerSeasonTable.put(homeTeam, teamsInLeaguePerSeasonTable.get(homeTeam) + rankingMethod.getLoosPoints());
            teamsInLeaguePerSeasonTable.put(awayTeam, teamsInLeaguePerSeasonTable.get(awayTeam) + rankingMethod.getWinPoints());
        }else{
            teamsInLeaguePerSeasonTable.put(homeTeam, teamsInLeaguePerSeasonTable.get(homeTeam) + rankingMethod.getDrawPoints());
            teamsInLeaguePerSeasonTable.put(awayTeam, teamsInLeaguePerSeasonTable.get(awayTeam) + rankingMethod.getDrawPoints());
        }
        return teamsInLeaguePerSeasonTable;
    }



    //Setters
    public boolean addReferee(Referee referee) {
        if(referee != null){
            this.referees.add(referee);
            return true;
        }
        return false;
    }

    public void setBegin(boolean begin) {
        isBegin = begin;
    }

    public boolean setReferees(HashSet<Referee> referees) {
        if(referees != null){
            this.referees = referees;
            return true;
        }
        return false;
    }

    public boolean setTeamsInLeaguePerSeasonTable(HashMap<Team, Integer> teamsInLeaguePerSeasonTable) {
        if(teamsInLeaguePerSeasonTable != null){
            this.teamsInLeaguePerSeasonTable = teamsInLeaguePerSeasonTable;
            return true;
        }
        return false;
    }




    //Getters

    public int getSeason() {
        return season;
    }

    public SchedulingMethod getSchedulingMethod() {
        return schedulingMethod;
    }

    public void setRankingMethod(RankingMethod rankingMethod) {
        this.rankingMethod = rankingMethod;
    }

    public boolean isBegin() {
        return isBegin;
    }

    public void setSchedulingMethod(SchedulingMethod schedulingMethod) {
        this.schedulingMethod = schedulingMethod;
    }

    public RankingMethod getRankingMethod() {
        return rankingMethod;
    }

    public HashMap<Team, Integer> getTeamsInLeaguePerSeason() {
        return teamsInLeaguePerSeasonTable;
    }

    public List<Game> getGamesInLeaguePerSeason() {
        return gamesInLeaguePerSeason;
    }
}
