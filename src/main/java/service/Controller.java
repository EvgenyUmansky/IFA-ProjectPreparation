package service;

import domain.*;
import domain.controllers.*;

import java.text.ParseException;
import java.util.*;

/**
 * This class is the controller in the system - it receives calls from the UI and activates the functionality in each class in the domain layer.
 */
public class Controller {
    CoachController coachController;
    FanController fanController;
    GuestController guestController;
    SystemManagerController systemManagerController;
    PersonalPageController personalPageController;
    PlayerController playerController;
    RefereeController refereeController;
    StartController startController;
    private TeamController teamController;
    private LeagueController leagueController;
    private GameController gameController;

    // ========================= Constructor =========================

    /**
     * Constructor
     */
    public Controller(CoachController coachController, FanController fanController, GuestController guestController, SystemManagerController managerController, PersonalPageController personalPageController, PlayerController playerController, RefereeController refereeController, StartController startController, TeamController teamController, LeagueController leagueController, GameController gameController) {
        this.coachController = coachController;
        this.fanController = fanController;
        this.guestController = guestController;
        this.systemManagerController = managerController;
        this.personalPageController = personalPageController;
        this.playerController = playerController;
        this.refereeController = refereeController;
        this.startController = startController;
        this.teamController = teamController;
        this.leagueController = leagueController;
        this.gameController = gameController;
    }
    // ========================= System functions =========================
    // ====================================================================

    /**
     * UC 1.1.1
     * Connects to external systems
     */
    public void connectToExternalSystems() {
        startController.connectToExternalSystems();
    }


    /**
     * UC 2.3
     * Connects a user into the system
     * @param userName the user's username
     * @param password the user's password
     * @return the user's instance
     */
    public User login(String userName, String password) throws Exception {
        return startController.login(userName, password);
    }

    /**
     * UC 3.1
     * Disconnects a user from the system
     * @param userName the user's username
     */
    public void logout(String userName) {
        startController.logout(userName);
    }

    /**
     * UC 2.2
     * Registers a new user in the system
     * @param userName the new user's username
     * @param password the new user's password
     * @param name the new user's name
     * @param mail the new user's mail
     * @return the new user instance
     * @throws Exception if the registration was unsuccessful
     */
    public User register(String userName, String password, String name, String mail) throws Exception {
        return startController.register(userName, password, name, mail);
    }


    // =================== User functions ===========================
    // ==============================================================

    /**
     * Returns all the profile pages that a user has permission to edit
     * @param username the user's username
     * @return the list of pages he has permissions to
     */
    public ArrayList<PersonalPage> getPagesByUsername(String username) {
        return personalPageController.getPagesByUsername(username);
    }


    // =================== Personal Pages functions =================
    // ==============================================================


    /**
     * UC 4.2, 5.2
     * Updates the info in the profile page
     * @param pageName the profile page
     * @param info the updated info
     * @return the updated page
     */
    public PersonalPage updateInfo(String pageName, String info){
        return personalPageController.updateInfo(pageName, info);
    }


    // =================== Team Player functions ====================
    // ==============================================================


    /**
     * UC 4.1
     * Updates the player's details
     * @param username the player's username
     * @param playerName the player's name
     * @param birthDate the player's birth date
     * @param position the player's position
     * @param squadNumber the player's shirt number
     */
    public void updatePlayerDetails(String username, String playerName, String birthDate, String position, String squadNumber) throws ParseException {
        playerController.updatePlayerDetails(username, playerName, birthDate, position, squadNumber);
    }


    // ======================= Coach functions ============================
    // ====================================================================


    /**
     * UC 5.1
     * Updates the coach's details
     * @param username the coach's username
     * @param coachName the coach's name
     * @param qualification the coach's qualification
     * @param role the coach's role
     */
    public void updateCoachDetails(String username, String coachName, String qualification, String role) {
        coachController.updateCoachDetails(username, coachName, qualification, role);
    }


    // ========================= Guest functions ==========================
    // ====================================================================


    /**
     * UC 2.4
     * Returns the team instance by the team's name
     * @param teamName the team's name
     * @return the team instance by the team's name
     */
    public Team getTeamDetails(String teamName) {
        return teamController.getTeamDetails(teamName);
    }


    /**
     * Returns the player instance by his name
     * @param playerName the player's name
     * @return the player instance by his name
     */
    public TeamPlayer getPlayersDetails(String playerName) {
        return playerController.getPlayersDetails(playerName);
    }


    /**
     * Returns the coach instance by his name
     * @param coachName the player's name
     * @return the coach instance by his name
     */
    public TeamCoach getCoachDetails(String coachName) {
        return coachController.getCoachDetails(coachName);
    }


    /**
     * Returns the league instance that matches the league name
     * @param leagueName the league name
     * @return the league instance that matches the league name
     */
    public League getLeagueDetails(String leagueName) {
        return leagueController.getLeagueDetails(leagueName);
    }


    /**
     * Returns the leagues instances from a certain season
     * @param year the season
     * @return the leagues instances from the season
     */
    public ArrayList<League> getSeasonDetails(String year) {
        return leagueController.getSeasonDetails(year);
    }


    /**
     * UC 2.5
     *
     * @param words
     */
    public void searchByKeyWord(String words) {
        guestController.searchByKeyWord(words);
    }


    // ========================= Fan functions ============================
    // ====================================================================


    /**
     * UC 3.2
     * Adds a fan as a subscriber to the page
     * @param pageName the profile page
     * @param username the fan's username
     */
    public void addFanSubscriptionToPersonalPage(String pageName, String username) {
        personalPageController.addFanSubscriptionToPersonalPage(pageName, username);
    }


    /**
     * UC 3.3
     * Adds a fan as a subscriber to a game
     * @param game the game
     * @param username the fan's username
     */
    public void addFanSubscriptionToGame(String game, String username) {
        gameController.addFanSubscriptionToGame(game, username);
    }


    /**
     * UC 3.4
     * Sends a complaint to the system administrators
     * @param username the user's username
     * @param sysAdmins the list of system administrators
     * @param message the complaint
     */
    public void sendComplaintToSysAdmin(String username, ArrayList<SystemAdministrator> sysAdmins, AlertNotification message) {
        // TODO: Strings in signature
        //((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).sendComplaintToSysAdmin(sysAdmins, message);
    }

    // UC 3.5 - get history of fans searches

    /**
     * UC 3.5
     * Gets a fan's history of searches
     * @param username the fan's username
     * @return the fan's history of searches
     */
    public String[] getFanHistory(String username) {
        //TODO - get from data base
        return fanController.getFanHistory(username);
    }

    /**
     * UC 3.6
     * Returns the info of a fan
     * @param username the fan's username
     * @return the fan's info
     */
    public String getFanProfileDetails(String username) {
        return fanController.getFanProfileDetails(username);
    }

    /**
     * UC 3.6
     * Updates a fan's info
     * @param username the fan's username
     * @param newPassword the new password
     * @param newName the new name
     * @param newMail the new mail
     */
    public void setFanProfileDetails(String username, String newPassword, String newName, String newMail) {
        fanController.setFanProfileDetails(username, newPassword, newName, newMail);
    }

    // ========================= Referee functions ========================
    // ====================================================================


    /**
     * UC 10.1
     * Returns the details about a referee
     * @param username the referee's username
     * @return the info about the referee
     */
    public String getRefereeDetails(String username) {
        return refereeController.getRefereeDetails(username);
    }

    /**
     * UC 10.1
     * Updates the details about a referee
     * @param username the referee's username
     * @param newPassword the new password
     * @param newName the new name
     * @param newMail the new mail
     * @param qualification the updated qualification
     * @param refereeType the updated type
     */
    public void setRefereeProfileDetails(String username, String newPassword, String newName, String newMail, String qualification, String refereeType) {
        refereeController.setRefereeProfileDetails(username, newPassword, newName, newMail, qualification, refereeType);
    }


    /**
     * UC 10.2
     * Returns a list of games that the referee referees at
     * @param username the referee's username
     * @return the list of games that the referee referees at
     */
    public ArrayList<Game> getRefereeGames(String username) {
        return gameController.getRefereeGames(username);
    }


    /**
     * UC 10.3
     * Adds an event that took place during a game to its events list
     * @param username the referee's username
     * @param game the match
     * @param gameEvent the event
     * @throws Exception in case the addition was unsuccessful
     */
    public void addGameEventToGame(String username, String game, String gameEvent) throws Exception {
        gameController.addGameEventToGame(username, game, gameEvent);
    }


    /**
     * UC 10.4
     * Updates an event that took place during a game
     * @param username the referee's username
     * @param game the match
     * @param gameEvent the event
     * @param dateTimeStr the time the event took place
     * @param gameMinutes the minute of the game the event took place in
     * @param eventName the name of the event
     * @param description the description of the event
     * @throws Exception in case the update was unsuccessful
     */
    public void changeGameEvent(String username, String game, String gameEvent, String dateTimeStr, String gameMinutes, String eventName, String description) throws Exception {
        // TODO: check the referee is MAIN in UI
        gameController.changeGameEvent(username, game, gameEvent, dateTimeStr, gameMinutes, eventName, description);
    }


    // =================== Association Agent functions ====================
    // ====================================================================


    /**
     * UC 9.1
     * Creates a new league
     * @param leagueName the league name
     * @return an instance of the new league
     */
    public League setLeague(String leagueName) {
        return leagueController.setLeague(leagueName);
    }


    /**
     * UC 9.2
     * Updates the season in the league
     * @param leagueName the league name
     * @param season the season
     * @return the updated league
     */
    public League updateSeasonForLeague(String leagueName, String season) {
        return leagueController.updateSeasonForLeague(leagueName, season);
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
        refereeController.createReferee(username, password, name, mail);
        // TODO: Send invitation to referee
    }

    /**
     * UC 9.3
     * Removes the referee role from a referee
     * @param username the referee's username
     */
    public void removeReferee(String username) {
        refereeController.removeReferee(username);
    }

    /**
     * UC 9.4
     * Adds a referee to a league in a specific season
     * @param leagueName the league
     * @param userName the referee's username
     */
    public void addRefereeToLeaguePerSeason(String leagueName, String userName) {
        leagueController.addRefereeToLeaguePerSeason(leagueName, userName);
    }

    /**
     * UC 9.5
     * Sets the ranking method in the league
     * @param winP amount of points given for a win
     * @param drawP amount of points given for a draw
     * @param loseP amount of points given for a loss
     * @param leagueName the league
     */
    public void setRankingMethod(String winP, String drawP, String loseP, String leagueName) {
       leagueController.setRankingMethod(winP, drawP, loseP, leagueName);
    }

    /**
     * UC 9.6
     * Sets the scheduling method of teams into games in the league
     * @param leagueName the league
     * @param schedulingMethodName the scheduling method
     */
    public void setSchedulingMethod(String leagueName, String schedulingMethodName) {
        leagueController.setSchedulingMethod(leagueName, schedulingMethodName);
    }

    /**
     * UC 9.7
     * schedules teams into games in a league
     * @param leagueName the league
     */
    public void scheduleGamesInLeagues(String leagueName) {
    // Click this button after you have all the teams in league, Automatic scheduling
        leagueController.scheduleGamesInLeagues(leagueName);
    }

    /**
     * UC 9.8
     *
     */
    public void setRulesForBudgetControl() {
        teamController.setRulesForBudgetControl();
    }

    /**
     * UC 9.9
     *
     */
    public void setTeamBudget() {
        teamController.setTeamBudget();
    }

    //
    // =================== Team Owner functions ====================
    // =============================================================


    /**
     * UC 6.1
     * Adds a player to a team
     * @param teamName the team
     * @param userName the player's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addPlayer(String teamName, String userName) throws Exception {
        teamController.addPlayer(teamName, userName);
    }

    /**
     * UC 6.1
     * Adds a coach to a team
     * @param teamName the team
     * @param userName the coach's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addCoach(String teamName, String userName) throws Exception {
        teamController.addCoach(teamName, userName);
    }

    /**
     * UC 6.1
     * Adds a field to a team
     * @param teamName the team
     * @param fieldName the field's name
     * @throws Exception if the addition was unsuccessful
     */
    public void addField(String teamName, String fieldName) throws Exception {
        teamController.addField(teamName, fieldName);
    }


    /**
     * UC 6.1
     * Removes a player from the team
     * @param teamName the team
     * @param userName the player's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removePlayer(String teamName, String userName) throws Exception {
        teamController.removePlayer(teamName, userName);
    }


    /**
     * UC 6.1
     * Removes a coach from the team
     * @param teamName the team
     * @param userName the coach's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removeCoach(String teamName, String userName) throws Exception {
        teamController.removeCoach(teamName, userName);
    }

    /**
     * UC 6.1
     * Removes a field from the team
     * @param teamName the team
     * @param fieldName the field's name
     * @throws Exception if the removal was unsuccessful
     */
    public void removeField(String teamName, String fieldName) throws Exception {
        teamController.removeField(teamName, fieldName);
    }


    /**
     * UC 6.2
     * Adds a new owner to a team
     * @param teamName the team
     * @param userNameNewTeamOwner the username of the new owner
     * @param userNameTeamOwner the username of the owner that appoints the new one
     * @throws Exception if the appointment was unsuccessful
     */
    public void addOwner(String teamName, String userNameNewTeamOwner, String userNameTeamOwner) throws Exception {
        teamController.addOwner(teamName, userNameNewTeamOwner, userNameTeamOwner);
    }


    /**
     * UC 6.3
     * Removes an owner from the team.
     * This operation is possible only if the removed owner was appointed by the removing owner
     * @param teamName the team
     * @param userNameTeamOwner the owner that removes
     * @param userNameRemovedTeamOwner the owner that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeOwner(String teamName, String userNameTeamOwner, String userNameRemovedTeamOwner) throws Exception {
        teamController.removeOwner(teamName, userNameTeamOwner, userNameRemovedTeamOwner);
    }


    /**
     * UC 6.4
     * Adds a team manager to the team
     * @param teamName the team
     * @param userNameNewTeamManager the new manager
     * @param userNameTeamOwner the owner
     * @throws Exception if the addition was unsuccessful
     */
    public void addManager(String teamName, String userNameNewTeamManager, String userNameTeamOwner) throws Exception {
        teamController.addManager(teamName, userNameTeamOwner, userNameTeamOwner);
    }

    /**
     * UC 6.5
     * Removes a manager from the team.
     * This operation is possible only if the removed manager was appointed by the removing owner
     * @param teamName the team
     * @param userNameTeamOwner the owner that removes
     * @param userNameRemovedTeamManager the manager that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeManager(String teamName, String userNameRemovedTeamManager, String userNameTeamOwner) throws Exception {
        teamController.removeManager(teamName, userNameTeamOwner, userNameTeamOwner);
    }


    /**
     * UC 6.6, 8.1
     * Closes a team
     * @param userName the username of the user that closes the team
     * @param teamName the team
     */
    public void closeTeam(String userName, String teamName) {
        teamController.closeTeam(userName, teamName);
    }

    /**
     * UC 6.6
     * Reopens a team
     * @param teamName the team
     */
    public void openTeam(String teamName) {
        teamController.openTeam(teamName);
    }


    /**
     * UC 6.7
     *
     */
    public void manageFinance(){
    }


    // =================== Team Manager functions ====================
    // ===============================================================

    /**
     * UC 7.1
     *
     */
    public void setPermissionsToManager() {
        // UC 7.1 - set permissions to team manager
        //responsible of Team Owner!
    }


    // =================== System Manager functions ====================
    // =================================================================


    /**
     * UC 8.2
     * Removes a user from the system
     * @param userName the removed user's username
     */
    public void removeUserFromSystem(String userName) {
        systemManagerController.removeUserFromSystem(userName);
    }


    /**
     * UC 8.3
     *
     */
    public void showComplain() {
        // UC 8.3A - show Complaint
        systemManagerController.showComplain();
    }


    /**
     * UC 8.3
     *
     */
    public void commentToComplaint() {
        // UC 8.3B - add comment to complaint
        systemManagerController.commentToComplaint();
    }


    /**
     * UC 8.4
     *
     */
    public void showLogDocument() {
        // UC 8.4 - show log document
        systemManagerController.showLogDocument();
    }


    /**
     * UC 8.5
     *
     */
    public void startModelRecommendationSystem() {
        // UC 8.5 - start model of recommendation Systems
        systemManagerController.startModelRecommendationSystem();
    }


    // =================================== General =======================================

    /**
     * Returns a team instance by its name
     * @param teamName the team's name
     * @return the team instance that matches the name
     */
    public Team getTeamByName(String teamName) {
        return teamController.getTeamByName(teamName);
    }

    /**
     * Returns the list of roles of a certain user
     * @param userName the user's username
     * @return the user's list of roles
     * @throws Exception if the user doesn't exist
     */
    public HashMap<Role, Subscriber> getUserRoles(String userName) throws Exception {
        return startController.getUserRoles(userName);
    }



    /**
     * TEST function - SHOULD BE IMPLEMENTED IN UI
     */
    public User showLoginPanel() throws Exception {
        return startController.showLoginPanel();
    }


    /**
     *  TEST function - SHOULD BE IMPLEMENTED IN UI
     */
    public void runSystem() throws Exception {
        // Load test Data (Use mock)
        startController.runSystem();
    }


    /**
     * UC 1.1
     * Initializes the system
     * @throws Exception if the initialization was unsuccessful
     */
    public void init() throws Exception {
        // TODO: Create DB if not exist -> Later
        startController.init();
    }
}
