package service;

import DataAccess.GameDBAccess;
import domain.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.GameDTO;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ApiGameControllerTest {
    ApiGameController controller;
    GameDBAccess gameDBAccess;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiGameController();
        gameDBAccess = GameDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        controller = null;
        gameDBAccess = null;
    }

    @Test
    void getGames() {
        ArrayList<Game> gameDB = new ArrayList<>(gameDBAccess.conditionedSelect(new String[0]).values());
        ArrayList<GameDTO> gameAPI = controller.getGames();

        for(int i = 0; i < gameDB.size(); i++){
            assertEquals(gameDB.get(i).getId(), gameAPI.get(i).getGameId());
        }
    }

    @Test
    void getRefereeGames() {
    }

    @Test
    void getGame() {
        Game gameDB = gameDBAccess.select("1000");
        GameDTO gameAPI = controller.getGame("1000");

        assertEquals(gameDB.getId(), gameAPI.getGameId());
    }

    @Test
    void addGameEventToGame() {
    }

    @Test
    void addFanSubscriptionToGame() {
    }
}