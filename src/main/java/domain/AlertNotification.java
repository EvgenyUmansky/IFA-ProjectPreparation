package domain;

public class AlertNotification {
    private String title;
    private String message;
    private boolean isSeen;

/////////// Constructor ///////////
    public AlertNotification(String title, String message) {
        this.title = title;
        this.message = message;
        this.isSeen = false;
    }

/////////// Functionality ///////////


/////////// Getters and Setters ///////////
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
