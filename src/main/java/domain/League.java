package domain;

import java.util.*;

/**
 * Represents a football league in a certain season
 */
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


    //====================== Constructors =====================//

    /**
     * Constructor
     * @param season the season
     * @param schedulingMethod the method of games scheduling
     * @param rankingMethod the method of table ranking
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

    /**
     * Constructor
     * @param leagueName the name of the league
     */
    public League(String leagueName) {
        this.leagueName = leagueName;
    }


    /**
     * Constructor
     * @param season the season
     * @param teamsInLeaguePerSeason the list of teams that participate in the league in the season
     * @param schedulingMethod the method of games scheduling
     * @param rankingMethod the method of table ranking
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



    //====================== DB Access Functions =====================//

    /**
     * Returns the league instance that matches the given name and season from the DB
     * @param season the season
     * @param leagueName the name of the league
     * @return  the league instance that matches the given name and season from the DB
     */
    public static League getLeaguePerSeason(int season, String leagueName){
        return new League(season, null, null, null, leagueName); // TODO: Replace with DB call
    }

    /**
     * Returns the league instance that matches the given name from the DB
     * @param leagueName the name of the league
     * @return the league instance that matches the given name from the DB
     */
    public static League getLeagueByName(String leagueName){
        return new League(leagueName); // TODO: Replace with DB call
    }

    /**
     * Returns all the leagues that took place in the given season from the DB
     * @param season the given season
     * @return all the leagues that took place in the given season from the DB
     */
    public static ArrayList<League> getAllLeaguesPerSeason(int season){
        ArrayList<League> leaguesPerSeason = new ArrayList<>();
        return leaguesPerSeason; // TODO: Replace with DB call
    }


    //====================== Functionality =====================//

    /**
     * initializes the table of the season. Each team has 0 points and played no games.
     * @param teams the teams that participate in the league in the season
     * @return true if the initialization was successful, false if not
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
     * Schedules teams into games according to the scheduling method
     * @return true if the scheduling was successful, false if not
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
     * Updates the league table according to the result of a given match
     * @param game the match
     * @return the updated league table
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
     * Updates the table according to the result of the game
     * @param homeTeam the home team in the game
     * @param awayTeam the visiting team in the game
     * @param hostTeamScore the amount of goals the home team scored
     * @param awayTeamScore the amount of goals the visiting team scored
     * @return true if the update was successful, false if not
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

    /**
     * Adds a referee to the list of referees in the league
     * @param referee the added referee
     * @return true if the addition was successful, false if not
     */
    public boolean addReferee(Referee referee) {
        if(referee != null){
            this.referees.add(referee);
            return true;
        }
        return false;
    }

    //====================== Getters and Setters =====================//


    /**
     * Indicates if the season has begun in this league
     * @param begin true if the season begun, false if not
     */
    public void setBegin(boolean begin) {
        isBegin = begin;
    }

    /**
     * Sets the list of referees in the league to the given list
     * @param referees the list of referees
     * @return true if the update was successful, false if not
     */
    public boolean setReferees(HashSet<Referee> referees) {
        if(referees != null){
            this.referees = referees;
            return true;
        }
        return false;
    }

    /**
     * Sets the league table to the given table
     * @param teamsInLeaguePerSeasonTable the given table
     * @return true if the update was successful, false if not
     */
    public boolean setTeamsTable(HashMap<Team, Integer> teamsInLeaguePerSeasonTable) {
        if(teamsInLeaguePerSeasonTable != null){
            this.teamsTable = teamsInLeaguePerSeasonTable;
            return true;
        }
        return false;
    }


    /**
     * Updates the season to the given season
     * @param season the given season
     * @return the updated league
     */
    public League setSeason(int season){
        this.season = season;
        return this;
    }


    /**
     * Returns the season
     * @return the season
     */
    public int getSeason() {
        return season;
    }

    /**
     * Returns the scheduling method of teams into games
     * @return the scheduling method of teams into games
     */
    public SchedulingMethod getSchedulingMethod() {
        return schedulingMethod;
    }

    /**
     * Updates the ranking method of teams in the table
     * @param rankingMethod the ranking method of teams in the table
     */
    public void setRankingMethod(RankingMethod rankingMethod) {
        this.rankingMethod = rankingMethod;
    }

    /**
     * Indicates if the season has begun in this league
     * @return true if the season has begun in this league, false if not
     */
    public boolean isBegin() {
        return isBegin;
    }

    /**
     * Updates the scheduling method of teams into games to the given method
     * @param schedulingMethod the scheduling method of teams into games
     */
    public void setSchedulingMethod(SchedulingMethod schedulingMethod) {
        this.schedulingMethod = schedulingMethod;
    }

    /**
     * Returns the ranking method of teams in the table
     * @return the ranking method of teams in the table
     */
    public RankingMethod getRankingMethod() {
        return rankingMethod;
    }

    /**
     * Returns the set of teams that participate in the league this season
     * @return the set of teams that participate in the league this season
     */
    public HashMap<Team, Integer> getTeamsInLeaguePerSeason() {
        return teamsTable;
    }

    /**
     * Returns the list of games in the league this season
     * @return the list of games in the league this season
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Returns the name of the league
     * @return the name of the league
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     * Returns the list of referees that belong to this league
     * @return the list of referees that belong to this league
     */
    public HashSet<Referee> getReferees() {
        return referees;
    }
}
