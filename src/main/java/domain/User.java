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
    private boolean isClosed;

    // ========================= Constructor ==========================

    public User(String userName, String password, String name, String mail) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.mail = mail;
        this.roles = new HashMap<>();
        addRoleToUser(Role.FAN);
        isClosed = false;
    }

    // ========================= Getters ==========================


    public boolean isClosed() {
        return isClosed;
    }

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

    public ArrayList<PersonalPage> getPages() {
        String username = this.getUserName();
        String mail = this.getMail();
        // TODO: Get pages from DB (all pages that the user had permission to edit them)
        return new ArrayList<PersonalPage>() {{
            add(new PlayerPage(new TeamPlayer(username, mail), null));
        }};
    }

    // ========================= Setters ==========================


    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public User setPassword(String password) {
        if (password != null)
            this.password = password;
        return this;
    }

    public User setName(String name) {
        if (name != null)
            this.name = name;
        return this;
    }

    public User setRoles(HashMap<Role, Subscriber> roles) {
        if (roles != null)
            this.roles = roles;
        return this;
    }

    private User setMail(String newMail) {
        if (newMail != null) {
            this.mail = newMail;
        }
        return this;
    }


    // ====================== Edit Profile ======================
    // ==========================================================
    // UC 3.6
    public String getProfileDetails() {
        // TODO: Get subscriber extra details if exist
        return "User Name: " + this.getUserName() + "\n" +
                "Password: " + this.getPassword() + "\n" +
                "Name: " + this.getName() + "\n" +
                "Mail: " + this.getMail();
    }

    // UC 3.6, UC 10.1
    public User setProfileDetails(String newPassword, String newName, String newMail) {
        this.setPassword(newPassword);
        this.setName(newName);
        this.setMail(newMail);
        return this;
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

    public User removeRoleFromUser(Role role) {
        roles.remove(role);
        return this;
    }


    public static HashMap<String, User> getUsersByRole(Role role) {
        // TODO: Get users from DB
        HashMap<String, User> usersByRole = new HashMap<>();
        usersByRole.put("Admin", new User("Admin", "Admin1234", "Admin", "Admin@ifa.com").addRoleToUser(role));
        return usersByRole;
    }

    public static User getUserByID(String userName) {
        // TODO: Get user from DB
        return new User(userName, "1234", null, null).addRoleToUser(Role.TEAM_OWNER).addRoleToUser(Role.ASSOCIATION_AGENT).addRoleToUser(Role.REFEREE);
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
}
