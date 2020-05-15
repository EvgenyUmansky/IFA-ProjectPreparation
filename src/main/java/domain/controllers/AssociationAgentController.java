package domain.controllers;

import domain.*;

public class AssociationAgentController {

    // =================== Association Agent functions ====================
    // ====================================================================


    /**
     * UC 9.1
     * Creates a new league
     * @param leagueName the league name
     * @return an instance of the new league
     */
    public League setLeague(String leagueName) {
        return new League(leagueName);
    }


    /**
     * UC 9.2
     * Updates the season in the league
     * @param leagueName the league name
     * @param season the season
     * @return the updated league
     */
    public League updateSeasonForLeague(String leagueName, String season) {
        return League.getLeagueByName(leagueName).setSeason(Integer.parseInt(season));
    }


    /**
     * UC 9.3
     * Registers a new referee in the system
     * @param username the referee's username
     * @param password the referee's password
     * @param name the referee's name
     * @param mail the referee's mail
     * @throws Exception if the creation was unsuccessful
     */
    public void createReferee(String username, String password, String name, String mail) throws Exception {
        // this.register(username, password, name, mail).addRoleToUser(Role.REFEREE);
        // TODO: Send invitation to referee
    }

    /**
     * UC 9.3
     * Removes the referee role from a referee
     * @param username the referee's username
     */
    public void removeReferee(String username) {
        User.getUserByID(username).removeRoleFromUser(Role.REFEREE);
    }

    /**
     * UC 9.4
     * Adds a referee to a league in a specific season
     * @param league the league
     * @param userName the referee's username
     */
    public void addRefereeToLeaguePerSeason(League league, String userName) {
        //This method will be shown after the user chose a referee from the list (using getReferees() method)

        league.addReferee((Referee) User.getUserByID(userName).getRoles().get(Role.REFEREE));
        Alert alert = new Alert();
        alert.addToMailSet(User.getUserByID(userName).getRoles().get(Role.REFEREE));
        alert.sendAlert(new AlertNotification("Invitation","MAZAL TOV! you are a referee!!"));
    }

    /**
     * UC 9.5
     * Sets the ranking method in the league
     * @param winP amount of points given for a win
     * @param drawP amount of points given for a draw
     * @param loseP amount of points given for a loss
     * @param league the league
     */
    public void setRankingMethod(int winP,int drawP,  int loseP, League league) {
        league.getRankingMethod().setRankingMethod(winP, loseP, drawP);
    }

    /**
     * UC 9.6
     * Sets the scheduling method of teams into games in the league
     * @param league the league
     * @param schedulingMethod the scheduling method
     */
    public void setSchedulingMethod(League league, SchedulingMethod schedulingMethod) {
        league.setSchedulingMethod(schedulingMethod);
    }

    /**
     * UC 9.7
     * schedules teams into games in a league
     * @param league the league
     */
    public void scheduleGamesInLeagues(League league) {
        // Click this button after you have all the teams in league, Automatic scheduling
        league.scheduledGames();
    }

    /**
     * UC 9.8
     *
     */
    public void setRulesForBudgetControl() {
    }

    /**
     * UC 9.9
     *
     */
    public void setTeamBudget() {
    }
}
