package service;

import DataAccess.LeagueDBAccess;
import domain.League;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApiLeagueControllerTest {
    private LeagueDBAccess leagueDBAccess;
    ApiLeagueController controller;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiLeagueController();
        leagueDBAccess = LeagueDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        controller = null;
        leagueDBAccess = null;
    }

    @Test
    void getLeagueDetails() {
        ArrayList<League> leaguesDB = new ArrayList<>(leagueDBAccess.conditionedSelect(new String[0]).values());
        ArrayList<League> leaguesAPI = controller.getLeagueDetails();

        for(int i = 0; i < leaguesDB.size(); i++){
            assertEquals(leaguesDB.get(i).getLeagueName(), leaguesAPI.get(i).getLeagueName());
        }
    }

}