package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    LeaguePerSeason lpr = new LeaguePerSeason(2020, new TwoGameSchedulingMethod(), new RankingMethod());
    Team hostTeam = new Team("Real Madrid", new Field("Enspania", 100), new TeamOwner("Zidane", "euguman@gmail.com", true));
    Team guestTeam = new Team("unReal Madrid", new Field("Magadan", 400), new TeamOwner("Ronaldo", "euguman@gmail.com", true));
    Game game = new Game(lpr, hostTeam, guestTeam, hostTeam.getMyField(), "2019-02-02 22:00", new ArrayList<>());

    @Test
    void addFanToAlerts() {
        game.addFanToAlerts(new Fan("Messi", "euguman@gmail.com", true));
        game.addFanToAlerts(new Fan("unMessi", "", false));

        assertEquals(1, game.getAlertFans().getMailAlertList().size());
        assertEquals(1, game.getAlertFans().getInSystemAlertList().size());
    }

    @Test
    void addRefereeToGame(){
        game.addRefereeToGame(new Referee("Evgeny", "", false, 4, RefereeType.ASSISTANT));
        assertEquals(1, game.getReferees().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());
    }

    @Test
    void addRefereeToAlerts() {
        game.addRefereeToAlerts(new Referee("Messi", "euguman@gmail.com", true, 4, RefereeType.MAIN));
        game.addRefereeToAlerts(new Referee("unMessi", "", false, 4, RefereeType.ASSISTANT));

        assertEquals(1, game.getAlertReferees().getMailAlertList().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());

        game.addRefereeToAlerts(new Referee("Evgeny", "", false, 4, RefereeType.ASSISTANT));
        assertEquals(2, game.getAlertReferees().getInSystemAlertList().size());

    }

    @Test
    void sendAlertScoreToFan() {
    }

    @Test
    void sendAlertCloseGame() {
    }

    @Test
    void sendAlertChangeDateGame() {
    }

    @Test
    void addGameEvent() {
        assertTrue(game.addGameEvent(new GameEvent("2019-02-02 22:30", 30, GameAlert.GOAL, "desc")));
        assertFalse(game.addGameEvent(new GameEvent("2019-02-02 21:30", 30, GameAlert.GOAL, "desc")));

        assertEquals(1, game.getGameEvents().size());
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