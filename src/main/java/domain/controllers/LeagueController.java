package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.LeagueDBAccess;
import DataAccess.LeagueSeasonDBAccess;
import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import service.pojos.TeamDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LeagueController {
    static Logger logger = Logger.getLogger(LeagueController.class.getName());

    private DBAccess<League> lda = LeagueDBAccess.getInstance();
    private DBAccess<ArrayList<League>> lsda = LeagueSeasonDBAccess.getInstance();

    public ArrayList<League> getLeagues() {
        // TODO: get leagues from DB
       /* League league1 = new League(2020, new OneGameSchedulingMethod(), new RankingMethod(), "aleph");
        League league2 = new League(2020, new OneGameSchedulingMethod(), new RankingMethod(),"bet");
        League league3 = new League(2020, new OneGameSchedulingMethod(), new RankingMethod(),"gimel");*/
//        League league4 = new League(2020, new OneGameSchedulingMethod(), new RankingMethod(),"dalet");
//        League league5 = new League(2020, new OneGameSchedulingMethod(), new RankingMethod(),"hea");

        HashMap<String, League> allLeagues = lda.conditionedSelect(new String[0]);
        return new ArrayList<>(allLeagues.values());
    }

    public League createSeason(String leagueName, String season, String scheduling, String winPoints, String losePoints, String drawPoints) {
        League league = new League(leagueName, Integer.parseInt(season), scheduling.equalsIgnoreCase("oneGameSchedulingMethod"),
                Integer.parseInt(winPoints), Integer.parseInt(losePoints), Integer.parseInt(drawPoints));

        lda.save(league);

        logger.info(leagueName + " " + season + "  was created");
        return league;
    }

    public ArrayList<League> getAllSeasonsInLeagues(){
        HashMap<String,ArrayList<League>> allLeaguesPerSeason = lsda.conditionedSelect(new String[0]);
        ArrayList<League> allLeagues = new ArrayList<>();
        ArrayList<ArrayList<League>> values = new ArrayList<>(allLeaguesPerSeason.values());
        for(ArrayList<League> leagues : values){
            allLeagues.addAll(leagues);
        }

        return allLeagues;
    }

    // =================== Guest functions ====================
    // ========================================================

    /**
     * UC 2.4
     * Returns the league instance that matches the league name
     *
     * @param leagueName the league name
     * @return the league instance that matches the league name
     */
    public League getLeagueDetails(String leagueName) {
        return League.getLeagueByName(leagueName);
    }

    /**
     * UC 2.4
     * Returns the leagues instances from a certain season
     *
     * @param year the season
     * @return the leagues instances from the season
     */
    public ArrayList<League> getSeasonDetails(String year) {
        return League.getAllLeaguesPerSeason(Integer.parseInt(year));
    }


    // =================== Association Agent functions ====================
    // ====================================================================

    /**
     * UC 9.1
     * Creates a new league
     *
     * @param leagueName the league name
     * @return an instance of the new league
     */
    public League setLeague(String leagueName) {
        logger.info(leagueName + " was set");
        return new League(leagueName);
    }


    /**
     * UC 9.2
     * Updates the season in the league
     *
     * @param leagueName the league name
     * @param season     the season
     * @return the updated league
     */
    public League updateSeasonForLeague(String leagueName, String season) {
        logger.info(season + " was updated for " + leagueName);
        return League.getLeagueByName(leagueName).setSeason(Integer.parseInt(season));
    }

    /**
     * UC 9.4
     * Adds a referee to a league in a specific season
     *
     * @param leagueName the league
     * @param userName   the referee's username
     */
    public void addRefereeToLeaguePerSeason(String leagueName, String userName) {
        //This method will be shown after the user chose a referee from the list (using getReferees() method)

        // TODO: get league
        // League league = get from DB by leagueName
        // league.addReferee((Referee) User.getUserByID(userName).getRoles().get(Role.REFEREE));

//        Alert alert = new Alert();
//        alert.addToMailSet(User.getUserByID(userName).getRoles().get(Role.REFEREE));
//        alert.sendAlert(new AlertNotification("Invitation","MAZAL TOV! you are a referee!!"));
        logger.info(userName + " was added to " + leagueName);
    }

    /**
     * UC 9.5
     * Sets the ranking method in the league
     *
     * @param league the league
     * @param winP   amount of points given for a win
     * @param drawP  amount of points given for a draw
     * @param loseP  amount of points given for a loss
     */
    public void setRankingMethod(String league, String winP, String drawP, String loseP) {
        // TODO: get league
        // League league = get from DB by leagueName
        // league.getRankingMethod().setRankingMethod(Integer.parseInt(winP), Integer.parseInt(loseP), Integer.parseInt(drawP));
        logger.info("New Ranking Method was added to " + league);
    }

    /**
     * UC 9.6
     * Sets the scheduling method of teams into games in the league
     *
     * @param league               the league
     * @param schedulingMethodName the scheduling method
     */
    public void setSchedulingMethod(String league, String schedulingMethodName) {
        SchedulingMethod schedulingMethod;
        switch (schedulingMethodName) {
            case "OneGameSchedulingMethod":
                logger.info(schedulingMethodName + " Scheduling Method was added to " + league);
                schedulingMethod = new OneGameSchedulingMethod();
            case "TwoGameSchedulingMethod":
                logger.info(schedulingMethodName + " Scheduling Method was added to " + league);
                schedulingMethod = new TwoGameSchedulingMethod();
            default:
                return;
        }
        // TODO: get league
        // League league = get from DB by leagueName
        // league.setSchedulingMethod(schedulingMethod);
    }

    /**
     * UC 9.7
     * schedules teams into games in a league
     *
     * @param leagueName the league
     */
    public void scheduleGamesInLeagues(String leagueName) {
        // Click this button after you have all the teams in league, Automatic scheduling
        // League league = get from DB by leagueName
        // league.scheduledGames();
    }
}
