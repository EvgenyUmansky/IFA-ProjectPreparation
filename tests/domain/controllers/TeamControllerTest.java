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
    DBAccess< Pair<String, ArrayList<String>>> urda = UserRolesDBAccess.getInstance();

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

        urda.save(new Pair<>("user1",user1Roles));
        urda.save(new Pair<>("user2",user2Roles));
        urda.save(new Pair<>("user3",user3Roles));
        urda.save(new Pair<>("user4",user4Roles));
        urda.save(new Pair<>("user5",user5Roles));


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
        tda.delete(new Team("team1",null,null));
        fda.delete(new Field("field1",0));
        fda.delete(new Field("field2",0));
        fda.delete(new Field("field3",0));
        fda.delete(new Field("field4",0));

        oda.delete(new TeamOwner("user4",null,null));
        mda.delete(new TeamManager("user4", null, null));
        cda.delete(new TeamCoach("user3", "mail3@mail.com", "main coach", "qualification3", "name3"));
        pda.delete(new TeamPlayer("user1", "mail1@mail.com", null, "CDM", "6"));
        pda.delete(new TeamPlayer("user2", "mail2@mail.com",null, "CAM", "8"));

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

        urda.delete(new Pair<>("user1",user1Roles));
        urda.delete(new Pair<>("user2",user2Roles));
        urda.delete(new Pair<>("user3",user3Roles));
        urda.delete(new Pair<>("user4",user4Roles));
        urda.delete(new Pair<>("user5",user5Roles));

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
        urda.save(new Pair<>("user6",roles));

//        TeamOwner owner6 = new TeamOwner("user6", "mail6@mail.com", "name6");
//        oda.save(owner6);
//        Field field2 = new Field("field2", 10500);
//        Team team2 = new Team("team2",field2,owner6);
//        tda.save(team2);
//        tfda.save(new Pair<>("team2","field2"));


        TeamDTO team2Test = teamController.createTeam("user6","team2","field2");
        assertNotNull(team2Test);
        assertEquals("team2",team2Test.getTeamName());
        assertEquals("field2",team2Test.getStadium().getFieldName());
        assertNotEquals(0,team2Test.getOwners().size());
    }

    @Test
    void getTeams() {
        ArrayList<TeamDTO> teams = teamController.getTeams();
        assertEquals(2,teams.size());

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