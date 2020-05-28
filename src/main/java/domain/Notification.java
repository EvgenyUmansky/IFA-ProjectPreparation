package domain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a notification that is sent to users in the system.
 */
public class Notification {

    // static AtomicInteger nextId = new AtomicInteger();
    private int id;

    private String subject;
    private boolean isSeen;


    /**
     * Constructor
     * @param subject the content of the message
     */
    public Notification(String subject) {
        // set id
        //this.id = nextId.incrementAndGet();
        this.id = 0;

        this.subject = subject;
        this.isSeen = false;
    }

    /**
     * Constructor DB
     * @param subject the content of the message
     */
    public Notification(int id, String subject) {
        // set id
        this.id = id;

        this.subject = subject;
        this.isSeen = false;
    }



/////////// Getters and Setters ///////////

    /**
     * Returns the content of the message
     * @return the content of the message
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the content of the message to the given message
     * @param subject the given message
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns true if the message was seen, false otherwise
     * @return true if the message was seen, false otherwise
     */
    public boolean isSeen() {
        return isSeen;
    }

    /**
     * Sets the seen status of the message to the given value
     * @param seen the seen status of the message
     */
    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    /**
     *  get NotificationId
      */
    public int getId() {
        return id;
    }

    /**
     *  set NotificationId
     */
    public void setId(int id) {
        this.id = id;
    }

}
