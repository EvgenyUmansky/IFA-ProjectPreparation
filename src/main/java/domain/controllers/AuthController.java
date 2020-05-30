package domain.controllers;

import DataAccess.*;
import ExternalSystemsAccess.*;
import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import service.pojos.UserDTO;

import java.util.*;

/**
 * This class is the controller in the system - it receives calls from the UI and activates the functionality in each class in the domain layer.
 */
public class AuthController {
    static Logger logger = Logger.getLogger(AuthController.class.getName());

    private LinkedList<SystemEvent> systemEvents;
    private HashSet<League> leagues;
    private DBAccess<User> uda = UserDBAccess.getInstance();
    private DBAccess< Pair<String,ArrayList<String>> > urda = UserRolesDBAccess.getInstance();
    private DBAccess<Pair<String, ArrayList<Notification>>> ada = AlertDBAccess.getInstance();
    private RefereeGamesDBAccess rgda = RefereeGamesDBAccess.getInstance();
    private FanGamesDBAccess fgda = FanGamesDBAccess.getInstance();

    private TaxSystemAccess taxSystem;
    private AccountingSystemAccess accountingSystem;


    // ========================= Constructor =========================

    /**
     * Constructor
     */
    public AuthController() {
        systemEvents = new LinkedList<>();
    }


    // ========================= System functions ========================
    // ====================================================================

    /**
     * UC 1.1.1
     * Connects to external systems
     */
    public void connectToExternalSystems() {
        taxSystem = new TaxProxy();
        accountingSystem = new AccountingProxy();
    }


    /**
     * UC 2.3
     * Connects a user into the system
     * @param userName the user's username
     * @param password the user's password
     * @return the user's instance
     */
    public UserDTO login(String userName, String password) throws Exception {
        User user = uda.select(userName);

        if (user == null) {
            logger.info(userName + ": User not found!");
            throw new Exception(userName + ": User not found!");
        }

        if (!user.getPassword().equals(hash(password))) {
            logger.info(userName + ": Wrong password!");
            throw new Exception(userName + ": Wrong password!");
        }

        user.connect();

    /////////// Roles ///////////
        ArrayList<String> rolesAsStrings = urda.select(userName).getValue();

    /////////// Notifications ///////////
        ArrayList<String> notifications = getNotSeenNotifications(userName);


    /////////// Games ///////////
        ArrayList<String> gameIds = new ArrayList<>();


        ArrayList<Game> games = new ArrayList<>(fgda.select(userName).getValue());


        if(rolesAsStrings.contains("REFEREE")){
            games.addAll(rgda.select(userName).getValue());
        }

        for(Game game : games){
            gameIds.add(String.valueOf(game.getId()));
        }

        // add notifications to UserDTO
        // add gameIds to UserDTO
        logger.info(userName + " login to the system");
        return new UserDTO(user.getUserName(), user.getName(), rolesAsStrings.toArray(new String[0]), user.getMail(), notifications.toArray(new String[0]), gameIds.toArray(new String[0]));
    }

    /**
     * get all not seen notifications of a user and update them as seen in DB
     *
     * @param userName - user name of user
     * @return array list with all not seen notifications of the user
     */
    private ArrayList<String> getNotSeenNotifications(String userName){
        ArrayList<Notification> notifications = ada.select(userName).getValue();
        ArrayList<Notification> seenNotifications = new ArrayList<>(); // notifications marked seen
        ArrayList<String> notificationsString = new ArrayList<>();

        for(Notification notification : notifications){
            if(!notification.isSeen()) {
                notificationsString.add(notification.getSubject());
                notification.setSeen(true); // mark the notification as seen
                seenNotifications.add(notification);
            }
        }

        ada.update(new Pair<>(userName, seenNotifications)); // update not seen notification as seen

        return notificationsString;
    }

    /**
     * UC 3.1
     * Disconnects a user from the system
     * @param userName the user's username
     */
    public void logout(String userName) {
        User user = User.getUserByID(userName);
        user.disconnect();
        logger.info(userName + " logout from the system");
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
        User user = User.getUserByID(userName);
        if (user != null) {
            logger.info(userName + " already exists in the system");
            throw new Exception(userName + " already exists");
        }

        if (!User.isValidUserName(userName)) {
            logger.info(userName + " is not valid");
            throw new Exception(userName + " is not valid");
        }
        if (!User.isValidPassword(password)) {
            logger.info("The password is not valid");
            throw new Exception("The password is not valid");
        }

        User newUser = new User(userName, password, name, mail);
        newUser.addRoleToUser(Role.FAN, new Fan(newUser.getUserName(), newUser.getMail()));

        logger.info(userName + " registers to the system");
        return newUser;
    }

    // =================================== General =======================================


    /**
     * Returns the list of roles of a certain user
     * @param userName the user's username
     * @return the user's list of roles
     * @throws Exception if the user doesn't exist
     */
    public HashMap<Role, Subscriber> getUserRoles(String userName) throws Exception {
        //TODO: run over all the DBACCESS objects and activate the select function to get the user's roles
        return null;
    }


    public static void main(String[] args) {
        AuthController c = new AuthController();
        try {
            c.init();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * TEST function - SHOULD BE IMPLEMENTED IN UI
     */
//    public UserDTO showLoginPanel() throws Exception {
//        Scanner getInput = new Scanner(System.in);
//        System.out.println("Please Login to system");
//        System.out.println("Username: ");
//        String userName = getInput.nextLine();
//        System.out.println("Password: ");
//        String password = getInput.nextLine();
//        return this.login(userName, password);
//    }


    /**
     *  TEST function - SHOULD BE IMPLEMENTED IN UI
     */
    public void runSystem() throws Exception {
        // Load test Data (Use mock)
//        User connectedUser = showLoginPanel();
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

    /**
     * Encrypts the user's password
     * @param password the original password
     * @return
     */
    private String hash(String password) {
        int hash = 7;
        for (int i = 0; i < password.length(); i++) {
            hash = hash * 31 + password.charAt(i);
        }
        return Integer.toString(hash);
    }

}
