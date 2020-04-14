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
    protected void setName(String name) {
        this.name = name;
    }

    protected void setInfo(String info) {
        this.info = info;

    }

    protected void setMail(String mail) {
        this.mail = mail;
    }


    protected String getName() {
        return name;
    }
    protected String getInfo() {
        return info;
    }

    protected HashMap<String, Subscriber> getPageOwners() {
        return pageOwners;
    }

    /////////// Functionality ///////////

    // UC 3.2
    protected void addSubscriber(Subscriber user, boolean isMail) {
        if(isMail) {
            this.alert.addToMailSet(user);
        }
        else{
            this.alert.addToSystemSet(user);
        }
    }

    protected void addPermissions(Subscriber owner) {
        this.pageOwners.put(owner.getUserName(),owner);
    }

}
