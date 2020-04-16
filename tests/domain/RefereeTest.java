package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        games = new ArrayList<>();
        games.add(new Game(leaguePerSeasons.get(0), hostTeams.get(0), guestTeams.get(0), hostTeams.get(0).getMyField(), "2019-02-02 22:00", new ArrayList<>()));
        games.add(new Game(null, null, null, null, "2020-02-02 22:22", new ArrayList<>()));

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

    }

    @Test
    void addGameEventToGame() {
    }

    @Test
    void changeGameEvent() {
    }

    @Test
    void getQualification() {
    }

    @Test
    void setQualification() {
    }

    @Test
    void getRefereeType() {
    }

    @Test
    void setRefereeType() {
    }
}