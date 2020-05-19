package domain;

import java.util.List;

/**
 * Represents different methods of scheduling games in the system
 */
public interface SchedulingMethod  {

    /**
     * Schedules teams into games
     * @param league the league in which the teams take part
     * @param leagueTeams the list of teams
     * @return a list of games between the set of teams
     */
    List<Game> scheduleGamePolicy(League league, Team[] leagueTeams);
}
