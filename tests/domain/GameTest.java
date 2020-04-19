package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    ArrayList<Fan> fans;
    ArrayList<Referee> referees;
    ArrayList<GameEvent> gameEvents;
    ArrayList<League> leaguePerSeasons;
    ArrayList<Team> hostTeams;
    ArrayList<Team> guestTeams;
    ArrayList<Field> fields;

    Game game;
    Game gameCE;

    @BeforeEach
    public void insert() {
        fans = new ArrayList<>();
        fans.add(new Fan("Messi", "euguman@gmail.com"));
        fans.get(0).setMail(true);
        fans.add(new Fan("unMessi", ""));

        referees = new ArrayList<>();
        referees.add(new Referee("Evgeny", ""));
        referees.get(0).setMail(false);
        referees.get(0).setQualification(4);
        referees.get(0).setRefereeType(RefereeType.MAIN);
        referees.add(new Referee("Messi", "euguman@gmail.com"));
        referees.get(1).setMail(true);
        referees.get(1).setQualification(4);
        referees.get(1).setRefereeType(RefereeType.ASSISTANT);
        referees.add(new Referee("unMessi", ""));
        referees.get(2).setMail(false);

        gameEvents = new ArrayList<>();
        gameEvents.add(new GameEvent("2019-02-02 22:30", 30, GameAlert.GOAL, "desc"));
        gameEvents.add(new GameEvent("2019-02-02 21:30", 30, GameAlert.GOAL, "desc"));
        gameEvents.add(new GameEvent(LocalDateTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 30, GameAlert.GOAL, "desc"));
        gameEvents.add(new GameEvent("2020-01-02 12:29", 30, GameAlert.GOAL, "desc"));

        leaguePerSeasons = new ArrayList<>();
        leaguePerSeasons.add(new League(2020, new TwoGameSchedulingMethod(), new RankingMethod(), "Prime"));
        leaguePerSeasons.add(new League(2021, new TwoGameSchedulingMethod(), new RankingMethod(), "Prime"));

        hostTeams = new ArrayList<>();
        hostTeams.add(new Team("Real Madrid", new Field("Enspania", 100), new TeamOwner("Zidane", "euguman@gmail.com")));
        hostTeams.add(new Team("Real Hadera", new Field("Alina", 100), new TeamOwner("Evgeny", "euguman@gmail.com")));

        guestTeams = new ArrayList<>();
        guestTeams.add(new Team("unReal Madrid", new Field("Magadan", 400), new TeamOwner("Ronaldo", "euguman@gmail.com")));
        guestTeams.add(new Team("Real Nesher", new Field("Alina", 100), new TeamOwner("Evgeny", "euguman@gmail.com")));

        fields = new ArrayList<>();
        fields.add(new Field("1eg0", 400));

        game = new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), "2019-02-02 22:00", new ArrayList<>());
        gameCE = new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), new ArrayList<>());
    }

    @AfterEach
    public void delete(){
        fans = null;
        referees = null;
        gameEvents = null;
        leaguePerSeasons = null;
        hostTeams = null;
        guestTeams = null;
        fields = null;
        game = null;
        gameCE = null;
    }

    @Test
    void addRefereeToGame(){
        game.addRefereeToGame(referees.get(0));
        assertEquals(1, game.getReferees().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());
    }

    @Test
    void removeRefereeFromGame(){
        game.addRefereeToGame(referees.get(0));
        assertEquals(1, game.getReferees().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());

        game.removeRefereeFromGame(referees.get(0));
        assertEquals(0, game.getReferees().size());
        assertEquals(0, game.getAlertReferees().getInSystemAlertList().size());
    }

    @Test
    void addFanToAlerts() {
        game.addFanToAlerts(fans.get(0));
        game.addFanToAlerts(fans.get(1));

        assertEquals(1, game.getAlertFans().getMailAlertList().size());
        assertEquals(1, game.getAlertFans().getInSystemAlertList().size());
    }

    @Test
    void removeFanFromAlerts(){
        game.addFanToAlerts(fans.get(0));
        game.addFanToAlerts(fans.get(1));

        assertEquals(1, game.getAlertFans().getMailAlertList().size());
        assertEquals(1, game.getAlertFans().getInSystemAlertList().size());

        game.removeFanFromAlerts((Fan) game.getAlertFans().getMailAlertList().toArray()[0]);
        game.removeFanFromAlerts((Fan) game.getAlertFans().getInSystemAlertList().toArray()[0]);

        assertEquals(0, game.getAlertFans().getMailAlertList().size());
        assertEquals(0, game.getAlertFans().getInSystemAlertList().size());
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
    void deleteRefereeFromAlerts(){
        game.addRefereeToAlerts(referees.get(0));
        game.addRefereeToAlerts(referees.get(1));
        assertEquals(1, game.getAlertReferees().getMailAlertList().size());
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());
        game.addRefereeToAlerts(referees.get(2));
        assertEquals(2, game.getAlertReferees().getInSystemAlertList().size());

        game.deleteRefereeFromAlerts(referees.get(0));
        assertEquals(1, game.getAlertReferees().getInSystemAlertList().size());
        game.deleteRefereeFromAlerts(referees.get(1));
        assertEquals(0, game.getAlertReferees().getMailAlertList().size());
        game.deleteRefereeFromAlerts(referees.get(2));
        assertEquals(0, game.getAlertReferees().getInSystemAlertList().size());
    }

    @Test
    void addRefereesOfGameToAlerts(){
        game = new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), "2019-02-02 22:00", new ArrayList<Referee>(){{add(referees.get(0));}});
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
        game.addFanToAlerts(fans.get(0));
        game.addFanToAlerts(fans.get(1));

        game.addRefereeToAlerts(referees.get(0));
        game.addRefereeToAlerts(referees.get(1));

        Map<String, Boolean> isSentMap = new HashMap(game.setGameDate("2021-03-04 11:11"));
        for(String user : isSentMap.keySet()){
            assertTrue(isSentMap.get(user));
        }
    }

    @Test
    void addEvent() throws Exception {

        game.addEvent(gameEvents.get(0));

        assertEquals(1, game.getGameEvents().size());

        try {
            game.addEvent(gameEvents.get(1));
        } catch (Exception e) {
            assertEquals("java.lang.Exception: The date of the game is invalid", e.toString());
        }
    }

    @Test
    void changeGameEvent() throws Exception {
        String eventTimePlus6 = LocalDateTime.now().plusHours(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String eventTimePlus05 = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        gameCE.addEvent(gameEvents.get(2));

        // Not event of this game
        try {
            gameCE.changeEvent(gameEvents.get(3), "2020-01-02 12:00", 29, GameAlert.GOAL, "desc");
        } catch (Exception e) {
            assertEquals("java.lang.Exception: Not event of this game", e.toString());
        }

        gameCE.changeEvent(gameCE.getGameEvents().get(0), eventTimePlus05, 60, GameAlert.INJURY, "Test");

        assertEquals(LocalDateTime.now().plusHours(1).withSecond(0).withNano(0).toString(), gameCE.getGameEvents().get(0).getDateTime().toString());
        assertEquals(60, gameCE.getGameEvents().get(0).getGameMinutes());
        assertEquals(GameAlert.INJURY.toString(), gameCE.getGameEvents().get(0).getEventName().toString());
        assertEquals("Test", gameCE.getGameEvents().get(0).getDescription());

        // Not allowed to change the game events: out of time
        gameCE.setGameDate("2019-01-01 11:11");
        try {
            gameCE.changeEvent(gameCE.getGameEvents().get(0), eventTimePlus6, 29, GameAlert.GOAL, "desc");
        } catch (Exception e) {
            assertEquals("java.lang.Exception: Not allowed to change the game events: out of time", e.toString());
        }
    }

    @Test
    void getGamesByReferee(){

    }

    @Test
    void getLeaguePerSeason() {
        assertEquals(leaguePerSeasons.get(0).getSeason(), game.getLeague().getSeason());
    }

    @Test
    void setLeaguePerSeason() {
        game.setLeague(leaguePerSeasons.get(1));
        assertEquals(2021, game.getLeague().getSeason());
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
    void getGameEvents() throws Exception {
        assertEquals(0, game.getGameEvents().size());
        game.addEvent(gameEvents.get(0));
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
    void getGameDate()  {
        game.setGameDate("2020-12-12 13:00");
        assertEquals("2020-12-12T13:00", game.getGameDate().toString());
    }

    @Test
    void setGameDate() {
        game.setGameDate("2020-12-12 13:00");
        assertEquals("2020-12-12T13:00", game.getGameDate().toString());
    }


}