//package domain;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TeamCoachTest {
//
//    TeamCoach coach;
//
//    @BeforeEach
//    public void init(){
//        coach = new TeamCoach("coachUser","player@gmail.com",true);
//    }
//
//    @AfterEach
//    public void finish(){
//        coach = null;
//    }
//
//    @Test
//    void updateDetails() {
//        assertNull(coach.getRole());
//        assertNull(coach.getQualification());
//        assertTrue(coach.updateDetails("qua","role"));
//        assertNotNull(coach.getRole());
//        assertNotNull(coach.getQualification());
//    }
//
//    @Test
//    void setRole() {
//        assertNull(coach.getRole());
//        coach.setRole("role");
//        assertEquals("role",coach.getRole());
//    }
//
//    @Test
//    void setQualification() {
//        assertNull(coach.getQualification());
//        coach.setQualification("qua");
//        assertEquals("qua",coach.getQualification());
//
//    }
//}