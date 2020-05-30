package service.pojos;

public class UserDTO {
    private final String username;
    private final String name;
    private final String[] roles;
    private final String mail;
    private final String[] notifications;
    private final String[] games;

    public UserDTO(String username, String name, String[] roles, String mail, String[] notifications, String[] games) {
        this.username = username;
        this.name = name;
        this.roles = roles;
        this.mail = mail;
        this.notifications = notifications;
        this.games = games;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String[] getRoles() {
        return roles;
    }

    public String getMail() {
        return mail;
    }

    public String[] getNotifications() {
        return notifications;
    }

    public String[] getGames() {
        return games;
    }
}
