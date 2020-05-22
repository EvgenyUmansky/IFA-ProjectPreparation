package domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPageTest {

    TeamPlayer player = new TeamPlayer("pageTestUser","pageTestMail");
    PlayerPage page = new PlayerPage(player,"pageTestName");

    @Test
    void setBirthDate() {
        assertNull(page.getBirthDate());
        page.setBirthDate(new Date(1992,02,02));
        assertNotNull(page.getBirthDate());
    }

    @Test
    void setSquadNumber() {
        assertNull(page.getSquadNumber());
        page.setSquadNumber("5");
        assertEquals("5",page.getSquadNumber());
    }

    @Test
    void setPosition() {
        assertNull(page.getPosition());
        page.setPosition("player");
        assertEquals("player",page.getPosition());
    }

    @Test
    void setCurrentTeam() {
        Field field = new Field("test",100);
        TeamOwner owner = new TeamOwner("ownerUser","owner@gmail.com");
        Team team = new Team("team",field,owner);
        assertNull(page.getCurrentTeam());
        page.setCurrentTeam(team.getTeamName());
        assertEquals(team,page.getCurrentTeam());
    }
}