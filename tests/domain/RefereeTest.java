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
    ArrayList<LeaguePerSeason> leaguePerSeasons;
    ArrayList<Team> hostTeams;
    ArrayList<Team> guestTeams;
    ArrayList<Field> fields;
    ArrayList<Game> games;

    @BeforeEach
    public void insert() {
        referees = new ArrayList<>();
        referees.add(new Referee("Evgeny", "", false, 4, RefereeType.ASSISTANT));
        referees.add(new Referee("Messi", "euguman@gmail.com", true, 4, RefereeType.MAIN));
        referees.add(new Referee("unMessi", "", false, 4, RefereeType.ASSISTANT));

        gameEvents = new ArrayList<>();
        gameEvents.add(new GameEvent(LocalDateTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 30, GameAlert.GOAL, "desc"));
        gameEvents.add(new GameEvent("2020-01-02 12:29", 30, GameAlert.GOAL, "desc"));

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

        games = new ArrayList<>();
        games.add(new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), new ArrayList<>()));
        games.add(new Game(null, null, null, null, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), new ArrayList<>()));

        referee = new Referee("Evgeny", "euguman@gmail.com", true, 4, RefereeType.MAIN);
    }

    @AfterEach
    public void delete(){
        referee = null;
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
    void getRefereeGames() {
        assertEquals(0, referee.getRefereeGames(games).size());
        games.get(0).addRefereeToGame(referee);
        assertEquals(1, referee.getRefereeGames(games).size());
    }

    @Test
    void addGameEventToGame() {
        games.get(0).addRefereeToGame(referee);
        assertEquals(0, games.get(0).getGameEvents().size());
        referee.addGameEventToGame(games.get(0), gameEvents.get(0));
        assertEquals(1, games.get(0).getGameEvents().size());
    }

    @Test
    void changeGameEvent() {
        String eventTimePlus6 = LocalDateTime.now().plusHours(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String eventTimePlus05 = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        games.get(0).addRefereeToGame(referees.get(0));
        referees.get(0).addGameEventToGame(games.get(0), gameEvents.get(0));

        // Not MAIN referee
        assertFalse(referees.get(0).changeGameEvent(games.get(0), gameEvents.get(0), "2020-01-02 12:00", 29, GameAlert.GOAL, "desc"));

        games.get(0).addRefereeToGame(referee);
        games.get(0).addGameEvent(gameEvents.get(0));
        // Not event of this game
        assertFalse(referee.changeGameEvent(games.get(0), gameEvents.get(1), "2020-01-02 12:00", 29, GameAlert.GOAL, "desc"));

        referee.changeGameEvent(games.get(0), gameEvents.get(0), eventTimePlus05, 60, GameAlert.INJURY, "Test");

        assertEquals(LocalDateTime.now().plusHours(1).withSecond(0).withNano(0).toString(), games.get(0).getGameEvents().get(0).getDateTime().toString());
        assertEquals(60,  games.get(0).getGameEvents().get(0).getGameMinutes());
        assertEquals(GameAlert.INJURY.toString(), games.get(0).getGameEvents().get(0).getEventName().toString());
        assertEquals("Test", games.get(0).getGameEvents().get(0).getDescription());

        // Not allowed to change the game events: out of time
        games.get(0).setGameDate("2019-01-01 11:11");
        assertFalse(referee.changeGameEvent(games.get(0), gameEvents.get(0), eventTimePlus6, 29, GameAlert.GOAL, "desc"));
    }

    @Test
    void getQualification() {
        assertEquals(4, referee.getQualification());
    }

    @Test
    void setQualification() {
        referee.setQualification(5);
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
    }
}