package service;

import domain.*;

import java.util.*;

public class Controller {

    private LinkedList<SystemEvent> systemEvents;
    private HashSet<League> leagues;

    /////////// Constructor ///////////
    public Controller() {
        systemEvents = new LinkedList<>();
    }


    //UC 1.1.1
    public void connectToExternalSystems() {
        //TODO: Connect to external system. if fails throws Exception
    }


    // ========================= System functions =========================
    // ====================================================================

    /**
     * @param userName
     * @param password
     * @return
     */
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

    public void logout(String userName) {
        User user = User.getUserByID(userName);
        user.disconnect();
    }

    // the idea is that in the UI part where will be two text boxes - one for username and one for password.
    // when the user presses 'register', the functions isValidUserName and isValidPassword are activated and if they both returned true
    // then register function is activated
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

    public ArrayList<PersonalPage> getPagesByUsername(String username) {
        return User.getUserByID(username).getPages();
    }


    // =================== Personal Pages functions =================
    // ==============================================================

    // U.C 4.2, 5.2
    public PersonalPage updateInfo(PersonalPage page, String info){
        return page.setInfo(info);
    }



    // =================== Team Player functions ====================
    // ==============================================================


    // UC 4.1 - update player's details
    public void updatePlayerDetails(String username, String playerName, Date birthDate, String position, String squadNumber) {
        User playerUser = User.getUserByID(username);
        if(playerName!=null){
            playerUser.setName(playerName);
        }
        ((TeamPlayer)User.getUserByID(username).getRoles().get(Role.TEAM_PLAYER)).updateDetails(birthDate,position,squadNumber);

    }


    // ======================= Coach functions ============================
    // ====================================================================

    // UC 5.1 - update coach's details
    public void updateCoachDetails(String username, String coachName, String qualification, String role) {
        User coachUser = User.getUserByID(username);
        if(coachName!=null){
            coachUser.setName(coachName);
        }
        ((TeamCoach)User.getUserByID(username).getRoles().get(Role.COACH)).updateDetails(qualification,role);
    }


    // ========================= Guest functions ============================
    // ====================================================================

    //UC 2.4
    //show Teams details
    public Team getTeamDetails(String teamName) {
        return Team.getTeamByName(teamName);
    }

    //show players details
    public TeamPlayer getPlayersDetails(String playerName) {
        return TeamPlayer.getPlayerByName(playerName);
    }

    //show coach details
    public TeamCoach getCoachDetails(String coachName) {
        return TeamCoach.getCoachByName(coachName);
    }

    //show league details
    public League getLeagueDetails(String leagueName) {
        return League.getLeagueByName(leagueName);
    }

    //show season details
    public ArrayList<League> getSeasonDetails(int year) {
        return League.getAllLeaguesPerSeason(year);
    }

    // UC 2.5
    //search by key word
    public void searchByKeyWord(String words) {

    }


    // ========================= Fan functions ============================
    // ====================================================================

    // UC 3.2 - add fan to subscription list of the personal page
    public void addFanSubscriptionToPersonalPage(PersonalPage page, String username) {
        page.addSubscriber((Fan) User.getUserByID(username).getRoles().get(Role.FAN));
    }

    // UC 3.3 - add fan to subscription list of the game
    public void addFanSubscriptionToGame(Game game, String username) {
        game.addFanToAlerts(User.getUserByID(username).getRoles().get(Role.FAN));
    }

    // UC 3.4 - send complaint (by fan) to System Administrator
    public void sendComplaintToSysAdmin(String username, ArrayList<SystemAdministrator> sysAdmins, AlertNotification message) {
        ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).sendComplaintToSysAdmin(sysAdmins, message);
    }

    // UC 3.5 - get history of fans searches
    // mock
    public String[] getFanHistory(String username) {
        //TODO - get from data base
        return ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).getSearchHistory();
    }

    // UC 3.6 - get and set fan info
    // get info
    public String getFanProfileDetails(String username) {
        return User.getUserByID(username).getProfileDetails();
    }

    // set info
    public void setFanProfileDetails(String username, String newPassword, String newName, String newMail) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
    }

    // ========================= Referee functions ============================
    // ====================================================================

    // UC 10.1 - get and set referee info (fields)
    // get info
    public String getRefereeDetails(String username) {
        return User.getUserByID(username).getProfileDetails() + "\n" + ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).getRefereeDetails();
    }

    // set info
    public void setRefereeProfileDetails(String username, String newPassword, String newName, String newMail, int qualification, RefereeType refereeType) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
        ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).setRefereeDetails(newMail, qualification, refereeType);
    }

    // UC 10.2 - get all games the referee judge
    public ArrayList<Game> getRefereeGames(String username) {
        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
        return Game.getGamesByReferee(ref);
    }

    // UC 10.3 - create new game event and add it to list of game events of the game
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

    // UC 10.4 - update/change game events by main referee
    // TODO: check the referee is MAIN in UI
    public void changeGameEvent(String username, Game game, GameEvent gameEvent, String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription) throws Exception {
        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
        // TODO: compare with id from DB
        if(isEqualGameInList(Game.getGamesByReferee(ref), game)) {
            try {
                game.changeEvent(gameEvent, dateTimeStr, gameMinutes, eventName,  subscription);
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

    // TODO: change this function to ids from DB
    // Check if game in ArrayList of games
    private boolean isEqualGameInList(ArrayList<Game> games, Game game){
        for(Game refGame : games){
            if(game.isEqualGame(refGame)){
                return true;
            }
        }

        return false;
    }


    // =================== Association Agent functions ====================
    // ====================================================================


    // UC 9.1
    public League setLeague(String leagueName) {
        return new League(leagueName);
    }

    // UC 9.2
    public League updateSeasonForLeague(String leagueName, int season) {
        return League.getLeagueByName(leagueName).setSeason(season);
    }

    // UC 9.3
    public void createReferee(String username, String password, String name, String mail) throws Exception {
        this.register(username, password, name, mail).addRoleToUser(Role.REFEREE);
        // TODO: Send invitation to referee
    }

    // UC 9.3
    public void removeReferee(String username) {
        User.getUserByID(username).removeRoleFromUser(Role.REFEREE);
    }

    // UC 9.4
    //This method will be shown after the user chose a referee from the list (using getReferees() method)
    public void addRefereeToLeaguePerSeason(League league, String userName) {
        league.addReferee((Referee) User.getUserByID(userName).getRoles().get(Role.REFEREE));
        Alert alert = new Alert();
        alert.addToMailSet(User.getUserByID(userName).getRoles().get(Role.REFEREE));
        alert.sendAlert(new AlertNotification("Invitation","MAZAL TOV! you are a referee!!"));
    }

    // UC 9.5
    public void setRankingMethod(int winP,int drawP,  int loseP, League league) {
        league.getRankingMethod().setRankingMethod(winP, loseP, drawP);
    }

    // UC 9.6
    public void setSchedulingMethod(League league, SchedulingMethod schedulingMethod) {
        league.setSchedulingMethod(schedulingMethod);
    }

    // UC 9.7
    // Click this button after you have all the teams in league, Automatic scheduling
    public void scheduleGamesInLeagues(League league) {
        league.scheduledGames();
    }

    // UC 9.8
    public void setRulesForBudgetControl() {

    }

    // UC 9.9
    public void setTeamBudget() {

    }


    //
    // =================== Team Owner functions ====================
    // =============================================================


    // UC 6.1A - add properties
    // 6.1A1
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

    // 6.1A2
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

    // 6.1A3
    public void addField(Team team, String fieldName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        team.addField(Field.getFieldByName(fieldName));
    }


    // UC 6.1B - remove properties
    // 6.1B1
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
    // 6.1B2
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

    // 6.1B3
    public void removeField(Team team, String fieldName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        team.removeField(Field.getFieldByName(fieldName));
    }




    // UC 6.2 - add owner to team and new owner to listAppointments
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


    // UC 6.3 - remove owner by owner
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

    // UC 6.4 - add team Manager
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

    // UC 6.5 - remove manager by owner
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


    // UC 6.6A - close team
    public void closeTeam(String userName, Team team) {
        team.closeTeam(User.getUserByID(userName));
    }

    // UC 6.6B - open team
    public void openTeam(Team team) {
        team.openTeam();
    }


    // UC 6.7 - manage finance
    public void manageFinance(){

    }


    // =================== Team Manager functions ====================
    // ====================================================================

    // UC 7.1 - set permissions to team manager
    //responsible of Team Owner!
    public void setPermissionsToManager() {
    }


    // =================== System Manager functions ====================
    // ====================================================================


    // UC 8.1 - close team --- UC 6.6A
    public void closeTeam(Team team) {
        //DONE -> look at UC 6.6A
    }


    // UC 8.2 - remove user from System
    public void removeUserFromSystem(String userName) {
        User.getUserByID(userName).closeUser();
    }


    // UC 8.3A - show Complaint
    public void showComplain() {

    }


    // UC 8.3B - add comment to complaint
    public void commentToComplaint() {

    }

    // UC 8.4 - show log document
    public void showLogDocument() {

    }


    // UC 8.5 - start model of recommendation Systems
    public void startModelRecommendationSystem() {

    }

        // ====================================================================

        public Team getTeamByName(String teamName) {
        return Team.getTeamByName(teamName);
    }

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

    // TEST function - SHOULD BE IMPLEMENTED IN UI
    public User showLoginPanel() throws Exception {
        Scanner getInput = new Scanner(System.in);
        System.out.println("Please Login to system");
        System.out.println("Username: ");
        String userName = getInput.nextLine();
        System.out.println("Password: ");
        String password = getInput.nextLine();
        return this.login(userName, password);
    }

    // TEST function - SHOULD BE IMPLEMENTED IN UI
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

    public void init() throws Exception {
        // Create DB if not exist -> Later
        this.connectToExternalSystems();
        HashMap<String, User> admin = User.getUsersByRole(Role.SYSTEM_ADMIN);
        if (admin == null) {
            User newUser = this.register("admin", "admin1234", "admin", "admin@ifa.com");
            newUser.addRoleToUser(Role.SYSTEM_ADMIN, new SystemAdministrator(newUser.getUserName(), newUser.getMail()));
        }
    }
}
