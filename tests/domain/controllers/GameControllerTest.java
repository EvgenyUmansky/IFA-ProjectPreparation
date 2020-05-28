package domain.controllers;

import DataAccess.*;
import domain.Game;
import domain.Team;
import domain.TeamCoach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.GameDTO;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    GameController gameController;
    private AlertDBAccess ada = AlertDBAccess.getInstance();
    private NotificationDBAccess nda = NotificationDBAccess.getInstance();
    private GameEventDBAccess geda = GameEventDBAccess.getInstance();

    @BeforeEach
    public void insertBeforeTest() {
        gameController = new GameController();
        ada = AlertDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        gameController = null;
    }

    @Test
    void getGames() {
    }

    @Test
    void getGame() {
    }

    @Test
    void addFanSubscriptionToGame() {
    }

    @Test
    void getRefereeGames() {
        ArrayList<GameDTO> games = gameController.getRefereeGames("Referee_1");
        assertEquals(1000, games.get(0).getGameId());
        assertEquals("Team_1", games.get(0).getHostTeam());
        assertEquals("Team_2", games.get(0).getGuestTeam());
        assertEquals("Field_1", games.get(0).getField().getFieldName());
        assertEquals("0:0", games.get(0).getGameScore());
    }

    @Test
    void addGameEventToGame() {
        GameDTO gameDTO = gameController.addGameEventToGame("1000", "25", "GOAL", "addGameEventToGame test");
        assertEquals(1000, gameDTO.getGameId());
        assertEquals("Team_1", gameDTO.getHostTeam());
        assertEquals("Team_2", gameDTO.getGuestTeam());
        assertEquals("Field_1", gameDTO.getField().getFieldName());
        assertEquals("0:0", gameDTO.getGameScore());
    }

    @Test
    void changeGameEvent() {
    }
}