package domain;

import java.util.List;

public interface SchedulingMethod  {

    public List<Game> scheduleGamePolicy(LeaguePerSeason leaguePerSeason, Team[] leagueTeams);
}
