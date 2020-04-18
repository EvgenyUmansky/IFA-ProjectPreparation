package domain;

import java.util.*;

public abstract class PersonalPage {
    private Alert alert;
    private String name;
    private String info;
    private String mail;
    protected HashMap<String, Subscriber> pageOwners;


    /////////// Constructor //////////
    public PersonalPage(String name, String mail) {
        this.alert = new Alert();
        this.name = name;
        this.mail = mail;
    }

    public static PersonalPage getPage(String pageName) {
        return new PlayerPage(new TeamPlayer(null, null), pageName);
    }

    //-----Getters and Setters--------

    public PersonalPage setName(String name, String user) {
        this.name = name;
        return this;
    }

    public PersonalPage setInfo(String info) {
        this.info = info;
        return this;
    }

    public PersonalPage setMail(String mail) {
        this.mail = mail;
        return this;
    }


    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    /////////// Functionality ///////////

    // UC 3.2
    public void addSubscriber(Fan fan) {
        if (fan.isMail()) {
            this.alert.addToMailSet(fan);
        } else {
            this.alert.addToSystemSet(fan);
        }
    }

    public void addPermissions(Subscriber owner) {
        // TODO: Should update DB (maybe user-page table) and add new user to page permission
        this.pageOwners.put(owner.getUserName(), owner);
    }

}
