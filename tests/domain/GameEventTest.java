package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameEventTest {

    GameEvent gameEvent;
    @BeforeEach
    public void insert() {
        gameEvent = new GameEvent("2020-01-01 11:11", 29, GameAlert.INJURY, "Messi is wounded");
    }

    @AfterEach
    public void delete(){
        gameEvent = null;
    }

    @Test
    void getDateTime() {
        assertEquals("2020-01-01T11:11", gameEvent.getDateTime().toString());
    }

    @Test
    void setGameDate() {
        gameEvent.setGameDate("2012-11-11 12:12");
        assertEquals("2012-11-11T12:12", gameEvent.getDateTime().toString());
    }


    @Test
    void setGameDate2() {
        LocalDateTime localDateTime= LocalDateTime.now();
        gameEvent.setGameDate( localDateTime);
        assertEquals( localDateTime, gameEvent.getDateTime());

    }

    @Test
    void getGameMinutes() {
        assertEquals(29, gameEvent.getGameMinutes());
    }

    @Test
    void setGameMinutes() {
        gameEvent.setGameMinutes(41);
        assertEquals(41, gameEvent.getGameMinutes());
    }

    @Test
    void getEventName() {
        assertEquals(GameAlert.INJURY.toString(), gameEvent.getEventName().toString());
    }

    @Test
    void setEventName() {
        gameEvent.setEventName(GameAlert.GOAL);
        assertEquals(GameAlert.GOAL.toString(), gameEvent.getEventName().toString());
    }

    @Test
    void getDescription() {
        assertEquals("Messi is wounded", gameEvent.getDescription());
    }

    @Test
    void setDescription() {
        gameEvent.setDescription("bla bla bla");
        assertEquals("bla bla bla", gameEvent.getDescription());
    }

    @Test
    void testToString() {
        assertEquals("2020-01-01T11:11 29, INJURY Messi is wounded", gameEvent.toString());
    }
}