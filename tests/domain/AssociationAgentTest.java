package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class AssociationAgentTest {
    AssociationAgent assAgent;
    @Before
    public void setUp() throws Exception {
        assAgent = new AssociationAgent("Guy Ronaldo", "GuyMail@Revivo.com", false);
        String leaguename = "euroLeague";
        int leaguequalificationOk = 3;
        int leaguequalificationFail = 7;
        HashSet<League> leagues = new HashSet<>();
        League euroleague = new League(leaguename,leaguequalificationOk);
    }

    @After
    public void tearDown() throws Exception {
        assAgent = null;
    }

    @Test
    public void setLeague() {
        String leaguename = "euroLeague";
        int leaguequalificationOk = 3;
        HashSet<League> leagues  = new HashSet<>();
        assertEquals(0,leagues.size());
        assAgent.setLeague(leaguename,leaguequalificationOk,leagues);
        assertEquals(1,leagues.size());
        int leaguequalificationFail = 7;
        assAgent.setLeague(leaguename,leaguequalificationFail,leagues);
        assertEquals(1,leagues.size());

    }

    @Test
    public void setSeasonToLeague() {
        int year = 2020;
        SchedulingMethod schedulingMethod = new OneGameSchedulingMethod();
        RankingMethod rankingMethod = new RankingMethod();
        HashSet<League> leagues = new HashSet<>();
        assertEquals(0,leagues.size());
   //need complete after delete leagueperseason
    }

    @Test
    public void addReferee() {
        String userName = "guy";
        String password = "12345678";
        String name = "Guy";
        String mail = "guy@gmail.com";
        boolean isMail = false;
        int qualification = 10;
        RefereeType refereeType = RefereeType.ASSISTANT;
        User user = new User(true, userName, password, name, mail);
        assertEquals(false, user.getRoles().containsKey("Referee"));
        assAgent.addReferee(userName, password, name, mail, false, 10, refereeType, user);
        assertEquals(true, user.getRoles().containsKey("Referee"));
    }
    @Test
    public void removeReferee() {
        String userName = "guy";
        String password = "12345678";
        String name = "Guy";
        String mail = "guy@gmail.com";
        boolean isMail = false;
        int qualification = 10;
        RefereeType refereeType = RefereeType.ASSISTANT;
        User user = new User(true, userName, password, name, mail);
        assAgent.addReferee(userName, password, name, mail, false, 10, refereeType, user);
        assertEquals(true, user.getRoles().containsKey("Referee"));
        assAgent.removeReferee(userName, user);
        assertEquals(false, user.getRoles().containsKey("Referee"));
    }

    @Test
    public void addRefereeToLeagueBySeason() {
        //update after removing leagueperseason
    }

    @Test
    public void setRankingMethod() {
        int winP = 3;
        int loseP = -1;
        int drawP = 2;
        SchedulingMethod schedulingMethod = new OneGameSchedulingMethod();
        RankingMethod rankingMethod = new RankingMethod();
        LeaguePerSeason lps = new LeaguePerSeason(2020,new HashSet<Team>(),schedulingMethod,rankingMethod);
        assertEquals(3,lps.getRankingMethod().getWinPoints());
        assertEquals(0,lps.getRankingMethod().getLoosPoints());
        assertEquals(1,lps.getRankingMethod().getDrawPoints());
        assAgent.setRankingMethod(winP,loseP,drawP,lps);
        assertEquals(3,lps.getRankingMethod().getWinPoints());
        assertEquals(-1,lps.getRankingMethod().getLoosPoints());
        assertEquals(2,lps.getRankingMethod().getDrawPoints());
    }

    @Test
    public void setSchedulingMethod() {
        SchedulingMethod schedulingMethodone = new OneGameSchedulingMethod();
        SchedulingMethod schedulingMethodtwo = new TwoGameSchedulingMethod();

        RankingMethod rankingMethod = new RankingMethod();
        LeaguePerSeason lps = new LeaguePerSeason(2020,new HashSet<Team>(),schedulingMethodone,rankingMethod);
        assertEquals(true,lps.getSchedulingMethod() instanceof OneGameSchedulingMethod);
        assAgent.setSchedulingMethod(lps, schedulingMethodtwo);
        assertEquals(true,lps.getSchedulingMethod() instanceof TwoGameSchedulingMethod);

    }

    @Test
    public void startScheduleMethod() {
    }
}