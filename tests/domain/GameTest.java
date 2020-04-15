package domain;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
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
    void removeFanToAlerts(){
        addFanToAlerts();

        game.removeFanToAlerts((Fan) game.getAlertFans().getMailAlertList().toArray()[0]);
        game.removeFanToAlerts((Fan) game.getAlertFans().getInSystemAlertList().toArray()[0]);

        assertEquals(0, game.getAlertFans().getMailAlertList().size());
        assertEquals(0, game.getAlertFans().getInSystemAlertList().size());
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
    void removeRefereeToAlerts(){
        addRefereeToAlerts();

        game.removeRefereeToGame((Referee) game.getAlertReferees().getMailAlertList().toArray()[0]);
        game.removeRefereeToGame((Referee) game.getAlertReferees().getInSystemAlertList().toArray()[0]);

        assertEquals(0, game.getAlertReferees().getMailAlertList().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());
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
    void getLeaguePerSeason() {
        assertEquals(lpr.getSeason(), game.getLeaguePerSeason().getSeason());
    }

    @Test
    void setLeaguePerSeason() {
        game.setLeaguePerSeason(new LeaguePerSeason(2021, new TwoGameSchedulingMethod(), new RankingMethod()));
        assertEquals(2021, game.getLeaguePerSeason().getSeason());
    }

    @Test
    void getHostTeam() {
        assertEquals(hostTeam.getTeamName(), game.getHostTeam().getTeamName());
    }

    @Test
    void setHostTeam() {
        game.setHostTeam(new Team("Real Hadera", new Field("Alina", 100), new TeamOwner("Evgeny", "euguman@gmail.com", true)));
        assertEquals("Real Hadera", game.getHostTeam().getTeamName());
    }

    @Test
    void getGuestTeam() {
        assertEquals(guestTeam.getTeamName(), game.getGuestTeam().getTeamName());
    }

    @Test
    void setGuestTeam() {
        game.setGuestTeam(new Team("Real Nesher", new Field("Alina", 100), new TeamOwner("Evgeny", "euguman@gmail.com", true)));
        assertEquals("Real Nesher", game.getGuestTeam().getTeamName());
    }

    @Test
    void getField() {
        assertEquals("Enspania", game.getField().getFieldName());
    }

    @Test
    void setField() {
        game.setField(new Field("1eg0", 400));
        assertEquals("1eg0", game.getField().getFieldName());
    }

    @Test
    void getReferees() {
        assertEquals(0, game.getReferees().size());
        game.addRefereeToGame(new Referee("Evgeny", "", false, 4, RefereeType.ASSISTANT));
        assertEquals(1, game.getReferees().size());
    }

    @Test
    void getGameEvents() {
        assertEquals(0, game.getGameEvents().size());
        game.addGameEvent(new GameEvent("2019-02-02 22:30", 30, GameAlert.GOAL, "desc"));
        assertEquals(1, game.getGameEvents().size());
    }


    @Test
    void getHostTeamScore() {
        assertEquals(0, game.getHostTeamScore());
    }


    @Test
    void setHostTeamScore() {
        game.setHostTeamScore(3);
        assertEquals(3, game.getHostTeamScore());
    }

    @Test
    void getGuestTeamScore() {
        assertEquals(0, game.getGuestTeamScore());
    }

    @Test
    void setGuestTeamScore() {
        game.setGuestTeamScore(3);
        assertEquals(3, game.getGuestTeamScore());
    }

    @Test
    void getGameMinutes() {
        assertEquals(0, game.getGameMinutes());
    }

    @Test
    void setGameMinutes() {
        game.setGameMinutes(33);
        assertEquals(33, game.getGameMinutes());
    }

    @Test
    void getGameScore() {
        assertEquals("0:0", game.getGameScore());
        game.setHostTeamScore(3);
        game.setGuestTeamScore(2);
        assertEquals("3:2", game.getGameScore());
    }

    @Test
    void getGameDate() {

    }

    @Test
    void setGameDate() throws MessagingException {
        game.setGameDate("2020-12-12 13:00");
        assertEquals("2020-12-12T13:00", game.getGameDate().toString());
    }
}