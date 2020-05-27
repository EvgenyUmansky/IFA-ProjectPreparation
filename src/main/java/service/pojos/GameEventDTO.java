package service.pojos;

public class GameEventDTO {
    private String gameId;
    private String gameName;
    private String minutes;
    private String event;
    private String description;

    public GameEventDTO(String gameId, String gameName, String minutes, String event, String description) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.minutes = minutes;
        this.event = event;
        this.description = description;
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
        return event + " in " + gameName + " game in minute " + minutes + " : " + description;
    }
}
