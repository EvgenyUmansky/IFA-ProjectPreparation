package domain.controllers;

import domain.*;

import java.util.*;

/**
 * This class is the controller in the system - it receives calls from the UI and activates the functionality in each class in the domain layer.
 */
public class AuthController {

    private LinkedList<SystemEvent> systemEvents;
    private HashSet<League> leagues;


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
        // TODO: Connect to external system. if fails throws Exception
    }


    /**
     * UC 2.3
     * Connects a user into the system
     * @param userName the user's username
     * @param password the user's password
     * @return the user's instance
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

    /**
     * UC 3.1
     * Disconnects a user from the system
     * @param userName the user's username
     */
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

    // =================================== General =======================================


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
        AuthController c = new AuthController();
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
