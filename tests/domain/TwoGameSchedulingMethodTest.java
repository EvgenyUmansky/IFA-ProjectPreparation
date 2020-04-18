package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TwoGameSchedulingMethodTest {
    SchedulingMethod schedulingMethod;
    League leaguePerSeason;
    Team[]leagueTeams;



    @BeforeEach
    public void init(){
        schedulingMethod = new TwoGameSchedulingMethod();
        leaguePerSeason = new League(2020, schedulingMethod,new RankingMethod(), "alpha");
        leagueTeams = new Team[3];
        leagueTeams[0] = new Team("FCB", new Field("Barca-Field", 5000), new TeamOwner("abc", "aa"));
        leagueTeams[1] = new Team("Real", new Field("Real-Field", 5000), new TeamOwner("def", "bb"));
        leagueTeams[2] = new Team("Kissra", new Field("Kissra-Field", 5000), new TeamOwner("ghk", "cc"));
        leaguePerSeason.initTeams(new HashSet<>(Arrays.asList(leagueTeams)));

    }

    @AfterEach
    public void finish(){
        schedulingMethod = null;
        leaguePerSeason = null;
        leagueTeams =  null;
    }
    @Test
    void scheduleGamePolicy() {

        assertEquals(6 ,schedulingMethod.scheduleGamePolicy(leaguePerSeason, leagueTeams).size());
    }
}