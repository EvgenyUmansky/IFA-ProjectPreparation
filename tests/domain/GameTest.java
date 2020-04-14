package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class GameTest {

    LeaguePerSeason lpr = new LeaguePerSeason(2020, new TwoGameSchedulingMethod(), new RankingMethod());
    //Team hostTeam = new Team("Real Madrid", new Field("Enspania", 100));
    //Team guestTeam = new Team("unReal Madrid", new Field("Magadan", 400));
   // Game game = new Game(lpr, hostTeam, guestTeam, hostTeam.getMyField(), "2019-02-02 22:00", new ArrayList<>());

    @Test
    void addFanSubscriber() {
    }

    @Test
    void addRefereeSubscriber() {
    }

    @Test
    void sendAlertScoreToFan() {
    }

    @Test
    void addGameEvent() {
        GameEvent gameEvent1 = new GameEvent("2019-02-02 22:30", 30, GameAlert.GOAL, "in game");
        GameEvent gameEvent2 = new GameEvent("2019-02-02 21:30", 30, GameAlert.GOAL, "before game");

    }

    @Test
    void getGameId() {
    }

    @Test
    void getLeaguePerSeason() {
    }

    @Test
    void setLeaguePerSeason() {
    }

    @Test
    void getHostTeam() {
    }

    @Test
    void setHostTeam() {
    }

    @Test
    void getGuestTeam() {
    }

    @Test
    void setGuestTeam() {
    }

    @Test
    void getField() {
    }

    @Test
    void setField() {
    }

    @Test
    void getReferees() {
    }

    @Test
    void setReferees() {
    }

    @Test
    void getGameEvents() {
    }

    @Test
    void setGameEvents() {
    }

    @Test
    void getHostTeamScore() {
    }

    @Test
    void setHostTeamScore() {
    }

    @Test
    void getGuestTeamScore() {
    }

    @Test
    void setGuestTeamScore() {
    }

    @Test
    void getGameMinutes() {
    }

    @Test
    void setGameMinutes() {
    }

    @Test
    void getGameScore() {
    }

    @Test
    void getGameDate() {
    }

    @Test
    void setGameDate() {
    }
}