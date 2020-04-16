package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaguePerSeasonTest {
    LeaguePerSeason leaguePerSeason1;
    LeaguePerSeason leaguePerSeason2;
    Team[]leagueTeams;


    @BeforeEach
    public void init(){
        leaguePerSeason1 = new LeaguePerSeason(2020, new OneGameSchedulingMethod(),new RankingMethod());
        leaguePerSeason2 = new LeaguePerSeason(2020, new TwoGameSchedulingMethod(), new RankingMethod());


        leagueTeams = new Team[3];
        leagueTeams[0] = new Team("FCB", new Field("Barca-Field", 5000), new TeamOwner("abc", "aa", true));
        leagueTeams[1] = new Team("Real", new Field("Real-Field", 5000), new TeamOwner("def", "bb", true));
        leagueTeams[2] = new Team("Kissra", new Field("Kissra-Field", 5000), new TeamOwner("ghk", "cc", true));

        leaguePerSeason1.initializedTeamsInLeaguePerSeason(new HashSet<>(Arrays.asList(leagueTeams)));
        leaguePerSeason2.initializedTeamsInLeaguePerSeason(new HashSet<>(Arrays.asList(leagueTeams)));
    }

    @AfterEach
    public void finish(){
        leaguePerSeason1 = null;
        leaguePerSeason2 = null;
        leagueTeams = null;
    }

    @Test
    void initializedTeamsInLeaguePerSeason() {
        assertEquals(true, leaguePerSeason1.initializedTeamsInLeaguePerSeason(new HashSet<>()));
        assertEquals(false, leaguePerSeason1.initializedTeamsInLeaguePerSeason(null));
    }

    @Test
    void scheduledGames() {
       leaguePerSeason1.setBegin(false);
        assertEquals(true, leaguePerSeason1.scheduledGames());

        assertEquals(3, leaguePerSeason1.getGamesInLeaguePerSeason().size());

        leaguePerSeason1.setBegin(true);
        assertEquals(false, leaguePerSeason1.scheduledGames());


        leaguePerSeason2.setBegin(false);
        leaguePerSeason2.scheduledGames();
        assertEquals(6, leaguePerSeason2.getGamesInLeaguePerSeason().size());
    }


//TODO:
    @Test
    void updateTableOfTheLeague() {
        //LeaguePerSeason leaguePerSeason, Team hostTeam, Team guestTeam, Field field, String gameDateStr, ArrayList<Referee> referees
        Game firstGame = new Game(leaguePerSeason1, leagueTeams[0], leagueTeams[1], new Field("asadfs", 500), "2016-11-09 11:44", new ArrayList<>());
        firstGame.setGuestTeamScore(0);//REAL
        firstGame.setHostTeamScore(1);// FCB

        HashMap <Team, Integer> tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(3, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(0, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));


        tableLeague = leaguePerSeason1.updateTableOfTheLeague(firstGame);
        assertEquals(6, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[0]));
        assertEquals(0, leaguePerSeason1.getTeamsInLeaguePerSeason().get(leagueTeams[1]));

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