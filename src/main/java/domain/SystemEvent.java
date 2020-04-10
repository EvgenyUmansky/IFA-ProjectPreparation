package domain;


import java.util.Date;

public class SystemEvent {

    private String description;
    private Date date;

    // Constructor
    public SystemEvent(String description) {
        this.date = new Date();
        this.description = description;
    }

}
