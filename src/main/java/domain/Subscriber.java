package domain;


import java.util.ArrayList;

/**
 * This class represents a user that has a specific role in the system.
 * When a user is defined as a certain role, a new matching instance of subscriber is created and added to the user's roles list.
 */
public abstract class Subscriber {

    private final String userName;
    private String name;
    private String mail;
    private boolean isMail;
    private final ArrayList<AlertNotification> alertsMessages;
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
        this.alertsMessages = new ArrayList<>();
        this.mail = mail;
        this.isMail = false;
    }

    public Subscriber(String userName, String name, String mail, boolean isMail) {
        this.userName = userName;
        this.name = name;
        this.mail = mail;
        this.isMail = isMail;
        this.alertsMessages = new ArrayList<>();
    }

    /**
     * Adds a notification to the user's notifications box in the system
     * @param message the notification
     * @return the notification
     */
    public AlertNotification addAlertMessage(AlertNotification message){
        this.alertsMessages.add(message);

        return message;
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
    public ArrayList<AlertNotification> getAlertsMessages() {
        return alertsMessages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
