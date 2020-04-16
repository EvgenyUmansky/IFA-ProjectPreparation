package domain;

import java.util.ArrayList;
import java.util.List;


public class TwoGameSchedulingMethod implements SchedulingMethod{

    /**
     * leagueTeams[i] = first Team |  leagueTeams[j] = second Team
     * @param league
     * @param leagueTeams
     * @return list of games where each team plays against every team in the league two times | Home-Away schedule game policy
     */
    public List<Game> scheduleGamePolicy(League league, Team[]leagueTeams) {
        List<Game> gamesList = new ArrayList<Game>();
        for(int i = 0; i < leagueTeams.length - 1; i++){
            for (int j = 1; j < leagueTeams.length; j++){
                if (!leagueTeams[i].equals(leagueTeams[j]) ) {
                    Game firstGame = new Game(league, leagueTeams[i], leagueTeams[j], leagueTeams[i].getMyField(), "2016-11-09 11:44", new ArrayList<>());
                    Game secondGame = new Game(league, leagueTeams[j], leagueTeams[i], leagueTeams[j].getMyField(), "2016-11-09 11:44", new ArrayList<>());
                    if (!gamesList.contains(firstGame) && !gamesList.contains(secondGame)) {
                        gamesList.add(firstGame);
                        gamesList.add(secondGame);
                    }
                }
            }
        }
        return gamesList;
    }
}
