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


    // ========================= Fan functions ============================
    // ====================================================================

    // UC 3.2 - add fan to subscription list of the personal page
    public void addFanSubscriptionToPersonalPage(PersonalPage page, String username) {
        page.addSubscriber((Fan)User.getUserByID(username).getRoles().get(Role.FAN));
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
    public ArrayList<String> getFanHistory(String username){
        //TODO - get from data base
        return new ArrayList<>();
    }

    // UC 3.6 - get and set fan info
    public String getFanProfileDetails(String username) {
        return ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).getFanDetails();
    }

    // for now it's only mail - iteration 2
    public void setFanProfileDetails(String username, String newMail) {
        ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).setFanDetails(newMail);
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
    public ArrayList<Game> getRefereeGames(String username, ArrayList<Game> allGames) {
        return ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).getRefereeGames(allGames);
    }

    // UC 10.3 - create new game event and add it to list of game events of the game
    public void addGameEventToGame(String username, Game game, GameEvent gameEvent) {
        ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).addGameEventToGame(game, gameEvent);
    }

    // UC 10.4 - update/change game events by main referee
    public boolean changeGameEvent(String username, Game game, GameEvent gameEvent, String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription){
        return ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).changeGameEvent(game, gameEvent, dateTimeStr, gameMinutes, eventName, subscription);
    }


    // =================== Association Agent functions ====================
    // ====================================================================


    // 9.1
    public League setLeague(String leagueName){
        return new League(leagueName);
    }

    // 9.2
    public League updateSeasonForLeague(String leagueName, int season){
        return League.getLeagueByName(leagueName).setSeason(season);
    }

    // 9.3
    public void addNewReferee(String username, String password, String name, String mail) throws Exception {
        this.register(username, password, name, mail).addRoleToUser(Role.REFEREE);
        // TODO: Send invitation to referee
    }

    // 9.3
    public void removeReferee(String username){
        User.getUserByID(username).removeRoleFromUser(Role.REFEREE);
    }

    // ====================================================================
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
