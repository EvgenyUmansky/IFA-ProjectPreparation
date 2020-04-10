package domain;

import domain.Season;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeasonTest {
    Season season1 = new Season();
    Season season2 = new Season(1998);


    @Test
    public void getYear () {
        assertEquals(2020, season1.getYear());
        assertEquals(1998, season2.getYear());
        assertNotEquals(2021, season1.getYear());
        assertNotEquals(1999, season2.getYear());
    }
}