package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TeamPageTest {

    Field field;
    TeamPage teamPage;
    TeamOwner owner;
    Team team;

    @BeforeEach
    public void init(){
        field = new Field("test",100);
        owner = new TeamOwner("ownerUser","owner@gmail.com");
        team = new Team("test",field,owner);
        teamPage = new TeamPage(team);
    }

    @Test
    void setStadium() {
        assertEquals(field,teamPage.getStadium());
        Field fieldTest = new Field("stadium",200);
        teamPage.setStadium(fieldTest);
        assertEquals(fieldTest,teamPage.getStadium());
    }

    @Test
    void setPlayers() {
        assertEquals(team.getPlayers(),teamPage.getPlayers());
        TeamPlayer player = new TeamPlayer("playeruser","player@gmail.com");
        HashMap<String,TeamPlayer> players = new HashMap<>();
        players.put(player.getUserName(),player);
        teamPage.setPlayers(players);
        assertEquals(players,teamPage.getPlayers());
    }

    @Test
    void setCoaches() {
        assertEquals(team.getCoaches(),teamPage.getCoaches());
        TeamCoach coach = new TeamCoach("coachUser","coach@gmail.com");
        HashMap<String,TeamCoach> coachs = new HashMap<>();
        coachs.put(coach.getUserName(),coach);
        teamPage.setCoaches(coachs);
        assertEquals(coachs,teamPage.getCoaches());
    }

    @Test
    void setManagers() {
        assertEquals(team.getManagers(),teamPage.getManagers());
        TeamManager manager = new TeamManager("managerUser","manager@gmail.com");
        HashMap<String,TeamManager> managers = new HashMap<>();
        managers.put(manager.getUserName(),manager);
        teamPage.setManagers(managers);
        assertEquals(managers,teamPage.getManagers());
    }
}