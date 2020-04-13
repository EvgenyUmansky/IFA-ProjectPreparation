package domain;

import java.util.*;

public abstract class PersonalPage {
    private Alert alert;
    private String name;
    private String info;
    private String mail;
    protected HashMap<String,Subscriber> pageOwners;


    /////////// Constructor //////////
    public PersonalPage(String name, String mail) {
        this.alert = new Alert();
        this.name = name;
        this.mail = mail;
    }

    //-----Getters and Setters--------
    public boolean setName(String name, String user) {
        if(pageOwners.containsKey(user)){
            this.name = name;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setInfo(String info, String user) {
        if(pageOwners.containsKey(user)){
            this.info = info;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setMail(String mail, String user) {
        if(pageOwners.containsKey(user)){
            this.mail = mail;
            return true;
        }
        else{
            return false;
        }
    }


    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }

    /////////// Functionality ///////////

    // UC 3.2
    public void addSubscriber(Subscriber user, boolean isMail) {
        if(isMail) {
            this.alert.addToMailSet(user);
        }
        else{
            this.alert.addToSystemSet(user);
        }
    }

    public void addPermissions(Subscriber owner) {
        this.pageOwners.put(owner.getUserName(),owner);
    }

}
