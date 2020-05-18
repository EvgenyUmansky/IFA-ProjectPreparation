package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a matches scheduling method by playing each team once in a season against any of the other teams in the league
 */
public class OneGameSchedulingMethod implements SchedulingMethod {

    /**
     * leagueTeams[i] = first Team |  leagueTeams[j] = second Team
     * @param league the league the games are played in
     * @param leagueTeams the set of teams that play in the league
     * @return list of games where each team plays against every team in the league only one time
     */
    @Override
    public List<Game> scheduleGamePolicy(League league, Team[] leagueTeams) {
        List<Game> gamesList = new ArrayList<Game>();
        for(int i = 0; i < leagueTeams.length - 1; i++){
            for (int j = 1; j < leagueTeams.length; j++) {
                if (!leagueTeams[i].equals(leagueTeams[j]) ){
                    Game firstGame = new Game(league, leagueTeams[i], leagueTeams[j], leagueTeams[i].getStadium(), "2016-11-09 11:44", new ArrayList<>());
                    if (!gamesList.contains(firstGame)) {
                        gamesList.add(firstGame);
                    }
               }
            }
        }
        return gamesList;
    }
}
