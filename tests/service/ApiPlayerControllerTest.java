package service;

import DataAccess.TeamPlayerDBAccess;
import domain.TeamPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApiPlayerControllerTest {
    ApiPlayerController controller;
    TeamPlayerDBAccess playerDBAccess;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiPlayerController();
        playerDBAccess = TeamPlayerDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        controller = null;
        playerDBAccess = null;
    }

    @Test
    void getPlayers() {
        String[] conditions = new String[2];
        conditions[0] = "teamname";
        conditions[1] = "null";
        ArrayList<TeamPlayer> playersTest = new ArrayList<>(playerDBAccess.conditionedSelect(conditions).values());;
        ArrayList<TeamPlayer> playersAPI = controller.getPlayers(true);

        for(int i = 0; i < playersTest.size(); i++){
            assertEquals(playersTest.get(i).getUserName(), playersAPI.get(i).getUserName());
        }

    }
}