package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LeaguePerSeasonTest {
    LeaguePerSeason leaguePerSeason1;
    LeaguePerSeason leaguePerSeason2;


    @BeforeEach
    public void init(){
        leaguePerSeason1 = new LeaguePerSeason(2020, new OneGameSchedulingMethod(),new RankingMethod());
        leaguePerSeason2 = new LeaguePerSeason(2020, new TwoGameSchedulingMethod(), new RankingMethod());
    }

    @AfterEach
    public void finish(){
        leaguePerSeason1 = null;
        leaguePerSeason2 = null;
    }

    @Test
    void initializedTeamsInLeaguePerSeason() {
        assertEquals(true, leaguePerSeason1.initializedTeamsInLeaguePerSeason(new HashSet<>()));
        assertEquals(false, leaguePerSeason1.initializedTeamsInLeaguePerSeason(null));
    }

    @Test
    void scheduledGames() {
//        leaguePerSeason1.scheduledGames();
//        leaguePerSeason2.scheduledGames();
//        assertEquals(leaguePerSeason1.getGamesInLeaguePerSeason().size(), leaguePerSeason2.getGamesInLeaguePerSeason().size()*2);
       leaguePerSeason1.setBegin(false);
        assertEquals(true, leaguePerSeason1.scheduledGames());
        leaguePerSeason1.setBegin(true);
        assertEquals(false, leaguePerSeason1.scheduledGames());

    }


//TODO:
    @Test
    void updateTableOfTheLeague() {
    }



    @Test
    void addReferee() {
        assertEquals(false, leaguePerSeason1.addReferee(null));
    }

    @Test
    void setBegin() {
        leaguePerSeason1.setBegin(false);
        assertEquals(true, leaguePerSeason1.scheduledGames());
        leaguePerSeason1.setBegin(true);
        assertEquals(false, leaguePerSeason1.scheduledGames());
    }

    @Test
    void setReferees() {
        assertEquals(false, leaguePerSeason1.setReferees(null));
//        assertEquals(true, leaguePerSeason1.addReferee(new HashSet<Referee>());

    }

    @Test
    void setTeamsInLeaguePerSeasonTable() {
        assertEquals(false, leaguePerSeason1.setTeamsInLeaguePerSeasonTable(null));
        assertEquals(true, leaguePerSeason1.setTeamsInLeaguePerSeasonTable(new HashMap<>()));

    }

//    @Test
//    void getSeason() {
//    }
//
//    @Test
//    void getSchedulingMethod() {
//    }
//
//    @Test
//    void setRankingMethod() {
//    }
//
//    @Test
//    void isBegin() {
//    }
//
//    @Test
//    void setSchedulingMethod() {
//    }
//
//    @Test
//    void getRankingMethod() {
//    }
//
//    @Test
//    void getTeamsInLeaguePerSeason() {
//    }
//
//    @Test
//    void getGamesInLeaguePerSeason() {
//    }
}