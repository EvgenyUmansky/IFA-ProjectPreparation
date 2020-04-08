package domain;

public abstract class Subscriber extends User {

    private boolean connected;
    private String userName;
    private String password;
    private String name;

    // Constructor
    public Subscriber(String userName, String password, String name){
        connected = false;
        this.userName = userName;
        this.name = name;
        this.password = password;
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

    public String getPassword(){
        return password;
    }

    public String getUserName(){
        return userName;
    }

}
