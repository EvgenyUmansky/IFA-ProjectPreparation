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

    // U.C 4.1 5.1
    public PersonalPage updateInfo(PersonalPage page, String info){
        return page.setInfo(info);
    }

    // =================== Team Player functions ====================
    // ==============================================================


    // ATTENTION PLEASE: Naor, UC4.1 == UC 5.1 AND UC4.2 == UC5.2


    // UC 4.1 - update player's details
    public void updatePlayerDetails(String userName, String mail, Date birthDate, String position, String squadNumber) {

    }

    // UC 4.2 upload Content To Page
    public void uploadContentPlayerToPage() {

    }

    // ======================= Coach functions ============================
    // ====================================================================

    // UC 5.1 - update coach's details
    public void updateCoachDetails(String userName, String mail, String qualification, String role) {

    }

    // UC 5.2 upload Content To Page
    public void uploadContentToCoachPage() {

    }

    // ========================= Guess functions ============================
    // ====================================================================

    //UC 2.4
    //show Teams details
    public void showTeamDetails(Team team) {

    }

    //show players details
    public void showPlayersDetails(TeamPlayer player) {

    }

    //show coach details
    public void showCoachDetails(TeamCoach teamCoach) {

    }

    //show league details
    public void showLeagueDetails(League league) {

    }

    //show season details
    public void showSeasonDetails(League league, int year) {

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

    // 3.5 - get history of fans searches
    // mock
    public ArrayList<String> getFanHistory(String username) {
        //TODO - get from data base
        return new ArrayList<>();
    }

    // UC 3.6 - get and set fan info
    public String getFanProfileDetails(String username) {
        return User.getUserByID(username).getProfileDetails();
    }

    // for now it's only mail - iteration 2
    public void setFanProfileDetails(String username, String newPassword, String newName, String newMail) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
    }

    // ========================= Referee functions ============================
    // ====================================================================

    // UC 10.1 - get and set referee info (fields)
    public String getRefereeDetails(String username) {
        return ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).getRefereeDetails();
    }

    // Envelope function for setProfileDetails
    public void setRefereeProfileDetails(String username, String newMail, int qualification, RefereeType refereeType) {
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
        if(Game.getGamesByReferee(ref).contains(game)){
            game.addEvent(gameEvent);
        }else {
            throw new Exception("This referee doesn't judge in this game");
        }
    }

    //TODO: we may implement UC 10.4 within UC 10.3 - For Evegeny

/*    // UC 10.4 - update/change game events by main referee
    public boolean changeGameEvent(String username, Game game, GameEvent gameEvent, String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription) {
        return ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).changeGameEvent(game, gameEvent, dateTimeStr, gameMinutes, eventName, subscription);
    }*/


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
    public void addNewReferee(String username, String password, String name, String mail) throws Exception {
        this.register(username, password, name, mail).addRoleToUser(Role.REFEREE);
    }

    // 9.3
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
    public void setRankingMethod(int winP, int loseP, int drawP, League league) {
        league.getRankingMethod().setWinPoints(winP).setLoosPoints(loseP).setDrawPoints(drawP);
    }

    // UC 9.6
    public void setSchedulingMethod(League league, SchedulingMethod schedulingMethod) {
        league.setSchedulingMethod(schedulingMethod);
    }

    // UC 9.7
    // Click this button after you have all the teams in league, Automatic scheduling
    public void sceduleGamesInLeagues(League league) {
        Team[] teams = league.getTeamsInLeaguePerSeason().keySet().toArray(new Team[league.getTeamsInLeaguePerSeason().size()]);
        league.getSchedulingMethod().scheduleGamePolicy(league, teams);
    }

    // UC 9.8
    public void setRulesForBudgetControl() {

    }

    // UC 9.9
    public void setTeamBudget() {

    }


    // =================== Team Owner functions ====================
    // =============================================================


    //6.1A - add property
    public void addProperty(Team team,Object property) {
        team.addProperty(property);
    }


    //6.1B - remove property

    public void removeProperty(Team team,Object property) {
        team.removeProperty(property);
    }

    //6.2 - add owner to team and new owner to listAppointments
    public void addOwner(Team team, String userNameNewTeamOwner, String userNameTeamOwner){
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Error("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newOwnerUser = User.getUserByID(userNameNewTeamOwner);
        TeamOwner owner = ((TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER));
        newOwnerUser.getRoles().put(Role.TEAM_OWNER,new TeamOwner(userNameNewTeamOwner, newOwnerUser.getMail(), team, new HashSet<>()));
        owner.addToOwnerAppointments((TeamOwner) newOwnerUser.getRoles().get(Role.TEAM_OWNER));
        team.addOwner(ownerUser,newOwnerUser);
    }


    //6.3 - remove owner by owner
    public void removeOwner(Team team, String userNameTeamOwner, String userNameRemovedTeamOwner){
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Error("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedOwnerUser = User.getUserByID(userNameRemovedTeamOwner);
        TeamOwner owner = (TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER), removedOwner = (TeamOwner)removedOwnerUser.getRoles().get(Role.TEAM_OWNER);
        owner.removeFromOwnerAppointments(removedOwner);
        team.removeOwner(removedOwnerUser);
    }

    //6.4 - add team Manager
    public void addManager(Team team, String userNameNewTeamManager, String userNameTeamOwner){
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Error("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newManagerUser = User.getUserByID(userNameNewTeamManager);
        TeamOwner owner = ((TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER));
        newManagerUser.getRoles().put(Role.TEAM_MANAGER,new TeamManager(userNameNewTeamManager, newManagerUser.getMail()));
        owner.addToManagerAppointments((TeamManager) newManagerUser.getRoles().get(Role.TEAM_MANAGER));
        team.addManager(ownerUser,newManagerUser);
    }

    //6.5 - remove manager by owner
    public void removeManager(Team team, String userNameRemovedTeamManager, String userNameTeamOwner){
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Error("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedManagerUser = User.getUserByID(userNameRemovedTeamManager);
        TeamOwner owner = (TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER);
        TeamManager manager = (TeamManager)removedManagerUser.getRoles().get(Role.TEAM_MANAGER);
        owner.removeFromManagerAppointments(manager);
        team.removeManager(removedManagerUser);
    }


    //UC6.6A - close team
    public void closeTeam(String userName, Team team) {
        team.closeTeam(User.getUserByID(userName));
    }

    //UC6.6B - open team
    public void openTeam(Team team) {
        team.openTeam();
    }


    //UC6.7 - manage finance
    public void manageFinance(){

    }


    // =================== Team Manager functions ====================
    // ====================================================================

    //UC7.1 - set permissions to team manager
    //responsible of Team Owner!
    public void setPermissionsToManager() {

    }


    // =================== System Manager functions ====================
    // ====================================================================


    //UC8.1 - close team
    public void closeTeam(Team team) {

    }


    //UC8.2 - remove user from System
    public void removeUserFromSystem(User user) {

    }


    //UC8.3A - show Complaint
    public void showComplain() {

    }


    //UC8.3B - add comment to complaint
    public void commentToComplaint() {

    }

    //UC8.4 - show log document
    public void showLogDocument() {

    }


    //UC8.5 - start model of recommendation Systems
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
