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
    void setRankingMethod() {
        assertEquals(true, rankingMethod.setRankingMethod(2, 1, 0));
        assertEquals(false, rankingMethod.setRankingMethod(1,rankingMethod.getDrawPoints(), rankingMethod.getLosePoints()));
        assertEquals(false, rankingMethod.setRankingMethod(0,rankingMethod.getDrawPoints(), rankingMethod.getLosePoints()));
        assertEquals(false, rankingMethod.setRankingMethod(-1,rankingMethod.getDrawPoints(), rankingMethod.getLosePoints()));

        rankingMethod = new RankingMethod();
        assertEquals(true, rankingMethod.setRankingMethod(rankingMethod.getWinPoints(),2, rankingMethod.getLosePoints()));
        assertEquals(false, rankingMethod.setRankingMethod(rankingMethod.getWinPoints(),3, rankingMethod.getLosePoints()));
        assertEquals(false, rankingMethod.setRankingMethod(rankingMethod.getWinPoints(),0, rankingMethod.getLosePoints()));

        rankingMethod = new RankingMethod();
        assertEquals(true, rankingMethod.setRankingMethod(rankingMethod.getWinPoints(),rankingMethod.getDrawPoints(),-1));
        assertEquals(false, rankingMethod.setRankingMethod(rankingMethod.getWinPoints(),rankingMethod.getDrawPoints(), 1));
        assertEquals(false, rankingMethod.setRankingMethod(rankingMethod.getWinPoints(),rankingMethod.getDrawPoints(), 3));

    }



}