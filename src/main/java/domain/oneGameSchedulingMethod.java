package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class oneGameSchedulingMethod implements SchedulingMethod {

    /**
     * leagueTeams[i] = first Team |  leagueTeams[j] = second Team
     * @param leaguePerSeason
     * @param leagueTeams
     * @return list of games where each team plays against every team in the league only one time
     */
    public List<Game> scheduleGamePolicy(LeaguePerSeason leaguePerSeason, Team[] leagueTeams) {
        List<Game> gamesList = new ArrayList<Game>();
        for(int i = 0; i < leagueTeams.length; i++){
            for (int j = 1; j < leagueTeams.length; j++){
                Game firstGame = new Game(leaguePerSeason, leagueTeams[i], leagueTeams[j], leagueTeams[i].getMyField(), "", new ArrayList<>());
                gamesList.add(firstGame);

            }
        }
        return gamesList;
    }
}
