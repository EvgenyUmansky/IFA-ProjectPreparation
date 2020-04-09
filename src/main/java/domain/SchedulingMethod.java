package domain;

import java.util.List;
import java.util.Set;

public interface SchedulingMethod  {

    public List<Game> scheduleGamePolicy(LeaguePerSeason leaguePerSeason, Team[] leagueTeams);
}
