package domain;

import java.util.List;

public interface SchedulingMethod  {

    public List<Game> scheduleGamePolicy(League league, Team[] leagueTeams);
}
