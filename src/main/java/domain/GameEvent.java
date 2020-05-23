package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an event that takes place during a match
 */
public class GameEvent {
    // ID
    static AtomicInteger nextId = new AtomicInteger();
    private final int id;


    private LocalDateTime dateTime;
    private int gameMinutes;
    private GameAlert eventName;
    private String description;

/////////// Constructors ///////////

    /**
     * Constructor
     * @param gameMinutes the minute of the game
     * @param eventName the type of the event
     * @param description the description of the event
     */
    public GameEvent(int gameMinutes, GameAlert eventName, String description) {
        // set id
        this.id = nextId.incrementAndGet();

        this.dateTime = LocalDateTime.now().withNano(0).withSecond(0);
        this.gameMinutes = gameMinutes;
        this.eventName = eventName;
        this.description = description;
    }


/////////// Getters and Setters ///////////
    /**
     * Returns the id of event
     * @return the id of event
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the date and time the event took place
     * @return the date and time the event took place
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Updates the date and time the event took place (string format: "2016-11-09 11:44")
     * @param dateTimeStr the date and time the event took place
     */
    public void setGameDate(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * Updates the date and time the event took place (LocalDateTime object)
     * @param dateTime the date and time the event took place
     */
    public void setGameDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns the minute in the match that the event took place in
     * @return the minute in the match that the event took place in
     */
    public int getGameMinutes() {
        return gameMinutes;
    }

    /**
     * Updates the minute in the match that the event took place in
     * @param gameMinutes the minute in the match that the event took place in
     */
    public void setGameMinutes(int gameMinutes) {
        this.gameMinutes = gameMinutes;
    }

    /**
     * Returns the name of the event
     * @return the name of the event
     */
    public GameAlert getEventName() {
        return eventName;
    }

    /**
     * Updates the name of the event
     * @param eventName the name of the event
     */
    public void setEventName(GameAlert eventName) {
        this.eventName = eventName;
    }

    /**
     * Returns the description of the event
     * @return the description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of the event
     * @param description the description of the event
     */
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
