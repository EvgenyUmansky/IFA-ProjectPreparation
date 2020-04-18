package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private boolean connected;
    private String userName;
    private String password;
    private String name;
    private HashMap<Role, Subscriber> roles;
    private String mail;

    // ========================= Constructor ==========================

    public User(String userName, String password, String name, String mail) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.mail = mail;
        this.roles = new HashMap<>();
        addRoleToUser(Role.FAN);
    }

    // ========================= Getters ==========================

    public String getMail() {
        return this.mail;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public HashMap<Role, Subscriber> getRoles() {
        return roles;
    }

    // ========================= Setters ==========================

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(HashMap<Role, Subscriber> roles) {
        this.roles = roles;
    }


    // ====================== Edit Profile ======================
    // ==========================================================
    // UC 3.6
    private String getProfileDetails(User user, Subscriber subscriber) {

        return "User Name: " + user.getUserName() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "Name: " + user.getName() + "\n" +
                "Mail: " + subscriber.getMail();
    }

    // UC 3.6, UC 10.1
    private boolean setProfileDetails(User user, Subscriber subscriber, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {

        if (!newUserName.isEmpty()) {
            user.setUserName(newUserName);
        } else {
            System.out.println("The new user name is empty. The user name is not changed");
        }

        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        } else {
            System.out.println("The new password is empty. The password is not changed");
        }

        // set name
        if (!newName.isEmpty()) {
            user.setName(newName);
        } else {
            System.out.println("The new name is empty. The name is not changed");
        }
        // set mail

        if (isEmptyMail) {
            subscriber.setMail("");
        } else if (!newMail.isEmpty()) {
            subscriber.setMail(newMail);
        } else {
            System.out.println("The mail is not changed");
        }

        return true;
    }


    public User addRoleToUser(Role role, Subscriber subscriber) {
        roles.put(role, subscriber);
        return this;
    }

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

    public boolean removeRoleFromUser(Role role) {
        roles.remove(role);
        return true;
    }


    public static HashMap<String, User> getUsersByRole(Role role) {
        // TODO: Get users from DB
        HashMap<String, User> usersByRole = new HashMap<>();
        usersByRole.put("Admin", new User("Admin", "Admin1234", "Admin", "Admin@ifa.com").addRoleToUser(role));
        return usersByRole;
    }

    public static User getUserByID(String userName) {
        // TODO: Get user from DB
        return new User(userName, "1234", null, null).addRoleToUser(Role.TEAM_OWNER).addRoleToUser(Role.ASSOCIATION_AGENT);
    }






    public static boolean isValidUserName(String userName) {
        int userNameLength = userName.length();
        if (userNameLength < 2 || userNameLength > 10)
            return false;

        for (int i = 0; i < userNameLength; i++) {
            char currentLetter = userName.charAt(i);
            if (!Character.isLetter(currentLetter))
                return false;
        }

        return true;
    }


    public static boolean isValidPassword(String password) {
        int passwordLength = password.length();
        if (passwordLength < 6 || passwordLength > 15) {
            return false;
        }

        for (int i = 0; i < passwordLength; i++) {
            char currentLetter = password.charAt(i);
            if (!Character.isLetter(currentLetter) && !Character.isDigit(currentLetter))
                return false;
        }

        return true;
    }


    public boolean isConnected() {
        return connected;
    }

    public void connect() {
        connected = true;
    }

    public void disconnect() {
        connected = false;
    }






}
