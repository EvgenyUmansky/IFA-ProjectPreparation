package domain;

/**
 * This class represents a notification that is sent to users in the system.
 */
public class AlertNotification {

    private String title;
    private String message;
    private boolean isSeen;


    /**
     * Constructor
     * @param title the title of the message
     * @param message the content of the message
     */
    public AlertNotification(String title, String message) {
        this.title = title;
        this.message = message;
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
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the message to the given message
     * @param message the given message
     */
    public void setMessage(String message) {
        this.message = message;
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
}
