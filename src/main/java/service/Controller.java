package service;

import domain.*;

import java.util.*;

/**
 * This class is the controller in the system - it receives calls from the UI and activates the functionality in each class in the domain layer.
 */
public class Controller {

    private LinkedList<SystemEvent> systemEvents;
    private HashSet<League> leagues;


    // ========================= Constructor =========================

    /**
     * Constructor
     */
    public Controller() {
        systemEvents = new LinkedList<>();
    }


     // ========================= System functions =========================
    // ====================================================================

    /**
     * UC 1.1.1
     * Connects to external systems
     */
    public void connectToExternalSystems() {
        // TODO: Connect to external system. if fails throws Exception
    }


    /**
     * UC 2.3
     * Connects a user into the system
     * @param userName the user's username
     * @param password the user's password
     * @return the user's instance
     */
    // Should be bound to AuthController
    public User login(String userName, String password) throws Exception {
        User user = User.getUserByID(userName);

        if (user == null) {
            throw new Exception("User not found!");
        }

        if (!user.getPassword().equals(password)) {
            throw new Exception("Wrong password!");
        }

        user.connect();
        return user;
    }

    /**
     * UC 3.1
     * Disconnects a user from the system
     * @param userName the user's username
     */
    // Should be bound to AuthController
    public void logout(String userName) {
        User user = User.getUserByID(userName);
        user.disconnect();
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
    // Should be bound to AuthController
    public User register(String userName, String password, String name, String mail) throws Exception {
        User user = User.getUserByID(userName);
        if (user != null) {
            throw new Exception("User already exist");
        }

        if (!User.isValidUserName(userName)) {
            throw new Exception("Username is not valid");
        }
        if (!User.isValidPassword(password)) {
            throw new Exception("Password is not valid");
        }

        User newUser = new User(userName, password, name, mail);
        newUser.addRoleToUser(Role.FAN, new Fan(newUser.getUserName(), newUser.getMail()));

        return newUser;
    }


    // =================== User functions ===========================
    // ==============================================================

    /**
     * Returns all the profile pages that a user has permission to edit
     * @param username the user's username
     * @return the list of pages he has permissions to
     */
    // Should be bound to PagerController
    public ArrayList<PersonalPage> getPagesByUsername(String username) {
        return User.getUserByID(username).getPages();
    }


    // =================== Personal Pages functions =================
    // ==============================================================


    /**
     * UC 4.2, 5.2
     * Updates the info in the profile page
     * @param page the profile page
     * @param info the updated info
     * @return the updated page
     */
    // Should be bound to PagerController
    public PersonalPage updateInfo(PersonalPage page, String info){
        return page.setInfo(info);
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
    // Should be bound to PagerController
    public void updatePlayerDetails(String username, String playerName, Date birthDate, String position, String squadNumber) {
        User playerUser = User.getUserByID(username);
        if(playerName!=null){
            playerUser.setName(playerName);
        }
        ((TeamPlayer)User.getUserByID(username).getRoles().get(Role.TEAM_PLAYER)).updateDetails(birthDate,position,squadNumber);

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
    // Should be bound to PagerController
    public void updateCoachDetails(String username, String coachName, String qualification, String role) {
        User coachUser = User.getUserByID(username);
        if(coachName!=null){
            coachUser.setName(coachName);
        }
        ((TeamCoach)User.getUserByID(username).getRoles().get(Role.COACH)).updateDetails(qualification,role);
    }


    // ========================= Guest functions ============================
    // ====================================================================


    /**
     * UC 2.4
     * Returns the team instance by the team's name
     * @param teamName the team's name
     * @return the team instance by the team's name
     */
    // Should be bound to TeamsController
    public Team getTeamDetails(String teamName) {
        return Team.getTeamByName(teamName);
    }


    /**
     * Returns the player instance by his name
     * @param playerName the player's name
     * @return the player instance by his name
     */
    // Should be bound to PlayerController
    public TeamPlayer getPlayersDetails(String playerName) {
        return TeamPlayer.getPlayerByName(playerName);
    }


    /**
     * Returns the coach instance by his name
     * @param coachName the player's name
     * @return the coach instance by his name
     */
    // Should be bound to Coach..?Controlller
    public TeamCoach getCoachDetails(String coachName) {
        return TeamCoach.getCoachByName(coachName);
    }


    /**
     * Returns the league instance that matches the league name
     * @param leagueName the league name
     * @return the league instance that matches the league name
     */
    // Should be bound to LeagueControlller
    public League getLeagueDetails(String leagueName) {
        return League.getLeagueByName(leagueName);
    }


    /**
     * Returns the leagues instances from a certain season
     * @param year the season
     * @return the leagues instances from the season
     */
    // Should be bound to SeasonControlller
    public ArrayList<League> getSeasonDetails(int year) {
        return League.getAllLeaguesPerSeason(year);
    }


    /**
     * UC 2.5
     *
     * @param words
     */
    public void searchByKeyWord(String words) {

    }


    // ========================= Fan functions ============================
    // ====================================================================


    /**
     * UC 3.2
     * Adds a fan as a subscriber to the page
     * @param page the profile page
     * @param username the fan's username
     */
    // Should be bound to PagerControlller as Update
    public void addFanSubscriptionToPersonalPage(PersonalPage page, String username) {
        page.addSubscriber((Fan) User.getUserByID(username).getRoles().get(Role.FAN));
    }


    /**
     * UC 3.3
     * Adds a fan as a subscriber to a game
     * @param game the game
     * @param username the fan's username
     */
    // Should be bound to PagerControlller as Update
    public void addFanSubscriptionToGame(Game game, String username) {
        game.addFanToAlerts((Fan)User.getUserByID(username).getRoles().get(Role.FAN));
    }


    /**
     * UC 3.4
     * Sends a complaint to the system administrators
     * @param username the user's username
     * @param sysAdmins the list of system administrators
     * @param message the complaint
     */
    public void sendComplaintToSysAdmin(String username, ArrayList<SystemAdministrator> sysAdmins, AlertNotification message) {
        ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).sendComplaintToSysAdmin(sysAdmins, message);
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
        return ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).getSearchHistory();
    }

    /**
     * UC 3.6
     * Returns the info of a fan
     * @param username the fan's username
     * @return the fan's info
     */
    public String getFanProfileDetails(String username) {
        return User.getUserByID(username).getProfileDetails();
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
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
    }

    // ========================= Referee functions ============================
    // ====================================================================


    /**
     * UC 10.1
     * Returns the details about a referee
     * @param username the referee's username
     * @return the info about the referee
     */
    public String getRefereeDetails(String username) {
        return User.getUserByID(username).getProfileDetails() + "\n" + ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).getRefereeDetails();
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
    public void setRefereeProfileDetails(String username, String newPassword, String newName, String newMail, int qualification, RefereeType refereeType) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
        ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).setRefereeDetails(newMail, qualification, refereeType);
    }


    /**
     * UC 10.2
     * Returns a list of games that the referee referees at
     * @param username the referee's username
     * @return the list of games that the referee referees at
     */
    public ArrayList<Game> getRefereeGames(String username) {
        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
        return Game.getGamesByReferee(ref);
    }


    /**
     * UC 10.3
     * Adds an event that took place during a game to its events list
     * @param username the referee's username
     * @param game the match
     * @param gameEvent the event
     * @throws Exception in case the addition was unsuccessful
     */
    public void addGameEventToGame(String username, Game game, GameEvent gameEvent) throws Exception {
        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));

        // TODO: compare with id from DB
        if(isEqualGameInList(Game.getGamesByReferee(ref), game)){
            try {
                game.addEvent(gameEvent);
            }
            catch (Exception e){
                e.printStackTrace(); // not valid date exception
                // TODO: logger
            }
        }
        else {
            throw new Exception("This referee doesn't judge in this game");
        }
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
    public void changeGameEvent(String username, Game game, GameEvent gameEvent, String dateTimeStr, int gameMinutes, GameAlert eventName, String description) throws Exception {
        // TODO: check the referee is MAIN in UI
        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
        // TODO: compare with id from DB
        if(isEqualGameInList(Game.getGamesByReferee(ref), game)) {
            try {
                game.changeEvent(gameEvent, dateTimeStr, gameMinutes, eventName,  description);
            }
            catch (Exception e){
                e.printStackTrace();
                // TODO: logger
            }
        }
        else {
            throw new Exception("This referee doesn't judge in this game");
        }
    }

    /**
     * Checks if a game already exists in the referee's games list
     * @param games the list of games
     * @param game the checked game
     * @return true if the list contains the game, false if not
     */
    private boolean isEqualGameInList(ArrayList<Game> games, Game game){
    // TODO: change this function to ids from DB
    // Check if game in ArrayList of games
        for(Game refGame : games){
            if(game.isEqualGame(refGame)){
                return true;
            }
        }
        return false;
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
        return new League(leagueName);
    }


    /**
     * UC 9.2
     * Updates the season in the league
     * @param leagueName the league name
     * @param season the season
     * @return the updated league
     */
    public League updateSeasonForLeague(String leagueName, int season) {
        return League.getLeagueByName(leagueName).setSeason(season);
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
        this.register(username, password, name, mail).addRoleToUser(Role.REFEREE);
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

    //
    // =================== Team Owner functions ====================
    // =============================================================


    /**
     * UC 6.1
     * Adds a player to a team
     * @param team the team
     * @param userName the player's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addPlayer(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamPlayer player = (TeamPlayer)(User.getUserByID(userName).getRoles().get(Role.TEAM_PLAYER));
        if(player == null){
            throw new Exception("This user is not a player");
        }
        team.addPlayer(player);
    }

    /**
     * UC 6.1
     * Adds a coach to a team
     * @param team the team
     * @param userName the coach's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addCoach(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamCoach coach = (TeamCoach)(User.getUserByID(userName).getRoles().get(Role.COACH));
        if(coach == null){
            throw new Exception("This user is not a coach");
        }
        team.addCoach(coach);
    }

    /**
     * UC 6.1
     * Adds a field to a team
     * @param team the team
     * @param fieldName the field's name
     * @throws Exception if the addition was unsuccessful
     */
    public void addField(Team team, String fieldName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        team.addField(Field.getFieldByName(fieldName));
    }


    /**
     * UC 6.1
     * Removes a player from the team
     * @param team the team
     * @param userName the player's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removePlayer(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamPlayer player = (TeamPlayer)(User.getUserByID(userName).getRoles().get(Role.TEAM_PLAYER));
        if(player == null){
            throw new Exception("This user is not a player");
        }
        team.removePlayer(player);
    }


    /**
     * UC 6.1
     * Removes a coach from the team
     * @param team the team
     * @param userName the coach's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removeCoach(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamCoach coach = (TeamCoach)(User.getUserByID(userName).getRoles().get(Role.COACH));
        if(coach == null){
            throw new Exception("This user is not a coach");
        }
        team.removeCoach(coach);
    }

    /**
     * UC 6.1
     * Removes a field from the team
     * @param team the team
     * @param fieldName the field's name
     * @throws Exception if the removal was unsuccessful
     */
    public void removeField(Team team, String fieldName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        team.removeField(Field.getFieldByName(fieldName));
    }


    /**
     * UC 6.2
     * Adds a new owner to a team
     * @param team the team
     * @param userNameNewTeamOwner the username of the new owner
     * @param userNameTeamOwner the username of the owner that appoints the new one
     * @throws Exception if the appointment was unsuccessful
     */
    public void addOwner(Team team, String userNameNewTeamOwner, String userNameTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newOwnerUser = User.getUserByID(userNameNewTeamOwner);
        TeamOwner owner = ((TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER));
        newOwnerUser.getRoles().put(Role.TEAM_OWNER, new TeamOwner(userNameNewTeamOwner, newOwnerUser.getMail(), team, new HashSet<>()));
        owner.addToOwnerAppointments((TeamOwner) newOwnerUser.getRoles().get(Role.TEAM_OWNER));
        team.addOwner(ownerUser,newOwnerUser);
    }


    /**
     * UC 6.3
     * Removes an owner from the team.
     * This operation is possible only if the removed owner was appointed by the removing owner
     * @param team the team
     * @param userNameTeamOwner the owner that removes
     * @param userNameRemovedTeamOwner the owner that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeOwner(Team team, String userNameTeamOwner, String userNameRemovedTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedOwnerUser = User.getUserByID(userNameRemovedTeamOwner);
        TeamOwner owner = (TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER), removedOwner = (TeamOwner)removedOwnerUser.getRoles().get(Role.TEAM_OWNER);
        owner.removeFromOwnerAppointments(removedOwner);
        team.removeOwner(removedOwnerUser);
        removedOwnerUser.removeRoleFromUser(Role.TEAM_OWNER);
    }


    /**
     * UC 6.4
     * Adds a team manager to the team
     * @param team the team
     * @param userNameNewTeamManager the new manager
     * @param userNameTeamOwner the owner
     * @throws Exception if the addition was unsuccessful
     */
    public void addManager(Team team, String userNameNewTeamManager, String userNameTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newManagerUser = User.getUserByID(userNameNewTeamManager);
        TeamOwner owner = ((TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER));
        newManagerUser.getRoles().put(Role.TEAM_MANAGER,new TeamManager(userNameNewTeamManager, newManagerUser.getMail()));
        owner.addToManagerAppointments((TeamManager) newManagerUser.getRoles().get(Role.TEAM_MANAGER));
        team.addManager(ownerUser,newManagerUser);
    }

    /**
     * UC 6.5
     * Removes a manager from the team.
     * This operation is possible only if the removed manager was appointed by the removing owner
     * @param team the team
     * @param userNameTeamOwner the owner that removes
     * @param userNameRemovedTeamManager the manager that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeManager(Team team, String userNameRemovedTeamManager, String userNameTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedManagerUser = User.getUserByID(userNameRemovedTeamManager);
        TeamOwner owner = (TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER);
        TeamManager manager = (TeamManager)removedManagerUser.getRoles().get(Role.TEAM_MANAGER);
        owner.removeFromManagerAppointments(manager);
        team.removeManager(removedManagerUser);
        removedManagerUser.removeRoleFromUser(Role.TEAM_MANAGER);
    }


    /**
     * UC 6.6, 8.1
     * Closes a team
     * @param userName the username of the user that closes the team
     * @param team the team
     */
    public void closeTeam(String userName, Team team) {
        team.closeTeam(User.getUserByID(userName));
    }

    /**
     * UC 6.6
     * Reopens a team
     * @param team the team
     */
    public void openTeam(Team team) {
        team.openTeam();
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
        User.getUserByID(userName).closeUser();
    }


    /**
     * UC 8.3
     *
     */
    public void showComplain() {
        // UC 8.3A - show Complaint
    }


    /**
     * UC 8.3
     *
     */
    public void commentToComplaint() {
        // UC 8.3B - add comment to complaint
    }


    /**
     * UC 8.4
     *
     */
    public void showLogDocument() {
        // UC 8.4 - show log document
    }


    /**
     * UC 8.5
     *
     */
    public void startModelRecommendationSystem() {
        // UC 8.5 - start model of recommendation Systems
    }


    // =================================== General =======================================

    /**
     * Returns a team instance by its name
     * @param teamName the team's name
     * @return the team instance that matches the name
     */
    public Team getTeamByName(String teamName) {
        return Team.getTeamByName(teamName);
    }

    /**
     * Returns the list of roles of a certain user
     * @param userName the user's username
     * @return the user's list of roles
     * @throws Exception if the user doesn't exist
     */
    public HashMap<Role, Subscriber> getUserRoles(String userName) throws Exception {
        User user = User.getUserByID(userName);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        return user.getRoles();
    }


    public static void main(String[] args) {
        Controller c = new Controller();
        try {
            c.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * TEST function - SHOULD BE IMPLEMENTED IN UI
     */
    public User showLoginPanel() throws Exception {
        Scanner getInput = new Scanner(System.in);
        System.out.println("Please Login to system");
        System.out.println("Username: ");
        String userName = getInput.nextLine();
        System.out.println("Password: ");
        String password = getInput.nextLine();
        return this.login(userName, password);
    }


    /**
     *  TEST function - SHOULD BE IMPLEMENTED IN UI
     */
    public void runSystem() throws Exception {
        // Load test Data (Use mock)
        User connectedUser = showLoginPanel();
//        while (connectedUser.isConnected()) {
//            System.out.println("Please choose the role you wanna use now:");
//            HashMap<Role, Subscriber> roles = connectedUser.getRoles();
//            for(int i = 1; i <= roles.keySet().size(); i++){
//                System.out.println("[" + i + "] " + roles.keySet().toArray()[i-1]);
//            }
//
//            Role role = (Role) roles.keySet().toArray()[getInput.nextInt() - 1];
//            Subscriber sub = roles.get(role);
//
//        }
    }


    /**
     * UC 1.1
     * Initializes the system
     * @throws Exception if the initialization was unsuccessful
     */
    public void init() throws Exception {
        // TODO: Create DB if not exist -> Later
        this.connectToExternalSystems();
        HashMap<String, User> admin = User.getUsersByRole(Role.SYSTEM_ADMIN);
        if (admin == null) {
            User newUser = this.register("admin", "admin1234", "admin", "admin@ifa.com");
            newUser.addRoleToUser(Role.SYSTEM_ADMIN, new SystemAdministrator(newUser.getUserName(), newUser.getMail()));
        }
    }
}
