package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private boolean connected;
    private String userName;
    private String password;
    private String name;
    private HashMap<String,Subscriber> roles;


// Constructor


    public User(boolean connected, String userName, String password, String name, String mail) {
        this.connected = connected;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.roles = new HashMap<>();
    }

    public boolean addRoleToUser(String role,Subscriber subscriber){
        roles.put(role,subscriber);
        return true;
    }
    public boolean removeRoleFromUser(String role){
        roles.remove(role);
        return true;
    }



    public void setRoles(HashMap<String, Subscriber> roles) {
        this.roles = roles;
    }

    public HashMap<String, Subscriber> getRoles() {
        return roles;
    }

    public static boolean isValidUserName(String userName) {
        int userNameLength = userName.length();
        if(userNameLength < 2 || userNameLength > 10)
            return false;

        for(int i=0; i<userNameLength; i++){
            char currentLetter = userName.charAt(i);
            if(!Character.isLetter(currentLetter))
                return false;
        }

        return true;
    }


    public static boolean isValidPassword(String password){
        int passwordLength = password.length();
        if(passwordLength < 6 || passwordLength > 15){
            return false;
        }

        for(int i=0; i<passwordLength; i++){
            char currentLetter = password.charAt(i);
            if(!Character.isLetter(currentLetter) && !Character.isDigit(currentLetter))
                return false;
        }

        return true;
    }



    public boolean isConnected(){
        return connected;
    }

    public void connect(){
        connected = true;
    }

    public void disconnect(){
        connected = false;
    }


    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
