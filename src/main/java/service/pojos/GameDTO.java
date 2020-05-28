package service.pojos;

import domain.Field;
import domain.GameEvent;
import domain.Referee;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameDTO {
    private int gameId;
    private String hostTeam;
    private String guestTeam;
    private Field field;
    private LocalDateTime gameDate;
    private ArrayList<Referee> referees;
    private ArrayList<GameEventDTO> gameEvents;
    private String gameScore;

    public GameDTO(int gameId, String hostTeam, String guestTeam, Field field, LocalDateTime gameDate, ArrayList<Referee> referees, ArrayList<GameEventDTO> gameEvents, String gameScore){
        this.gameId = gameId;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.gameDate = gameDate;
        this.referees = referees;
        this.gameEvents = gameEvents;
        this.gameScore = gameScore;
    }

    public int getGameId() {
        return gameId;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public Field getField() {
        return field;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public ArrayList<Referee> getReferees() {
        return referees;
    }

    public ArrayList<GameEventDTO> getGameEvents() {
        return gameEvents;
    }

    public String getGameScore() {
        return gameScore;
    }
}
