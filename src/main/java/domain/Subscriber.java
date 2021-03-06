package domain;


import java.util.ArrayList;

/**
 * This class represents a user that has a specific role in the system.
 * When a user is defined as a certain role, a new matching instance of subscriber is created and added to the user's roles list.
 */
public abstract class Subscriber {

    private String name;
    private String userName;
    private String mail;
    private boolean isMail;
    private ArrayList<Notification> notifications;
    public String getUserName() {
        return userName;
    }


/////////// Constructor ///////////

    /**
     * Constructor
     * @param userName the user's username
     * @param mail the user's mail
     */
    public Subscriber(String userName, String mail){
        this.userName = userName;
        this.notifications = new ArrayList<>();
        this.mail = mail;
        this.name = null;
        this.isMail = false;
    }

    public Subscriber(String userName, String mail, String name){
        this.userName = userName;
        this.notifications = new ArrayList<>();
        this.mail = mail;
        this.name = name;
        this.isMail = false;
    }

    /**
     * Adds a notification to the user's notifications box in the system
     * @param notification alert message
     */
    public void addNotifications(Notification notification){
        this.notifications.add(notification);
    }

    /**
     * Returns the user's mail
     * @return the user's mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Updates the user preference in the way of receiving notifications.
     * @param mail true if the user prefers by mail and false if by the system's messages box.
     */
    public void setMail(boolean mail) {
        isMail = mail;
    }

    /**
     * Updates the user's mail
     * @param mail the user's mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Returns true if the user prefers receiving notifications by mail and false if by the system's messages box.
     * @return true if the user prefers receiving notifications by mail and false if by the system's messages box.
     */
    public boolean isMail() {
        return isMail;
    }

    /**
     * Returns all the notifications the user has received.
     * @return all the notifications the user has received.
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){ this.name = name;}
}
