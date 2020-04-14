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

    protected void setStadium(Field stadium, String user) {
        this.stadium = stadium;
    }

    protected void setPlayers(HashMap<String,TeamPlayer> players , String user) {
        this.players = players;
    }

    protected void setCoaches(HashMap<String,TeamCoach> coaches, String user) {
        this.coaches = coaches;
    }

    protected void setManagers(HashMap<String,TeamManager> managers, String user) {
        this.managers = managers;


    }
}
