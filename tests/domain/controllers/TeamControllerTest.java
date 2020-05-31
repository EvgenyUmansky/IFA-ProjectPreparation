package domain.controllers;

import DataAccess.*;
import domain.*;
import javafx.util.Pair;
import org.junit.jupiter.api.*;
import service.pojos.TeamDTO;

import java.util.ArrayList;

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

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }



    @Test
    void getTeams() {
        ArrayList<TeamDTO> teams = teamController.getTeams();
        assertTrue(teams.size() >0);
    }
}