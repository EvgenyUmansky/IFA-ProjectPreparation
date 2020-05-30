package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TeamCoachTest {

    TeamCoach coach;

    @BeforeEach
    public void init(){
        coach = new TeamCoach("coachUser","player@gmail.com");
    }

    @AfterEach
    public void finish(){
        coach = null;
    }

    @Test
    void updateDetails() {
        assertNull(coach.getRole());
        assertNull(coach.getQualification());
        coach.updateDetails("qua","role");
        assertNotNull(coach.getRole());
        assertNotNull(coach.getQualification());
    }

    @Test
    void getCoachByName(){

        TeamCoach teamCoach = TeamCoach.getCoachByName("coachUser");
        assertNotNull(teamCoach);

    }


    @Test
    void setRole() {
        assertNull(coach.getRole());
        coach.setRole("role");
        assertEquals("role",coach.getRole());
    }


    @Test
    void getAlertsMessages() {
        ArrayList<Notification> notifications = coach.getNotifications();
        assertNotNull(notifications);
    }




    @Test
    void setQualification() {
        assertNull(coach.getQualification());
        coach.setQualification("qua");
        assertEquals("qua",coach.getQualification());

    }
}