package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankingMethodTest {
    RankingMethod rankingMethod = new RankingMethod();
    @Test
    void setWinPoints() {
        rankingMethod.setWinPoints(4);
        assertEquals(4,rankingMethod.getWinPoints());
    }

    @Test
    void setLoosPoints() {
        rankingMethod. setLoosPoints(4);
        assertEquals(4,rankingMethod.getLoosPoints());
    }

    @Test
    void setDrawPoints() {
        rankingMethod.setDrawPoints(4);
        assertEquals(4,rankingMethod.getDrawPoints());
    }

    @Test
    void getWinPoints() {
        assertEquals(3,rankingMethod.getWinPoints());
    }

    @Test
    void getLoosPoints() {
        assertEquals(0,rankingMethod.getLoosPoints());

    }

    @Test
    void getDrawPoints() {
        assertEquals(1,rankingMethod.getDrawPoints());

    }
}