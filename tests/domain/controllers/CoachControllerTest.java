package domain.controllers;

import DataAccess.*;
import domain.TeamCoach;
import domain.TeamPlayer;
import domain.User;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CoachControllerTest {

    CoachController coachController = new CoachController();
    DBAccess<TeamCoach> cda = TeamCoachDBAccess.getInstance();
    DBAccess<User> uda = UserDBAccess.getInstance();
    DBAccess<Pair<String, ArrayList<String>>> urda = UserRolesDBAccess.getInstance();


    @BeforeEach
    void setUp() {
        uda.save(new User("user11", "pass11", "name11", "mail11@mail.com"));
        uda.save(new User("user12", "pass12", "name12", "mail12@mail.com"));
        uda.save(new User("user13", "pass13", "name13", "mail13@mail.com"));
        uda.save(new User("user14", "pass14", "name14", "mail14@mail.com"));
        uda.save(new User("user15", "pass15", "name15", "mail15@mail.com"));
        uda.save(new User("user16", "pass16", "name16", "mail16@mail.com"));
        uda.save(new User("user17", "pass17", "name17", "mail17@mail.com"));
        uda.save(new User("user18", "pass18", "name18", "mail18@mail.com"));
        uda.save(new User("user19", "pass19", "name19", "mail19@mail.com"));

        TeamCoach coach11 = new TeamCoach("user11", "mail11@mail.com");
        TeamCoach coach12 = new TeamCoach("user12", "mail12@mail.com");
        TeamCoach coach13 = new TeamCoach("user13", "mail13@mail.com");
        TeamCoach coach14 = new TeamCoach("user14", "mail14@mail.com");
        TeamCoach coach15 = new TeamCoach("user15", "mail15@mail.com");
        TeamCoach coach16 = new TeamCoach("user16", "mail16@mail.com");
        TeamCoach coach17 = new TeamCoach("user17", "mail17@mail.com");
        TeamCoach coach18 = new TeamCoach("user18", "mail18@mail.com");
        TeamCoach coach19 = new TeamCoach("user19", "mail19@mail.com");

        coach11.setCurrentTeam(null);
        coach12.setCurrentTeam("team1");
        coach13.setCurrentTeam(null);
        coach14.setCurrentTeam(null);
        coach15.setCurrentTeam("team1");
        coach16.setCurrentTeam("team1");
        coach17.setCurrentTeam("team1");
        coach18.setCurrentTeam("team1");
        coach19.setCurrentTeam(null);

        cda.save(coach11);
        cda.save(coach12);
        cda.save(coach13);
        cda.save(coach14);
        cda.save(coach15);
        cda.save(coach16);
        cda.save(coach17);
        cda.save(coach18);
        cda.save(coach19);

        ArrayList<String> coachRole = new ArrayList<>();
        coachRole.add("Coach");
        urda.save(new Pair<>("user11",coachRole));
        urda.save(new Pair<>("user12",coachRole));
        urda.save(new Pair<>("user13",coachRole));
        urda.save(new Pair<>("user14",coachRole));
        urda.save(new Pair<>("user15",coachRole));
        urda.save(new Pair<>("user16",coachRole));
        urda.save(new Pair<>("user17",coachRole));
        urda.save(new Pair<>("user18",coachRole));
        urda.save(new Pair<>("user19",coachRole));
    }

    @AfterEach
    void tearDown() {
        ArrayList<String> coachRole = new ArrayList<>();
        coachRole.add("Coach");
        urda.delete(new Pair<>("user11",coachRole));
        urda.delete(new Pair<>("user12",coachRole));
        urda.delete(new Pair<>("user13",coachRole));
        urda.delete(new Pair<>("user14",coachRole));
        urda.delete(new Pair<>("user15",coachRole));
        urda.delete(new Pair<>("user16",coachRole));
        urda.delete(new Pair<>("user17",coachRole));
        urda.delete(new Pair<>("user18",coachRole));
        urda.delete(new Pair<>("user19",coachRole));

        TeamCoach coach11 = new TeamCoach("user11", "mail11@mail.com");
        TeamCoach coach12 = new TeamCoach("user12", "mail12@mail.com");
        TeamCoach coach13 = new TeamCoach("user13", "mail13@mail.com");
        TeamCoach coach14 = new TeamCoach("user14", "mail14@mail.com");
        TeamCoach coach15 = new TeamCoach("user15", "mail15@mail.com");
        TeamCoach coach16 = new TeamCoach("user16", "mail16@mail.com");
        TeamCoach coach17 = new TeamCoach("user17", "mail17@mail.com");
        TeamCoach coach18 = new TeamCoach("user18", "mail18@mail.com");
        TeamCoach coach19 = new TeamCoach("user19", "mail19@mail.com");

        cda.delete(coach11);
        cda.delete(coach12);
        cda.delete(coach13);
        cda.delete(coach14);
        cda.delete(coach15);
        cda.delete(coach16);
        cda.delete(coach17);
        cda.delete(coach18);
        cda.delete(coach19);

        uda.delete(new User("user11", "pass11", "name11", "mail11@mail.com"));
        uda.delete(new User("user12", "pass12", "name12", "mail12@mail.com"));
        uda.delete(new User("user13", "pass13", "name13", "mail13@mail.com"));
        uda.delete(new User("user14", "pass14", "name14", "mail14@mail.com"));
        uda.delete(new User("user15", "pass15", "name15", "mail15@mail.com"));
        uda.delete(new User("user16", "pass16", "name16", "mail16@mail.com"));
        uda.delete(new User("user17", "pass17", "name17", "mail17@mail.com"));
        uda.delete(new User("user18", "pass18", "name18", "mail18@mail.com"));
        uda.delete(new User("user19", "pass19", "name19", "mail19@mail.com"));
    }

    @Test
    void getAvailableCoaches() {
        ArrayList<TeamCoach> availableCoaches = coachController.getAvailableCoaches();
        assertEquals(4,availableCoaches.size());

        for(TeamCoach coach : availableCoaches){
            assertNull(coach.getCurrentTeam());
        }
    }
}