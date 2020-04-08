package domain;


import java.util.ArrayList;

public abstract class Subscriber extends User {

    private boolean connected;
    private String userName;
    private String password;
    private String name;
    private String mail;

    private ArrayList<String> alertsMessages;

/////////// Constructor ///////////

    // With mail
    public Subscriber(String userName, String password, String name, String mail){
        connected = false;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.alertsMessages = new ArrayList<>();
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

    // when the function activated - send message to screen
    public String addAlertMessage(String message){
        this.alertsMessages.add(message);

        return message;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ArrayList<String> getAlertsMessages() {
        return alertsMessages;
    }
}
