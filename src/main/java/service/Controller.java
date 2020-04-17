package service;

import domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Controller {

    //TODO think about best solution
    private Alert sysAdminsAlert;

    private LinkedList<SystemEvent> systemEvents;
    private HashSet<League> leagues;
    private HashMap<String, User> users;

    /////////// Constructor ///////////
    public Controller() {
        users = new HashMap<>();
        sysAdminsAlert = new Alert();
        systemEvents = new LinkedList<>();
    }


    //UC 1.1.1
    public void connectToExternalSystems() {
        //TODO: Connect to external system. if fails throws Exception
    }

    /**
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName, String password) {
        if (!users.containsKey(userName)) {
            return false;
        }

        User user = users.get(userName);
        if (!user.getPassword().equals(password)) {
            return false;
        }

        user.connect();
        return true;
    }

    public boolean logout(String userName, boolean isOut) {
        if (!isOut) {
            return false;
        }

        User user = users.get(userName);
        user.disconnect();
        return true;
    }

    // the idea is that in the UI part where will be two text boxes - one for username and one for password.
    // when the user presses 'register', the functions isValidUserName and isValidPassword are activated and if they both returned true
    // then register function is activated
    public User register(String userName, String password, String name, String mail) throws Exception {
        if (users.containsKey(userName)) {
            throw new Exception("User already exist");
        }

        User newUser = new User(userName, password, name, mail);
        newUser.addRoleToUser(Role.FAN, new Fan(newUser.getUserName(), newUser.getMail(), false));

        users.put(userName, newUser);
        //newUser.connect();
        return newUser;
    }


    public boolean isValidUserName(String userName) {
        return User.isValidUserName(userName);
    }

    public boolean isValidPassword(String password) {
        return User.isValidPassword(password);
    }

    public Team getTeamByName(String teamName){
        return Team.getTeamByName(teamName);
    }

    public HashMap<Role,Subscriber> getRolesPerUser(String userName){
        return User.getUserByID(userName).getRoles();
    }

    public User getUser(String userName){
        return User.getUserByID(userName);
    }




    // ----------------------------------------------- Team Owner Use Cases (6) ----------------------------------------------- //









    public static void main(String[] args) {
        Controller c = new Controller();
        try {
            c.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        // Create DB if not exist -> Later
        this.connectToExternalSystems();
        HashMap<String, User> admin = User.getUsersByRole(Role.SYSTEM_ADMIN);
        if (admin == null) {
            User newUser = this.register("admin", "admin1234", "admin", "admin@ifa.com");
            newUser.addRoleToUser(Role.SYSTEM_ADMIN, new SystemAdministrator(newUser.getUserName(), newUser.getMail(), true));
        }

        // Load test Data (Use mock)
    }
}
