package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.LeagueDBAccess;
import DataAccess.LeagueSeasonDBAccess;
import domain.League;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LeagueControllerTest {

    LeagueController leagueController = new LeagueController();
    private DBAccess<League> lda = LeagueDBAccess.getInstance();
    private DBAccess<ArrayList<League>> lsda = LeagueSeasonDBAccess.getInstance();

    @BeforeEach
    void setUp() {
        lda.save(new League("league1",2020));
        lda.save(new League("league2",2020));
        lda.save(new League("league3",2020));
        lda.save(new League("league4",2020));
    }

    @AfterEach
    void tearDown() {
        lda.delete(new League("league1",2020));
        lda.delete(new League("league2",2020));
        lda.delete(new League("league3",2020));
        lda.delete(new League("league4",2020));
    }

    @Test
    void getLeagues() {
        ArrayList<League> leagues = leagueController.getLeagues();

        assertTrue(leagues.size() > 0);
        for(League league : leagues){
            assertNotNull(league);
        }
    }

    @Test
    void createSeason() {
        String scheduling = "oneGameSchedulingMethod";
        String winPoints = "3", losePoints = "0", drawPoints = "2",season = "2021";
        String leagueName = "league1";

        leagueController.createSeason(leagueName,season,scheduling,winPoints,losePoints,drawPoints);

        String[] conditions = new String[]{"leagueName","league1","year","2021"};
        League newSeason = lda.conditionedSelect(conditions).get("league1");
        assertNotNull(newSeason);
        assertEquals("league1",newSeason.getLeagueName());
        assertEquals(2021,newSeason.getSeason());
        assertEquals(3,newSeason.getRankingMethod().getWinPoints());
        assertEquals(0,newSeason.getRankingMethod().getLosePoints());
        assertEquals(2,newSeason.getRankingMethod().getDrawPoints());
        assertEquals("oneGameSchedulingMethod", newSeason.getSchedulingMethod().getClass().getName());
    }

    @Test
    void getAllSeasonsInLeagues() {
        ArrayList<League> allLeaguesAndSeasons = leagueController.getAllSeasonsInLeagues();
        assertTrue(allLeaguesAndSeasons.size() > 0);
    }
}