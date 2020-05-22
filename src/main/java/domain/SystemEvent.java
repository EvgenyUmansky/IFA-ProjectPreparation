package domain;


import java.util.Date;

/**
 * Represents the log of events in the system - TODO: change this to logger class
 */
public class SystemEvent {

    private String description;
    private Date date;

    // Constructor
    public SystemEvent(String description) {
        this.date = new Date();
        this.description = description;
    }

}
