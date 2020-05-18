package domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a user in the system.
 * A user can be of several types, but defined as a Fan by default. A user that has no type is considered as guest.
 */
public class User {

    private boolean connected;
    private String userName;
    private String password;
    private String name;
    private HashMap<Role, Subscriber> roles;
    private String mail;
    private boolean isClosed;

    // ========================= Constructor ==========================

    /**
     * Constructor
     * @param userName the user's username (id)
     * @param password the user's password
     * @param name the user's name
     * @param mail the user's mail
     */
    public User(String userName, String password, String name, String mail) {
        this.userName = userName;
        this.password = hash(password);
        this.name = name;
        this.mail = mail;
        this.roles = new HashMap<>();
        addRoleToUser(Role.FAN);
        isClosed = false;
    }


    /**
     *
     * @param userName
     * @param password
     * @param name
     * @param mail
     * @param isClosed
     */
    public User(String userName, String password, String name, String mail, boolean isClosed){
        this.userName = userName;
        this.password = hash(password);
        this.name = name;
        this.mail = mail;
        this.isClosed = isClosed;
        this.roles = new HashMap<>();
    }

    // ========================= Getters and Setters ==========================


    /**
     * Identifies if the user is deactivated
     * @return true if the user is deactivated, false if activated
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Returns the user's mail
     * @return the user's mail
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * Returns the user's name
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user's password
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's username
     * @return the user's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the types of users the user can be defined as (for the list of types see Role enum)
     * @return the types of users the user can be defined as
     */
    public HashMap<Role, Subscriber> getRoles() {
        return roles;
    }


    /**
     * Activate or deactivate the user
     * @param closed indicated the activation or deactivation
     */
    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    /**
     * Updates the user's password
     * @param password the user's password
     * @return the updated user
     */
    public User setPassword(String password) {
        if (password != null)
            this.password = password;
        return this;
    }

    /**
     * Updates the user's name
     * @param name the user's name
     * @return the updated user
     */
    public User setName(String name) {
        if (name != null)
            this.name = name;
        return this;
    }

    /**
     * Updates the user's list of roles in the system
     * @param roles the user's list of roles in the system
     * @return the updated user
     */
    public User setRoles(HashMap<Role, Subscriber> roles) {
        if (roles != null)
            this.roles = roles;
        return this;
    }

    /**
     * Updates the user's mail
     * @param newMail the user's mail
     * @return the updated user
     */
    private User setMail(String newMail) {
        if (newMail != null) {
            this.mail = newMail;
        }
        return this;
    }


    /**
     * Indicates whether the user is currently connected to the system
     * @return true if the user is connected, false otherwise
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Sets the user as connected to the system
     */
    public void connect() {
        connected = true;
    }

    /**
     * Sets the user as disconnected from the system
     */
    public void disconnect() {
        connected = false;
    }

    // ====================== Edit Profile ======================
    // ==========================================================


    /**
     * UC 3.6
     * Returns a String with the user's details
     * @return a String with the user's details
     */
    public String getProfileDetails() {
        // TODO: Get subscriber extra details if exist
        return "User Name: " + this.getUserName() + "\n" +
                "Password: " + this.getPassword() + "\n" +
                "Name: " + this.getName() + "\n" +
                "Mail: " + this.getMail();
    }

    /**
     * UC 3.6, 10.1
     * Updates the user's details
     * @param newPassword new password
     * @param newName new name
     * @param newMail new mail
     * @return the updated user
     */
    public User setProfileDetails(String newPassword, String newName, String newMail) {
        this.setPassword(newPassword);
        this.setName(newName);
        this.setMail(newMail);
        return this;
    }


    /**
     * Adds a role to the user
     * @param role the added role
     * @param subscriber the instance of the subscriber that matches the role and the user
     * @return the updated user
     */
    public User addRoleToUser(Role role, Subscriber subscriber) {
        roles.put(role, subscriber);
        return this;
    }

    /**
     * Adds a role to the user
     * @param role the added role
     * @return the updated user
     */
    public User addRoleToUser(Role role) {
        switch (role) {
            case SYSTEM_ADMIN:
                return addRoleToUser(role, new SystemAdministrator(userName, mail));
            case ASSOCIATION_AGENT:
                return addRoleToUser(role, new AssociationAgent(userName, mail));
            case TEAM_PLAYER:
                return addRoleToUser(role, new TeamPlayer(userName, mail));
            case COACH:
                return addRoleToUser(role, new TeamCoach(userName, mail));
            case TEAM_MANAGER:
                return addRoleToUser(role, new TeamManager(userName, mail));
            case TEAM_OWNER:
                return addRoleToUser(role, new TeamOwner(userName, mail));
            case REFEREE:
                return addRoleToUser(role, new Referee(userName, mail));
            case FAN:
                return addRoleToUser(role, new Fan(userName, mail));
        }
        return this;
    }

    /**
     * Removes a role from a user's roles list
     * @param role the removed role
     * @return the updated user
     */
    public User removeRoleFromUser(Role role) {
        roles.remove(role);
        return this;
    }

    /**
     * Deactivates the user
     * @return true if the deactivation was successful, false otherwise
     */
    public boolean closeUser() {
        if(this.getRoles().containsKey(Role.SYSTEM_ADMIN) && User.getUsersByRole(Role.SYSTEM_ADMIN).size() == 1){
            //  alert.sendAlert(new AlertNotification("Cant remove System Administer Account","System needs at least one system administer"));
            return false;
        }
        else if (!this.isClosed()) {
            this.setClosed(true);
//            alert.sendAlert(new AlertNotification("close user permanently", "you user close permanently"));
            return true;
        }
        return false;
    }

    // ====================== DB Access Functions ======================
    // ==========================================================


    /**
     * Returns the list of the profile pages of players, coaches and teams that the user has permission to edit
     * @return the list of the profile pages of players, coaches and teams that the user has permission to edit
     */
    public ArrayList<PersonalPage> getPages() {
        String username = this.getUserName();
        String mail = this.getMail();
        // TODO: Get pages from DB (all pages that the user had permission to edit them)
        return new ArrayList<PersonalPage>() {{
            add(new PlayerPage(new TeamPlayer(username, mail), "playerPage"));
        }};
    }

    /**
     * Returns a list of all the users that are defined as a certain role in the system
     * @param role the given role
     * @return a list of all the users that are defined as the given role in the system
     */
    public static HashMap<String, User> getUsersByRole(Role role) {
        // TODO: Get users from DB
        HashMap<String, User> usersByRole = new HashMap<>();
        usersByRole.put("Admin", new User("Admin", "Admin1234", "Admin", "Admin@ifa.com").addRoleToUser(role));
        return usersByRole;
    }

    /**
     * Returns the object of a user that matches the given username
     * @param userName the given username
     * @return the user that matches the username
     */
    public static User getUserByID(String userName) {
        // TODO: Get user from DB
        return new User(userName, "1234", null, "abc@gmail.com").addRoleToUser(Role.TEAM_OWNER).addRoleToUser(Role.ASSOCIATION_AGENT).addRoleToUser(Role.REFEREE).addRoleToUser(Role.TEAM_PLAYER).addRoleToUser(Role.COACH).addRoleToUser(Role.TEAM_MANAGER);
    }


    /**
     * Checks if a given username is legal
     * @param userName the username
     * @return true if the username is legal, false otherwise
     */
    public static boolean isValidUserName(String userName) {
        int userNameLength = userName.length();
        if (userNameLength < 2 || userNameLength > 10)
            return false;

        boolean containsLetter = false;

        for (int i = 0; i < userNameLength; i++) {
            char currentLetter = userName.charAt(i);
            if (!Character.isLetter(currentLetter) && !Character.isDigit(currentLetter))
                return false;
            if(Character.isLetter(currentLetter))
                containsLetter = true;
        }

        if(!containsLetter)
            return false;

        return true;
    }


    /**
     * Checks if a given password is legal
     * @param password the password
     * @return true if the password is legal, false otherwise
     */
    public static boolean isValidPassword(String password) {
        int passwordLength = password.length();
        if (passwordLength < 4 || passwordLength > 15) {
            return false;
        }

        for (int i = 0; i < passwordLength; i++) {
            char currentLetter = password.charAt(i);
            if (!Character.isLetter(currentLetter) && !Character.isDigit(currentLetter))
                return false;
        }

        return true;
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
