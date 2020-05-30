package domain.controllers;

import DataAccess.*;
import domain.*;
import javafx.util.Pair;
import org.junit.jupiter.api.*;
import service.pojos.TeamDTO;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamControllerTest {

    TeamController teamController = new TeamController();
    DBAccess<User> uda = UserDBAccess.getInstance();
    DBAccess<Field> fda = FieldDBAccess.getInstance();
    DBAccess<Team> tda = TeamDBAccess.getInstance();
    DBAccess<Pair<String, String>> tfda = TeamFieldsDBAccess.getInstance();
    DBAccess<TeamOwner> oda = OwnerDBAccess.getInstance();
    DBAccess<TeamPlayer> pda = TeamPlayerDBAccess.getInstance();
    DBAccess<TeamManager> mda = ManagerDBAccess.getInstance();
    DBAccess<TeamCoach> cda = TeamCoachDBAccess.getInstance();
    DBAccess<Pair<String, ArrayList<String>>> urda = UserRolesDBAccess.getInstance();

    @BeforeAll
    void setUp() {
        uda.save(new User("user1", "pass1", "name1", "mail1@mail.com"));
        uda.save(new User("user2", "pass2", "name2", "mail2@mail.com"));
        uda.save(new User("user3", "pass3", "name3", "mail3@mail.com"));
        uda.save(new User("user4", "pass4", "name4", "mail4@mail.com"));
        uda.save(new User("user5", "pass5", "name5", "mail5@mail.com"));

        pda.save(new TeamPlayer("user1", "mail1@mail.com", new Date(), "CDM", "6"));
        pda.save(new TeamPlayer("user2", "mail2@mail.com", new Date(), "CAM", "8"));

        cda.save(new TeamCoach("user3", "mail3@mail.com", "main coach", "qualification3", "name3"));
        mda.save(new TeamManager("user4", "mail4@mail.com", "name4"));

        TeamOwner owner1 = new TeamOwner("user5", "mail5@mail.com", "name5");
        oda.save(owner1);

        ArrayList<String> user1Roles = new ArrayList<>();
        ArrayList<String> user2Roles = new ArrayList<>();
        ArrayList<String> user3Roles = new ArrayList<>();
        ArrayList<String> user4Roles = new ArrayList<>();
        ArrayList<String> user5Roles = new ArrayList<>();

        user1Roles.add("FAN");
        user2Roles.add("FAN");
        user3Roles.add("FAN");
        user4Roles.add("FAN");
        user5Roles.add("FAN");

        user1Roles.add("TEAM_PLAYER");
        user2Roles.add("TEAM_PLAYER");
        user3Roles.add("COACH");
        user4Roles.add("TEAM_MANAGER");
        user5Roles.add("TEAM_OWNER");

        urda.save(new Pair<>("user1", user1Roles));
        urda.save(new Pair<>("user2", user2Roles));
        urda.save(new Pair<>("user3", user3Roles));
        urda.save(new Pair<>("user4", user4Roles));
        urda.save(new Pair<>("user5", user5Roles));


        Field field1 = new Field("field1", 10000);
        fda.save(field1);
        Field field2 = new Field("field2", 10500);
        Field field3 = new Field("field3", 10600);
        fda.save(field2);
        fda.save(field3);
        fda.save(new Field("field4", 177800));
        Team team1 = new Team("team1", field1, owner1);
        tda.save(team1);

        tfda.save(new Pair<>("team1", "field1"));
    }

    @AfterAll
    void tearDown() {
        tfda.delete(new Pair<>("team1", "field1"));
        tda.delete(new Team("team1", null, null));
        fda.delete(new Field("field1", 0));
        fda.delete(new Field("field2", 0));
        fda.delete(new Field("field3", 0));
        fda.delete(new Field("field4", 0));

        oda.delete(new TeamOwner("user4", null, null));
        mda.delete(new TeamManager("user4", null, null));
        cda.delete(new TeamCoach("user3", "mail3@mail.com", "main coach", "qualification3", "name3"));
        pda.delete(new TeamPlayer("user1", "mail1@mail.com", null, "CDM", "6"));
        pda.delete(new TeamPlayer("user2", "mail2@mail.com", null, "CAM", "8"));

        ArrayList<String> user1Roles = new ArrayList<>();
        ArrayList<String> user2Roles = new ArrayList<>();
        ArrayList<String> user3Roles = new ArrayList<>();
        ArrayList<String> user4Roles = new ArrayList<>();
        ArrayList<String> user5Roles = new ArrayList<>();

        user1Roles.add("FAN");
        user2Roles.add("FAN");
        user3Roles.add("FAN");
        user4Roles.add("FAN");
        user5Roles.add("FAN");

        user1Roles.add("TEAM_PLAYER");
        user2Roles.add("TEAM_PLAYER");
        user3Roles.add("COACH");
        user4Roles.add("TEAM_MANAGER");
        user5Roles.add("TEAM_OWNER");

        urda.delete(new Pair<>("user1", user1Roles));
        urda.delete(new Pair<>("user2", user2Roles));
        urda.delete(new Pair<>("user3", user3Roles));
        urda.delete(new Pair<>("user4", user4Roles));
        urda.delete(new Pair<>("user5", user5Roles));

        uda.delete(new User("user1", "pass1", "name1", "mail1@mail.com"));
        uda.delete(new User("user2", "pass2", "name2", "mail2@mail.com"));
        uda.delete(new User("user3", "pass3", "name3", "mail3@mail.com"));
        uda.delete(new User("user4", "pass4", "name4", "mail4@mail.com"));
        uda.delete(new User("user5", "pass5", "name5", "mail5@mail.com"));


    }

    @Test
    void createTeam() {
        uda.save(new User("user6", "pass6", "name6", "mail6@mail.com"));
        ArrayList<String> roles = new ArrayList<>();
        roles.add("Fan");
        roles.add("Team_Owner");
        urda.save(new Pair<>("user6", roles));

        uda.save(new User("user7", "pass7", "name7", "mail7@mail.com"));
        roles = new ArrayList<>();
        roles.add("Fan");
        roles.add("Coach");
        cda.save(new TeamCoach("user7", "mail7@mail.com", "main coach", "qualification7", "name7"));
        urda.save(new Pair<>("user7", roles));

//        TeamOwner owner6 = new TeamOwner("user6", "mail6@mail.com", "name6");
//        oda.save(owner6);
//        Field field2 = new Field("field2", 10500);
//        Team team2 = new Team("team2",field2,owner6);
//        tda.save(team2);
//        tfda.save(new Pair<>("team2","field2"));

        uda.save(new User("user11", "pass11", "name11", "mail11@mail.com"));
        uda.save(new User("user12", "pass12", "name12", "mail12@mail.com"));
        uda.save(new User("user13", "pass13", "name13", "mail13@mail.com"));
        uda.save(new User("user14", "pass14", "name14", "mail14@mail.com"));
        uda.save(new User("user15", "pass15", "name15", "mail15@mail.com"));
        uda.save(new User("user16", "pass16", "name16", "mail16@mail.com"));
        uda.save(new User("user17", "pass17", "name17", "mail17@mail.com"));
        uda.save(new User("user18", "pass18", "name18", "mail18@mail.com"));
        uda.save(new User("user19", "pass19", "name19", "mail19@mail.com"));
        uda.save(new User("user20", "pass20", "name20", "mail20@mail.com"));
        uda.save(new User("user21", "pass21", "name21", "mail121@mail.com"));
        TeamPlayer player11 = new TeamPlayer("user11", "mail11@mail.com");
        TeamPlayer player12 = new TeamPlayer("user12", "mail12@mail.com");
        TeamPlayer player13 = new TeamPlayer("user13", "mail13@mail.com");
        TeamPlayer player14 = new TeamPlayer("user14", "mail14@mail.com");
        TeamPlayer player15 = new TeamPlayer("user15", "mail15@mail.com");
        TeamPlayer player16 = new TeamPlayer("user16", "mail16@mail.com");
        TeamPlayer player17 = new TeamPlayer("user17", "mail17@mail.com");
        TeamPlayer player18 = new TeamPlayer("user18", "mail18@mail.com");
        TeamPlayer player19 = new TeamPlayer("user19", "mail19@mail.com");
        TeamPlayer player20 = new TeamPlayer("user20", "mail120@mail.com");
        TeamPlayer player21 = new TeamPlayer("user21", "mail121@mail.com");

        pda.save(player11);
        pda.save(player12);
        pda.save(player13);
        pda.save(player14);
        pda.save(player15);
        pda.save(player16);
        pda.save(player17);
        pda.save(player18);
        pda.save(player19);
        pda.save(player20);
        pda.save(player21);

        String[] players = new String[]{"user11", "user12", "user13", "user14", "user15", "user16", "user17", "user18", "user19", "user20", "user21"};
        teamController.createTeam("team2", "field2", "user7", players, "user6");

        player11 = pda.select("user11");
        player12 = pda.select("user12");
        player13 = pda.select("user13");
        player14 = pda.select("user14");
        player15 = pda.select("user15");
        player16 = pda.select("user16");
        player17 = pda.select("user17");
        player18 = pda.select("user18");
        player19 = pda.select("user19");
        player20 = pda.select("user20");
        player21 = pda.select("user21");

        Team team2Test = tda.select("team2");
        assertNotNull(team2Test);
        assertEquals("team2", team2Test.getTeamName());
        assertEquals("field2", team2Test.getStadium().getFieldName());
        assertNotEquals(0, team2Test.getOwners().size());

        assertEquals("team2",player11.getCurrentTeam());
        assertEquals("team2",player12.getCurrentTeam());
        assertEquals("team2",player13.getCurrentTeam());
        assertEquals("team2",player14.getCurrentTeam());
        assertEquals("team2",player15.getCurrentTeam());
        assertEquals("team2",player16.getCurrentTeam());
        assertEquals("team2",player17.getCurrentTeam());
        assertEquals("team2",player18.getCurrentTeam());
        assertEquals("team2",player19.getCurrentTeam());
        assertEquals("team2",player20.getCurrentTeam());
        assertEquals("team2",player21.getCurrentTeam());
    }

    @Test
    void getTeams() {
        ArrayList<TeamDTO> teams = teamController.getTeams();
        assertEquals(2, teams.size());


        //TODO: more checks
    }

   /* @Test
    void getTeamDetails() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void addCoach() {
    }

    @Test
    void addField() {
    }

    @Test
    void addManager() {
    }

    @Test
    void addOwner() {
    }*/

    /*@Test
    void removePlayer() {
    }

    @Test
    void removeCoach() {
    }

    @Test
    void removeField() {
    }*/

    /*@Test
    void removeOwner() {
    }*/

    /*@Test
    void removeManager() {
    }*/

   /* @Test
    void closeTeam() {
    }

    @Test
    void openTeam() {
    }

    @Test
    void manageFinance() {
    }

    @Test
    void setPermissionsToManager() {
    }

    @Test
    void setRulesForBudgetControl() {
    }*/

  /*  @Test
    void setTeamBudget() {
    }*/

    /*@Test
    void getTeamByName() {
    }*/
}