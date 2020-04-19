package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RefereeTest {

    Referee referee;

    ArrayList<Referee> referees;
    ArrayList<GameEvent> gameEvents;
    ArrayList<League> leaguePerSeasons;
    ArrayList<Team> hostTeams;
    ArrayList<Team> guestTeams;
    ArrayList<Field> fields;
    ArrayList<Game> games;

    @BeforeEach
    public void insert() {
        referees = new ArrayList<>();
        referees.add(new Referee("Evgeny", ""));
        referees.add(new Referee("Messi", "euguman@gmail.com"));
        referees.add(new Referee("unMessi", ""));

        gameEvents = new ArrayList<>();
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

        games = new ArrayList<>();
        games.add(new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), new ArrayList<>()));
        games.add(new Game(null, null, null, null, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), new ArrayList<>()));

        referee = new Referee("Evgeny", "euguman@gmail.com");
        referee.setQualification(4);
        referee.setRefereeType(RefereeType.MAIN);
    }

    @AfterEach
    public void delete(){
        referee = null;
        referees = null;
        gameEvents = null;
        leaguePerSeasons = null;
        hostTeams = null;
        guestTeams = null;
        fields = null;
        games = null;
    }

    @Test
    void isAcceptedRequest() {
        assertFalse(referee.isAcceptedRequest());
    }

    @Test
    void setAcceptedRequest() {
        assertFalse(referee.isAcceptedRequest());
        referee.setAcceptedRequest(true);
        assertTrue(referee.isAcceptedRequest());
    }

    @Test
    void getRefereeDetails(){
        assertEquals("User name: Evgeny\nMail: euguman@gmail.com\nQualification: 4\nType: MAIN", referee.getRefereeDetails());
    }

    @Test
    void setRefereeDetails(){
        referee.setRefereeDetails("newmail@mail.com", 2, RefereeType.ASSISTANT);
        assertEquals("newmail@mail.com", referee.getMail());
        assertEquals(2, referee.getQualification());
        assertEquals(RefereeType.ASSISTANT.toString(), referee.getRefereeType().toString());

        referee.setRefereeDetails("newmail@mail.com", 6, null);
        assertEquals("newmail@mail.com", referee.getMail());
        assertEquals(2, referee.getQualification());
        assertEquals(RefereeType.ASSISTANT.toString(), referee.getRefereeType().toString());

        referee.setRefereeDetails("newmail@mail.com", 0, null);
        assertEquals(2, referee.getQualification());
    }

    /*
    @Test
    void changeGameEvent() {
        String eventTimePlus6 = LocalDateTime.now().plusHours(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String eventTimePlus05 = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        games.get(0).addRefereeToGame(referees.get(0));
        //referees.get(0).addGameEventToGame(games.get(0), gameEvents.get(0));

        // Not MAIN referee
        //assertFalse(referees.get(0).changeGameEvent(games.get(0), gameEvents.get(0), "2020-01-02 12:00", 29, GameAlert.GOAL, "desc"));

        games.get(0).addRefereeToGame(referee);
        games.get(0).addEvent(gameEvents.get(0));
        // Not event of this game
        //assertFalse(referee.changeGameEvent(games.get(0), gameEvents.get(1), "2020-01-02 12:00", 29, GameAlert.GOAL, "desc"));

        //referee.changeGameEvent(games.get(0), gameEvents.get(0), eventTimePlus05, 60, GameAlert.INJURY, "Test");

        assertEquals(LocalDateTime.now().plusHours(1).withSecond(0).withNano(0).toString(), games.get(0).getGameEvents().get(0).getDateTime().toString());
        assertEquals(60,  games.get(0).getGameEvents().get(0).getGameMinutes());
        assertEquals(GameAlert.INJURY.toString(), games.get(0).getGameEvents().get(0).getEventName().toString());
        assertEquals("Test", games.get(0).getGameEvents().get(0).getDescription());

        // Not allowed to change the game events: out of time
        games.get(0).setGameDate("2019-01-01 11:11");
        //assertFalse(referee.changeGameEvent(games.get(0), gameEvents.get(0), eventTimePlus6, 29, GameAlert.GOAL, "desc"));
    }
     */

    @Test
    void getQualification() {
        assertEquals(4, referee.getQualification());
    }

    @Test
    void setQualification() {
        referee.setQualification(5);
        assertEquals(5, referee.getQualification());

        referee.setQualification(0);
        assertEquals(5, referee.getQualification());

        referee.setQualification(6);
        assertEquals(5, referee.getQualification());
    }

    @Test
    void getRefereeType() {
        assertEquals(RefereeType.MAIN.toString(), referee.getRefereeType().toString());
    }

    @Test
    void setRefereeType() {
        referee.setRefereeType(RefereeType.ASSISTANT);
        assertEquals(RefereeType.ASSISTANT.toString(), referee.getRefereeType().toString());

        referee.setRefereeType(null);
        assertEquals(RefereeType.ASSISTANT.toString(), referee.getRefereeType().toString());
    }
}