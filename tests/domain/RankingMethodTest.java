package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankingMethodTest {
    RankingMethod rankingMethod;
    @BeforeEach
    public void init(){
        rankingMethod = new RankingMethod();
    }

    @AfterEach
    public void finish(){
        rankingMethod = null;
    }


    @Test
    void setWinPoints() {
        assertEquals(true, rankingMethod.setWinPoints(2));
        assertEquals(false, rankingMethod.setWinPoints(1));
        assertEquals(false, rankingMethod.setWinPoints(0));
        assertEquals(false, rankingMethod.setWinPoints(-1));
    }

    @Test
    void setLoosPoints() {
        assertEquals(false, rankingMethod.setLoosPoints(2));
        assertEquals(false, rankingMethod.setLoosPoints(3));
        assertEquals(true, rankingMethod.setLoosPoints(-1));
    }

    @Test
    void setDrawPoints() {
        assertEquals(false, rankingMethod.setDrawPoints(3));
        assertEquals(true, rankingMethod.setDrawPoints(2));
        assertEquals(false, rankingMethod.setDrawPoints(-1));
    }

//    @Test
//    void getWinPoints() {
//        assertEquals(3,rankingMethod.getWinPoints());
//    }
//
//    @Test
//    void getLoosPoints() {
//        assertEquals(0,rankingMethod.getLoosPoints());
//
//    }
//
//    @Test
//    void getDrawPoints() {
//        assertEquals(1,rankingMethod.getDrawPoints());
//
//    }
}