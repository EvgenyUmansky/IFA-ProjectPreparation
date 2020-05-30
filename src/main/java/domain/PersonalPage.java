package domain;

import java.util.*;

/**
 * This class represents a profile page in the system
 */
public abstract class PersonalPage {

    private Alert alert;
    private String name;
    private String info;
    private String mail;
    protected HashMap<String, Subscriber> pageOwners;


    /////////// Constructor //////////

    /**
     * Constructor
     * @param name the name of the page owner
     * @param mail the mail of the page owner
     */
    public PersonalPage(String name, String mail) {
        this.alert = new Alert();
        this.name = name;
        this.mail = mail;
        pageOwners = new HashMap<>();
    }


    //-----Getters and Setters--------


    /**
     * UC 3.2
     * Sets the name of the profile page and updates its subscribers
     * @param name the new name
     * @return the updated instance of the page
     */
    public PersonalPage setName(String name) {
        this.name = name;
        alert.sendAlert(new Notification("Name of the page " + this.name + " has changed. The new name is " + name));

        return this;
    }

    /**
     * UC 3.2
     * Sets the info of the profile page and updates its subscribers
     * @param info the updated info of the page owner
     * @return the updated instance of the page
     */
    public PersonalPage setInfo(String info) {
        this.info = info;
        alert.sendAlert(new Notification("Info of the page " + this.name + "  has changed. The new info is " + info));

        return this;
    }

    /**
     * UC 3.2
     * Sets the mail of the profile page and updates its subscribers
     * @param mail the new mail in the profile page
     * @return the updated instance of the page
     */
    public PersonalPage setMail(String mail) {
        this.mail = mail;
        alert.sendAlert(new Notification("Mail of the page " + this.name + "  has changed. The new mail is " + mail));

        return this;
    }

    /**
     * Returns the name of the profile owner as presented in the page
     * @return the name of the profile owner as presented in the page
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the info of the profile owner as presented in the page
     * @return the info of the profile owner as presented in the page
     */
    public String getInfo() {
        return info;
    }

    /**
     * Returns the notifications system of the profile page
     * @return the notifications system of the profile page
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * Returns the mail of the profile owner as presented in the page
     * @return the mail of the profile owner as presented in the page
     */
    public String getMail() {
        return mail;
    }

    /**
     * Returns a HashMap of the subscribers that own the profile page
     * @return a HashMap of the subscribers that own the profile page
     */
    public HashMap<String, Subscriber> getPageOwners() {
        return pageOwners;
    }
    /////////// Functionality ///////////

    /**
     * Returns a new instance of a profile page
     * @param pageName the name of the page
     * @return a new instance of a profile page
     */
    public static PersonalPage getPage(String pageName) {
        return new PlayerPage(new TeamPlayer(null, null), pageName);
    }


    /**
     * UC 3.2
     * Adds a fan as a subscriber of the page
     * @param fan the new subscriber
     */
    public void addSubscriber(Fan fan) {
        alert.addSubscriber(fan);
    }

    /**
     * Adds a subscriber as an owner of the page
     * @param owner the added page owner
     */
    public void addPermissions(Subscriber owner) {
        // TODO: Should update DB (maybe user-page table) and add new user to page permission
        this.pageOwners.put(owner.getUserName(), owner);
    }

}
