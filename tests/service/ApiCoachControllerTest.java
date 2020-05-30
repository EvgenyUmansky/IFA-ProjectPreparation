package service;

import DataAccess.TeamCoachDBAccess;
import domain.TeamCoach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.UserDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApiCoachControllerTest {
    ApiCoachController controller;
    TeamCoachDBAccess teamCoachDBAccess;
    UserDTO userDTO;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiCoachController();
        teamCoachDBAccess = TeamCoachDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        userDTO = null;
    }

    @Test
    void getCoaches() {
        String[] conditions = new String[2];
        conditions[0] = "teamname";
        conditions[1] = "null";
        ArrayList<TeamCoach> coachesTest = new ArrayList<>(teamCoachDBAccess.conditionedSelect(conditions).values());;
        ArrayList<TeamCoach> coachesAPI = controller.getCoaches(true);

        for(int i = 0; i < coachesTest.size(); i++){
            assertEquals(coachesTest.get(i).getUserName(), coachesAPI.get(i).getUserName());
        }

    }
}