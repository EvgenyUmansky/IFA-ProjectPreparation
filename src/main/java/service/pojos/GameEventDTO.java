package service.pojos;
import java.time.LocalDateTime;

public class GameEventDTO {
    private String date;
    private String gameId;
    private String gameName;
    private String minutes;
    private String event;
    private String description;

    public GameEventDTO(String date, String gameId, String gameName, String minutes, String event, String description) {
        this.date = date;
        this.gameId = gameId;
        this.gameName = gameName;
        this.minutes = minutes;
        this.event = event;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void pinDate(){
        date = LocalDateTime.now().withNano(0).withSecond(0).toString().replace('T',' ');
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return date + ":" + '\n' + event + " in " + gameName + " game in minute " + minutes + ": " + description;
    }
}
