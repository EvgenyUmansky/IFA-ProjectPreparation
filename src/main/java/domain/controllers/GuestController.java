package domain.controllers;

import domain.League;
import domain.Team;
import domain.TeamCoach;
import domain.TeamPlayer;

import java.util.ArrayList;

public class GuestController {
    // ========================= Guest functions ============================
    // ====================================================================


    /**
     * UC 2.4
     * Returns the team instance by the team's name
     * @param teamName the team's name
     * @return the team instance by the team's name
     */
    public Team getTeamDetails(String teamName) {
        return Team.getTeamByName(teamName);
    }


    /**
     * Returns the player instance by his name
     * @param playerName the player's name
     * @return the player instance by his name
     */
    public TeamPlayer getPlayersDetails(String playerName) {
        return TeamPlayer.getPlayerByName(playerName);
    }


    /**
     * Returns the coach instance by his name
     * @param coachName the player's name
     * @return the coach instance by his name
     */
    public TeamCoach getCoachDetails(String coachName) {
        return TeamCoach.getCoachByName(coachName);
    }


    /**
     * Returns the league instance that matches the league name
     * @param leagueName the league name
     * @return the league instance that matches the league name
     */
    public League getLeagueDetails(String leagueName) {
        return League.getLeagueByName(leagueName);
    }


    /**
     * Returns the leagues instances from a certain season
     * @param year the season
     * @return the leagues instances from the season
     */
    public ArrayList<League> getSeasonDetails(int year) {
        return League.getAllLeaguesPerSeason(year);
    }


    /**
     * UC 2.5
     *
     * @param words
     */
    public void searchByKeyWord(String words) {

    }
}
