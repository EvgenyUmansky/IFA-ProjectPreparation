package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTest {
    League league1 = new League("Israeli Premier League", 2);
    League league2 = new League("LaLiga", 5);
    League league3 = new League("premier league", 5);
    @Test
    void setLeaguePerSeasons() {

    }

    @Test
    void addLeaguePerSeason() {
//        league1.addLeaguePerSeason(new LeaguePerSeason(league1, new Season(), ));
    }

    @Test
    void setLeagueQualification() {
        //must be between 1 and 5
        league1.setLeagueQualification(4);
        assertEquals(4 , league1.getLeagueQualification());
        league1.setLeagueQualification(10);
        assertEquals(4,league1.getLeagueQualification());
        league1.setLeagueQualification(-10);
        assertEquals(4,league1.getLeagueQualification());
    }

    @Test
    void getLeagueSeasons() {
        assertEquals(0, league3.getLeagueSeasons().size());
//        league3.addLeaguePerSeason(new LeaguePerSeason());
    }

    @Test
    void getLeagueName() {
        assertEquals("Israeli Premier League", league1.getLeagueName());
        assertNotEquals("La-Liga", league2.getLeagueName());
    }

    @Test
    void getLeagueQualification() {
        assertEquals(2 , league1.getLeagueQualification());
        league1.setLeagueQualification(4);
        assertEquals(4,league1.getLeagueQualification());
    }
}