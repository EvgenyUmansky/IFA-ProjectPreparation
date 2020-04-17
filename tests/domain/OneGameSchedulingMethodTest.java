package domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OneGameSchedulingMethodTest {
    SchedulingMethod schedulingMethod = new OneGameSchedulingMethod();
    LeaguePerSeason leaguePerSeason = new LeaguePerSeason(2020, new OneGameSchedulingMethod(),new RankingMethod());
    Team[]leagueTeams = new Team[3];


    @Test
    void scheduleGamePolicy() {
        HashMap teamOwner = new HashMap<>();
      //  teamOwner.put("abc", new TeamOwner("abc", "aa", "abc", "sdf"));
        HashMap teamOwner2 = new HashMap<>();
      //  teamOwner2.put("def", new TeamOwner("def", "dd", "def", "efsfd"));
      //  leagueTeams[0] = new Team("FCB", teamOwner);
      //  leagueTeams[1] = new Team("Real", teamOwner2);
      //  leagueTeams[2] = new Team("Kissra", teamOwner2);
        assertEquals(3 , schedulingMethod.scheduleGamePolicy(leaguePerSeason, leagueTeams).size());

    }
}