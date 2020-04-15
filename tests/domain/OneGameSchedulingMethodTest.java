package domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OneGameSchedulingMethodTest {
    SchedulingMethod schedulingMethod = new OneGameSchedulingMethod();
    LeaguePerSeason leaguePerSeason = new LeaguePerSeason(2020, new OneGameSchedulingMethod(),new RankingMethod());
    Team[]leagueTeams = new Team[3];


    @Test
    void scheduleGamePolicy() {
//        HashMap teamOwner = new HashMap<>();
//        teamOwner.put("abc", new TeamOwner("abc", "aa", "abc", "sdf"));
//        HashMap teamOwner2 = new HashMap<>();
//        teamOwner2.put("def", new TeamOwner("def", "dd", "def", "efsfd"));
        leagueTeams[0] = new Team("FCB", new Field("Barca-Field", 5000), new TeamOwner("abc", "aa", true));
        leagueTeams[1] = new Team("Real", new Field("Real-Field", 5000), new TeamOwner("def", "bb", true));
        leagueTeams[2] = new Team("Kissra", new Field("Kissra-Field", 5000), new TeamOwner("ghk", "cc", true));


        assertEquals(3 , schedulingMethod.scheduleGamePolicy(leaguePerSeason, leagueTeams).size());
        leaguePerSeason.initializedTeamsInLeaguePerSeason(new HashSet<>(Arrays.asList(leagueTeams)));

        int x =10;
    }
}