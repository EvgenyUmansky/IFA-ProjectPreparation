package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    ArrayList<Fan> fans;
    ArrayList<Referee> referees;
    ArrayList<GameEvent> gameEvents;
    ArrayList<LeaguePerSeason> leaguePerSeasons;
    ArrayList<Team> hostTeams;
    ArrayList<Team> guestTeams;
    ArrayList<Field> fields;

    Game game;

    @BeforeEach
    public void createStubs() {
        fans = new ArrayList<>();
        fans.add(new Fan("Messi", "euguman@gmail.com", true));
        fans.add(new Fan("unMessi", "", false));

        referees = new ArrayList<>();
        referees.add(new Referee("Evgeny", "", false, 4, RefereeType.ASSISTANT));
        referees.add(new Referee("Messi", "euguman@gmail.com", true, 4, RefereeType.MAIN));
        referees.add(new Referee("unMessi", "", false, 4, RefereeType.ASSISTANT));

        gameEvents = new ArrayList<>();
        gameEvents.add(new GameEvent("2019-02-02 22:30", 30, GameAlert.GOAL, "desc"));
        gameEvents.add(new GameEvent("2019-02-02 21:30", 30, GameAlert.GOAL, "desc"));

        leaguePerSeasons = new ArrayList<>();
        leaguePerSeasons.add(new LeaguePerSeason(2020, new TwoGameSchedulingMethod(), new RankingMethod()));
        leaguePerSeasons.add(new LeaguePerSeason(2021, new TwoGameSchedulingMethod(), new RankingMethod()));

        hostTeams = new ArrayList<>();
        hostTeams.add(new Team("Real Madrid", new Field("Enspania", 100), new TeamOwner("Zidane", "euguman@gmail.com", true)));
        hostTeams.add(new Team("Real Hadera", new Field("Alina", 100), new TeamOwner("Evgeny", "euguman@gmail.com", true)));

        guestTeams = new ArrayList<>();
        guestTeams.add(new Team("unReal Madrid", new Field("Magadan", 400), new TeamOwner("Ronaldo", "euguman@gmail.com", true)));
        guestTeams.add(new Team("Real Nesher", new Field("Alina", 100), new TeamOwner("Evgeny", "euguman@gmail.com", true)));

        fields = new ArrayList<>();
        fields.add(new Field("1eg0", 400));

        game = new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), "2019-02-02 22:00", new ArrayList<>());


    }

    @AfterEach
    public void deleteStubs(){
        fans = null;
        referees = null;
        gameEvents = null;
        leaguePerSeasons = null;
        hostTeams = null;
        guestTeams = null;
        fields = null;
        game = null;
    }

    @Test
    void addFanToAlerts() {
        game.addFanToAlerts(fans.get(0));
        game.addFanToAlerts(fans.get(1));

        assertEquals(1, game.getAlertFans().getMailAlertList().size());
        assertEquals(1, game.getAlertFans().getInSystemAlertList().size());
    }

    @Test
    void removeFanToAlerts(){
        game.addFanToAlerts(fans.get(0));
        game.addFanToAlerts(fans.get(1));

        assertEquals(1, game.getAlertFans().getMailAlertList().size());
        assertEquals(1, game.getAlertFans().getInSystemAlertList().size());

        game.removeFanToAlerts((Fan) game.getAlertFans().getMailAlertList().toArray()[0]);
        game.removeFanToAlerts((Fan) game.getAlertFans().getInSystemAlertList().toArray()[0]);

        assertEquals(0, game.getAlertFans().getMailAlertList().size());
        assertEquals(0, game.getAlertFans().getInSystemAlertList().size());
    }

    @Test
    void addRefereeToGame(){
        game.addRefereeToGame(referees.get(0));
        assertEquals(1, game.getReferees().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());
    }


    @Test
    void addRefereeToAlerts() {
        game.addRefereeToAlerts(referees.get(0));
        game.addRefereeToAlerts(referees.get(1));

        assertEquals(1, game.getAlertReferees().getMailAlertList().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());

        game.addRefereeToAlerts(referees.get(2));
        assertEquals(2, game.getAlertReferees().getInSystemAlertList().size());

    }

    @Test
    void removeRefereeToAlerts(){

        game.addRefereeToAlerts(referees.get(0));
        game.addRefereeToAlerts(referees.get(1));

        assertEquals(1, game.getAlertReferees().getMailAlertList().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());

        game.addRefereeToAlerts(referees.get(2));
        assertEquals(2, game.getAlertReferees().getInSystemAlertList().size());

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
        assertTrue(game.addGameEvent(gameEvents.get(0)));
        assertFalse(game.addGameEvent(gameEvents.get(1)));

        assertEquals(1, game.getGameEvents().size());
    }

    @Test
    void getLeaguePerSeason() {
        assertEquals(leaguePerSeasons.get(0).getSeason(), game.getLeaguePerSeason().getSeason());
    }

    @Test
    void setLeaguePerSeason() {
        game.setLeaguePerSeason(leaguePerSeasons.get(1));
        assertEquals(2021, game.getLeaguePerSeason().getSeason());
    }

    @Test
    void getHostTeam() {
        assertEquals(hostTeams.get(0).getTeamName(), game.getHostTeam().getTeamName());
    }

    @Test
    void setHostTeam() {
        game.setHostTeam(hostTeams.get(1));
        assertEquals("Real Hadera", game.getHostTeam().getTeamName());
    }

    @Test
    void getGuestTeam() {
        assertEquals(guestTeams.get(0).getTeamName(), game.getGuestTeam().getTeamName());
    }

    @Test
    void setGuestTeam() {
        game.setGuestTeam(guestTeams.get(1));
        assertEquals("Real Nesher", game.getGuestTeam().getTeamName());
    }

    @Test
    void getField() {
        assertEquals("Enspania", game.getField().getFieldName());
    }

    @Test
    void setField() {
        game.setField(fields.get(0));
        assertEquals(fields.get(0).getFieldName(), game.getField().getFieldName());
    }

    @Test
    void getReferees() {
        assertEquals(0, game.getReferees().size());
        game.addRefereeToGame(referees.get(0));
        assertEquals(1, game.getReferees().size());
    }

    @Test
    void getGameEvents() {
        assertEquals(0, game.getGameEvents().size());
        game.addGameEvent(gameEvents.get(0));
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
    void getGameDate() throws MessagingException {
        game.setGameDate("2020-12-12 13:00");
        assertEquals("2020-12-12T13:00", game.getGameDate().toString());
    }

    @Test
    void setGameDate() throws MessagingException {
        game.setGameDate("2020-12-12 13:00");
        assertEquals("2020-12-12T13:00", game.getGameDate().toString());
    }


}