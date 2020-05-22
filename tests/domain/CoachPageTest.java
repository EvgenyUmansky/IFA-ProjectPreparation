package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoachPageTest {

    TeamCoach coach = new TeamCoach("coachUser","coachMail");
    CoachPage page = new CoachPage(coach,"pageTestName");

    @Test
    void setRole() {
        assertNull(page.getRole());
        page.setRole("role");
        assertEquals("role",page.getRole());
    }

    @Test
    void setQualification() {
        assertNull(page.getQualification());
        page.setQualification("qualification");
        assertEquals("qualification",page.getQualification());
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