package domain;

import java.util.HashMap;

public class TeamPage extends PersonalPage {

    private Field stadium;
    private HashMap<String,TeamPlayer> players;
    private HashMap<String,TeamCoach> coaches;
    private HashMap<String,TeamManager> managers;

    public TeamPage(Team team) {
        super(team.getTeamName(), team.getTeamEmail());
        this.stadium = team.getStadium();
        this.players = team.getPlayers();
        this.coaches = team.getCoaches();
        this.managers = team.getManagers();

        //Give permissions to the page for all the team's managers
        for (String manager : managers.keySet()) {
            addPermissions(managers.get(manager));
        }
    }

    public void setStadium(Field stadium) {
        this.stadium = stadium;

    }
    public TeamPage setPlayers(HashMap<String,TeamPlayer> players) {
        this.players = players;
        return this;
    }

    public TeamPage setCoaches(HashMap<String,TeamCoach> coaches) {
        this.coaches = coaches;
        return this;
    }

    public TeamPage setManagers(HashMap<String,TeamManager> managers) {
        this.managers = managers;
        return this;
    }

    public Field getStadium() {
        return stadium;
    }

    public HashMap<String, TeamPlayer> getPlayers() {
        return players;
    }

    public HashMap<String, TeamCoach> getCoaches() {
        return coaches;
    }

    public HashMap<String, TeamManager> getManagers() {
        return managers;
    }
}
