package domain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a notification that is sent to users in the system.
 */
public class AlertNotification {

    static AtomicInteger nextId = new AtomicInteger();
    private final int id;

    private String title;
    private String subject;
    private boolean isSeen;


    /**
     * Constructor
     * @param title the title of the message
     * @param subject the content of the message
     */
    public AlertNotification(String title, String subject) {
        // set id
        this.id = nextId.incrementAndGet();

        this.title = title;
        this.subject = subject;
        this.isSeen = false;
    }

    /**
     * Constructor DB
     * @param title the title of the message
     * @param subject the content of the message
     */
    public AlertNotification(int id, String title, String subject) {
        // set id
        this.id = id;

        this.title = title;
        this.subject = subject;
        this.isSeen = false;
    }



/////////// Getters and Setters ///////////

    /**
     * Returns the title of the message
     * @return the title of the message
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the message to the given title
     * @param title the given title
     */
    public void setTitle(String title) {
        this.title = title;
    }

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

}
