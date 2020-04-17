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


    /////////// Use Case 3 - Fan ///////////
// UC 3.4 - send complaint (by fan) to System Administrator
    public boolean sendAlertToSysAdmin(String userName, AlertNotification message) {
//        Subscriber fan = (Subscriber) (users.get(userName));
//        if (!(fan instanceof Fan)) {
        User fanUser = users.get(userName);
        if (!fanUser.getRoles().containsKey(Role.FAN)) {
            System.out.println("Not fan instance");
            return false;
        }

        sysAdminsAlert.sendAlert(message);

        return true;
    }

    // ----------------------------------------------- Team Owner Use Cases (6) ----------------------------------------------- //



    //UC 6.3
    // TODO: 15/04/2020 recursive removing?
    public boolean removeTeamOwner(Subscriber owner, Subscriber ownerToRemove) {
        if (owner instanceof TeamOwner == true) {
            TeamOwner teamOwner = (TeamOwner) owner;
            if (users.containsKey(ownerToRemove.getUserName())) {
                User userRemoveOwner = users.get(ownerToRemove.getUserName());
                if (userRemoveOwner.getRoles().containsKey(Role.TEAM_OWNER)) {
                    userRemoveOwner.getRoles().remove(Role.TEAM_OWNER);
                    return true;
                }
            }
        }
        return false;
    }

    //UC 6.2
    // TODO: 15/04/2020 manage list of added owners for removing
    public boolean addTeamOwner(Subscriber owner, Subscriber secondOwner) {
        if (owner instanceof TeamOwner) {
            TeamOwner teamOwner = (TeamOwner) owner;
            if (users.containsKey(secondOwner.getUserName())) {
                User userNewOwner = users.get(secondOwner.getUserName());
                Subscriber newSubsOwner = new TeamOwner(secondOwner.getUserName(), secondOwner.getMail(), false, teamOwner.getTeam(), teamOwner.getManagerAppointments());
                userNewOwner.getRoles().put(Role.TEAM_OWNER, newSubsOwner);
                return true;
            }
        }
        return false;
    }


    //UC 6.4

    // TODO: 15/04/2020 manage list of added managers for removing
    public boolean addTeamManager(Subscriber owner, Subscriber newManager) {
        if (owner instanceof TeamOwner == true) {
            TeamOwner teamOwner = (TeamOwner) owner;
            if (users.containsKey(newManager.getUserName())) {
                User userNewManager = users.get(newManager.getUserName());
                Subscriber newSubManager = new TeamManager(newManager.getUserName(), newManager.getMail(), false);
                userNewManager.getRoles().put(Role.TEAM_MANAGER, newSubManager);
                return true;
            }
        }
        return false;
    }

    //UC 6.5
    // TODO: 15/04/2020 recursive removing?
    public boolean removeTeamManager(Subscriber owner, Subscriber managerToRemove) {
        if (owner instanceof TeamOwner) {
            TeamOwner teamOwner = (TeamOwner) owner;
            if (users.containsKey(managerToRemove.getUserName())) {
                User userRemoveManager = users.get(managerToRemove.getUserName());
                if (userRemoveManager.getRoles().containsKey(Role.TEAM_MANAGER)) {
                    userRemoveManager.getRoles().remove(Role.TEAM_MANAGER);
                    return true;
                }
            }
        }
        return false;
    }


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
