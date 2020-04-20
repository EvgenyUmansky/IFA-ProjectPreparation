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
        pageOwners = new HashMap<>();
    }

    public static PersonalPage getPage(String pageName) {
        return new PlayerPage(new TeamPlayer(null, null), pageName);
    }

    //-----Getters and Setters--------

    public PersonalPage setName(String name) {
        this.name = name;

        // UC 3.2
        alert.sendAlert(new AlertNotification("Name of the page " + this.name + " has changed", "The new name is " + name));

        return this;
    }

    public PersonalPage setInfo(String info) {
        this.info = info;

        // UC 3.2
        alert.sendAlert(new AlertNotification("Info of the page " + this.name + "  has changed", "The new info is " + info));

        return this;
    }

    public PersonalPage setMail(String mail) {
        this.mail = mail;

        // UC 3.2
        alert.sendAlert(new AlertNotification("Mail of the page " + this.name + "  has changed", "The new mail is " + mail));

        return this;
    }


    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Alert getAlert() {
        return alert;
    }

    public String getMail() {
        return mail;
    }

    /////////// Functionality ///////////

    // UC 3.2
    public void addSubscriber(Fan fan) {
        alert.addSubscriber(fan);
    }

    public void addPermissions(Subscriber owner) {
        // TODO: Should update DB (maybe user-page table) and add new user to page permission
        this.pageOwners.put(owner.getUserName(), owner);
    }

}
