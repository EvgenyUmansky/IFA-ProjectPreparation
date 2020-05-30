package service;

import DataAccess.TeamDBAccess;
import domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.TeamDTO;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ApiTeamControllerTest {

    private TeamDBAccess teamDBAccess;
    ApiTeamController controller;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiTeamController();
        teamDBAccess = TeamDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        controller = null;
        teamDBAccess = null;
    }

    @Test
    void getTeams() {
        ArrayList<Team> teamsDB = new ArrayList<>(teamDBAccess.conditionedSelect(new String[0]).values());
        ArrayList<TeamDTO> teamsAPI = controller.getTeams();

        for(int i = 0; i < teamsDB.size(); i++){
            assertEquals(teamsDB.get(i).getTeamName(), teamsAPI.get(i).getTeamName());
        }
    }

    @Test
    void getTeamDetails() {
        assertEquals("Arsenal", controller.getTeamDetails("Arsenal").getTeamName());
        assertEquals("Field_15", controller.getTeamDetails("Arsenal").getStadium().getFieldName());
    }

}