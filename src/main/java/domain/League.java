package domain;

import java.util.*;

public class League {
    private boolean isBegin;
    private int season;
    private String leagueName;
    private SchedulingMethod schedulingMethod;
    private RankingMethod rankingMethod;
    private HashSet<Referee> referees;
    /**
     * saves each team in a season and its points
     */
    private HashMap<Team, Integer> teamsTable;
    private List<Game> games;

    /**
     * DONT FORGET TO UPDATE THE TEAMS OF THE LEAGUE PER SEASON
     * @param season
     * @param schedulingMethod
     * @param rankingMethod
     */
    public League(int season, SchedulingMethod schedulingMethod, RankingMethod rankingMethod, String leagueName) {
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        this.teamsTable = new LinkedHashMap<>();
        this.games = new ArrayList<>();
        this.isBegin = false;
        this.referees = new HashSet<>();
        this.leagueName = leagueName;
    }

    public League(String leagueName) {
        this.leagueName = leagueName;
    }


    /**
     * With the teams in the constructor
     * @param season
     * @param teamsInLeaguePerSeason
     * @param schedulingMethod
     * @param rankingMethod
     */
    public League(int season, Set<Team> teamsInLeaguePerSeason, SchedulingMethod schedulingMethod, RankingMethod rankingMethod, String leagueName) {
        this.season = season;
        this.schedulingMethod = schedulingMethod;
        this.rankingMethod = rankingMethod;
        initTeams(teamsInLeaguePerSeason); //new table league of the season including all teams
        this.games = new ArrayList<>();
        this.isBegin = false;
        this.leagueName = leagueName;
    }

    public static League getLeauePerSeason(int season, String leagueName){
        return new League(season, null, null, null, leagueName); // TODO: Replace with DB call
    }
    public static League getLeagueByName(String leagueName){
        return new League(leagueName); // TODO: Replace with DB call
    }
    public static ArrayList<League> getAllLeaguesPerSeason(int season){
        ArrayList<League> leaguesPerSeason = new ArrayList<>();
        return leaguesPerSeason; // TODO: Replace with DB call
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * initialized the table of season so every team have 0 points
     * @param teams
     */
    public boolean initTeams(Set<Team> teams) {
        if(teams != null){
            for(Team team : teams){
                teamsTable.put(team, 0);
            }
            return true;
        }
       return false;
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
        Team [] teamArray = new Team[this.teamsTable.size()];
        for(Team team : this.teamsTable.keySet()){
            teamArray[i] = team;
            i++;
        }
        this.games.addAll(this.schedulingMethod.scheduleGamePolicy(this, teamArray));
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
        for(Team team : teamsTable.keySet()){
            if(team.equals(homeGameTeam)){
                hostTeamScore = game.getHostTeamScore();
                homeTeam = team;
            }
            if (team.equals(awayGameTeam)){
                awayTeamScore = game.getGuestTeamScore();
                awayTeam = team;
            }
        }

        //update The Table
        updateTable(homeTeam, awayTeam, hostTeamScore, awayTeamScore);

        return teamsTable;
    }

    /**
     * Update the table according to the result of the game
     * @param homeTeam
     * @param awayTeam
     * @param hostTeamScore
     * @param awayTeamScore
     */
    private boolean updateTable(Team homeTeam, Team awayTeam, int hostTeamScore, int awayTeamScore){
        if(homeTeam != null && awayTeam != null){
            if(hostTeamScore > awayTeamScore){
                teamsTable.put(homeTeam, teamsTable.get(homeTeam) + rankingMethod.getWinPoints());
                teamsTable.put(awayTeam, teamsTable.get(awayTeam) + rankingMethod.getLoosPoints());
            }else if(awayTeamScore > hostTeamScore){
                teamsTable.put(homeTeam, teamsTable.get(homeTeam) + rankingMethod.getLoosPoints());
                teamsTable.put(awayTeam, teamsTable.get(awayTeam) + rankingMethod.getWinPoints());
            }else{
                teamsTable.put(homeTeam, teamsTable.get(homeTeam) + rankingMethod.getDrawPoints());
                teamsTable.put(awayTeam, teamsTable.get(awayTeam) + rankingMethod.getDrawPoints());
            }
            return true;
        }
        return false;
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

    public boolean setTeamsTable(HashMap<Team, Integer> teamsInLeaguePerSeasonTable) {
        if(teamsInLeaguePerSeasonTable != null){
            this.teamsTable = teamsInLeaguePerSeasonTable;
            return true;
        }
        return false;
    }


    // Setters
    public League setSeason(int season){
        this.season = season;
        return this;
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
        return teamsTable;
    }

    public List<Game> getGames() {
        return games;
    }
}
