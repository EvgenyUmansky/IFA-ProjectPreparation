package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTest {
    League league1;
    League league2;
    League league3;

    @BeforeEach
    public void init(){
        league1 = new League("Israeli Premier League", 2);
        league2 = new League("LaLiga", 5);
        league3 = new League("premier league", 5);
    }

    @AfterEach
    public void finish(){
        league1 = null;
        league2 = null;
        league3 =  null;
    }



    @Test
    void setLeaguePerSeasons() {
        assertEquals(false,league1.setLeaguePerSeasons(null));
        Set<LeaguePerSeason> leagues = new LinkedHashSet<>();
        SchedulingMethod sm = new OneGameSchedulingMethod();
        RankingMethod rm = new RankingMethod();
        leagues.add(new LeaguePerSeason(1998, sm, rm));
        assertEquals(true, league1.setLeaguePerSeasons(leagues));
        assertEquals(1,league1.getLeagueSeasons().size());

    }

    @Test
    void addLeaguePerSeason() {
        SchedulingMethod sm = new OneGameSchedulingMethod();
        RankingMethod rm = new RankingMethod();
        LeaguePerSeason lps = new LeaguePerSeason(1998, sm, rm);
        assertEquals(0,league1.getLeagueSeasons().size());
        assertEquals(true,league1.addLeaguePerSeason(lps));
        assertEquals(false,league1.addLeaguePerSeason(null));
    }

    @Test
    void setLeagueQualification() {
        //must be between 1 and 5
        assertEquals(true , league1.setLeagueQualification(4));
        assertEquals(false,league1.setLeagueQualification(10));
        assertEquals(false,league1.setLeagueQualification(-10));
    }

//    @Test
//    void getLeagueSeasons() {
//        assertEquals(0, league3.getLeagueSeasons().size());
////        league3.addLeaguePerSeason(new LeaguePerSeason());
//    }
//
//    @Test
//    void getLeagueName() {
//        assertEquals("Israeli Premier League", league1.getLeagueName());
//        assertNotEquals("La-Liga", league2.getLeagueName());
//    }
//
//    @Test
//    void getLeagueQualification() {
//        assertEquals(2 , league1.getLeagueQualification());
//        league1.setLeagueQualification(4);
//        assertEquals(4,league1.getLeagueQualification());
//    }
}