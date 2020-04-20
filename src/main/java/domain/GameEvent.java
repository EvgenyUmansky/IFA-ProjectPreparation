package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameEvent {
    private LocalDateTime dateTime;
    private int gameMinutes;
    private GameAlert eventName;
    private String description;

/////////// Constructors ///////////
    public GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String description) {
        // Game date string format: "2016-11-09 11:44"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        this.gameMinutes = gameMinutes;
        this.eventName = eventName;
        this.description = description;
    }


/////////// Getters and Setters ///////////
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * string format: "2016-11-09 11:44"
     * @param dateTimeStr
     */
    public void setGameDate(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
    }

    public void setGameDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getGameMinutes() {
        return gameMinutes;
    }

    public void setGameMinutes(int gameMinutes) {
        this.gameMinutes = gameMinutes;
    }

    public GameAlert getEventName() {
        return eventName;
    }

    public void setEventName(GameAlert eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  dateTime + " " +
                gameMinutes + ", " +
                eventName.toString() +" " +
                description;
    }
}
