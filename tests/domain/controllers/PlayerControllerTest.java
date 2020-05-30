package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.TeamPlayerDBAccess;
import DataAccess.UserDBAccess;
import DataAccess.UserRolesDBAccess;
import domain.Team;
import domain.TeamPlayer;
import domain.User;
import javafx.util.Pair;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    PlayerController playerController = new PlayerController();
    DBAccess<TeamPlayer> pda = TeamPlayerDBAccess.getInstance();
    DBAccess<User> uda = UserDBAccess.getInstance();
    DBAccess<Pair<String, ArrayList<String>>> urda = UserRolesDBAccess.getInstance();


    @BeforeAll
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

        TeamPlayer player11 = new TeamPlayer("user11", "mail11@mail.com");
        TeamPlayer player12 = new TeamPlayer("user12", "mail12@mail.com");
        TeamPlayer player13 = new TeamPlayer("user13", "mail13@mail.com");
        TeamPlayer player14 = new TeamPlayer("user14", "mail14@mail.com");
        TeamPlayer player15 = new TeamPlayer("user15", "mail15@mail.com");
        TeamPlayer player16 = new TeamPlayer("user16", "mail16@mail.com");
        TeamPlayer player17 = new TeamPlayer("user17", "mail17@mail.com");
        TeamPlayer player18 = new TeamPlayer("user18", "mail18@mail.com");
        TeamPlayer player19 = new TeamPlayer("user19", "mail19@mail.com");

        player11.setCurrentTeam(null);
        player12.setCurrentTeam("team1");
        player13.setCurrentTeam(null);
        player14.setCurrentTeam(null);
        player15.setCurrentTeam("team1");
        player16.setCurrentTeam("team1");
        player17.setCurrentTeam("team1");
        player18.setCurrentTeam("team1");
        player19.setCurrentTeam(null);

        pda.save(player11);
        pda.save(player12);
        pda.save(player13);
        pda.save(player14);
        pda.save(player15);
        pda.save(player16);
        pda.save(player17);
        pda.save(player18);
        pda.save(player19);

        ArrayList<String> playerRole = new ArrayList<>();
        playerRole.add("TEAM_PLAYER");

        urda.save(new Pair<>("user11",playerRole));
        urda.save(new Pair<>("user12",playerRole));
        urda.save(new Pair<>("user13",playerRole));
        urda.save(new Pair<>("user14",playerRole));
        urda.save(new Pair<>("user15",playerRole));
        urda.save(new Pair<>("user16",playerRole));
        urda.save(new Pair<>("user17",playerRole));
        urda.save(new Pair<>("user18",playerRole));
        urda.save(new Pair<>("user19",playerRole));
    }

    @AfterAll
    void tearDown() {
        ArrayList<String> playerRole = new ArrayList<>();
        playerRole.add("TEAM_PLAYER");
        urda.delete(new Pair<>("user11",playerRole));
        urda.delete(new Pair<>("user12",playerRole));
        urda.delete(new Pair<>("user13",playerRole));
        urda.delete(new Pair<>("user14",playerRole));
        urda.delete(new Pair<>("user15",playerRole));
        urda.delete(new Pair<>("user16",playerRole));
        urda.delete(new Pair<>("user17",playerRole));
        urda.delete(new Pair<>("user18",playerRole));
        urda.delete(new Pair<>("user19",playerRole));

        TeamPlayer player11 = new TeamPlayer("user11", "mail11@mail.com");
        TeamPlayer player12 = new TeamPlayer("user12", "mail12@mail.com");
        TeamPlayer player13 = new TeamPlayer("user13", "mail13@mail.com");
        TeamPlayer player14 = new TeamPlayer("user14", "mail14@mail.com");
        TeamPlayer player15 = new TeamPlayer("user15", "mail15@mail.com");
        TeamPlayer player16 = new TeamPlayer("user16", "mail16@mail.com");
        TeamPlayer player17 = new TeamPlayer("user17", "mail17@mail.com");
        TeamPlayer player18 = new TeamPlayer("user18", "mail18@mail.com");
        TeamPlayer player19 = new TeamPlayer("user19", "mail19@mail.com");

        pda.delete(player11);
        pda.delete(player12);
        pda.delete(player13);
        pda.delete(player14);
        pda.delete(player15);
        pda.delete(player16);
        pda.delete(player17);
        pda.delete(player18);
        pda.delete(player19);

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
    void getAvailablePlayers() {
        ArrayList<TeamPlayer> availablePlayers = playerController.getAvailablePlayers();
        assertEquals(4,availablePlayers.size());

        //TODO: more checks
    }
}