package service.pojos;

public class UserDTO {
    private final String username;
    private final String name;
    private final String[] roles;
    private final String mail;

    public UserDTO(String username, String name, String[] roles, String mail){
        this.username = username;
        this.name = name;
        this.roles = roles;
        this.mail = mail;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String[] getRoles() {
        return roles;
    }
}
