package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
///////////////////////////
class LeagueTest {
    League leaguePerSeason1;
    League leaguePerSeason2;
    League leaguePerSeason3;
    Team[]leagueTeams;


    @BeforeEach
    public void init(){
        leaguePerSeason1 = new League(2020, new OneGameSchedulingMethod(),new RankingMethod(),"aleph");
        leaguePerSeason2 = new League(2020, new TwoGameSchedulingMethod(), new RankingMethod(), "bet");
        leaguePerSeason3 = new League(2020, new HashSet<Team>(),new TwoGameSchedulingMethod(), new RankingMethod(), "bet");


        leagueTeams = new Team[3];
        leagueTeams[0] = new Team("FCB", new Field("Barca-Field", 5000), new TeamOwner("abc", "aa"));
        leagueTeams[1] = new Team("Real", new Field("Real-Field", 5000), new TeamOwner("def", "bb"));
        leagueTeams[2] = new Team("Kissra", new Field("Kissra-Field", 5000), new TeamOwner("ghk", "cc"));

        leaguePerSeason1.initTeams(new HashSet<>(Arrays.asList(leagueTeams)));
        leaguePerSeason2.initTeams(new HashSet<>(Arrays.asList(leagueTeams)));
    }

    @AfterEach
    public void finish(){
        leaguePerSeason1 = null;
        leaguePerSeason2 = null;
        leagueTeams = null;
    }


    @Test
    void getLeagueByName() {
        assertNotNull(League.getLeagueByName("alef"));
    }

    /////////
    @Test
    void initTeams() {
        leaguePerSeason3 = new League(2020, new HashSet<Team>(),new TwoGameSchedulingMethod(), new RankingMethod(), "bet");
        assertEquals(true, leaguePerSeason1.initTeams(new HashSet<>()));
        assertEquals(false, leaguePerSeason1.initTeams(null));
    }

    @Test
    void scheduledGames() {
        leaguePerSeason1.setBegin(false);
        assertEquals(true, leaguePerSeason1.scheduledGames());

        assertEquals(3, leaguePerSeason1.getGames().size());

        leaguePerSeason1.setBegin(true);
        assertEquals(false, leaguePerSeason1.scheduledGames());


        leaguePerSeason2.setBegin(false);
        leaguePerSeason2.scheduledGames();
        assertEquals(6, leaguePerSeason2.getGames().size());
    }




    @Test
    void updateTableOfTheLeague() {
        Game firstGame = new Game(leaguePerSeason1, leagueTeams[0], leagueTeams[1], new Field("asadfs", 500), "2016-11-09 11:44", new ArrayList<>());
        firstGame.setGuestTeamScore(0);//REAL
        firstGame.setHostTeamScore(1);// FCB

        HashMap<Team, Integer> tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(3, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(0, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));


        tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(6, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(0, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));
    }



    @Test
    void updateTableOfTheLeague2() {
        Game firstGame = new Game(leaguePerSeason1, leagueTeams[0], leagueTeams[1], new Field("asadfs", 500), "2016-11-09 11:44", new ArrayList<>());
        firstGame.setGuestTeamScore(1);//REAL
        firstGame.setHostTeamScore(0);// FCB

        HashMap<Team, Integer> tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(0, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(3, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));


        tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(0, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(6, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));
    }



    @Test
    void updateTableOfTheLeague3() {
        Game firstGame = new Game(leaguePerSeason1, leagueTeams[0], leagueTeams[1], new Field("asadfs", 500), "2016-11-09 11:44", new ArrayList<>());
        firstGame.setGuestTeamScore(1);//REAL
        firstGame.setHostTeamScore(1);// FCB

        HashMap<Team, Integer> tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(1, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(1, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));


        tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(2, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(2, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));
    }



    @Test
    void addReferee() {
        assertEquals(false, leaguePerSeason1.addReferee(null));
        Referee referee = new Referee("itzik","mail.Gmail.com");
        assertEquals(true, leaguePerSeason1.addReferee(referee));
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
        assertEquals(true, leaguePerSeason1.setReferees(new HashSet<Referee>()));

    }

    @Test
    void setTeamsTable() {
        assertEquals(false, leaguePerSeason1.setTeamsTable(null));
        assertEquals(true, leaguePerSeason1.setTeamsTable(new HashMap<>()));
    }

    @Test
    void setSeason() {
        leaguePerSeason2.setSeason(2000);
        assertEquals(2000, leaguePerSeason2.getSeason());
    }

    @Test
    void getSeason() {
    int year = leaguePerSeason1.getSeason();
    assertEquals(2020,year);
    }

    @Test
    void getSchedulingMethod() {
        SchedulingMethod schedulingMethod = leaguePerSeason1.getSchedulingMethod();
        assertNotNull(schedulingMethod);
    }

    @Test
    void setRankingMethod() {
        RankingMethod rankingMethod = new RankingMethod();
        leaguePerSeason1.setRankingMethod(rankingMethod);
        assertTrue(leaguePerSeason1.getRankingMethod().equals(rankingMethod));
    }

    @Test
    void isBegin() {
        assertFalse(leaguePerSeason1.isBegin());
    }

    @Test
    void setSchedulingMethod() {
        SchedulingMethod schedulingMethod = new OneGameSchedulingMethod();
        leaguePerSeason1.setSchedulingMethod(schedulingMethod);
        assertTrue(leaguePerSeason1.getSchedulingMethod().equals(schedulingMethod));
    }

    @Test
    void getRankingMethod() {
        assertNotNull(leaguePerSeason1.getRankingMethod());
    }

    @Test
    void getTeamsInLeaguePerSeason() {
        assertNotNull(leaguePerSeason1.getTeamsInLeaguePerSeason());

    }



    @Test
    void getGames() {
        assertNotNull(leaguePerSeason1.getGames());
    }







    @Test
    void getLeagueName() {
        assertEquals("aleph",leaguePerSeason1.getLeagueName());
    }



    @Test
    void getReferees() {
        assertNotNull(leaguePerSeason1.getReferees());
    }


    //// static functions
    @Test
    void getLeaguePerSeason() {
        assertNotNull(League.getLeaguePerSeason(2020,"alef"));
    }

    @Test
    void getAllLeaguesPerSeason() {
        assertNotNull(League.getAllLeaguesPerSeason(2020));
    }



}