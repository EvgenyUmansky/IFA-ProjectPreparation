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

    public boolean setStadium(Field stadium, String user) {
        if(pageOwners.containsKey(user)){
            this.stadium = stadium;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean setPlayers(HashMap<String,TeamPlayer> players , String user) {
        if(pageOwners.containsKey(user)){
            this.players = players;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean setCoaches(HashMap<String,TeamCoach> coaches, String user) {
        if(pageOwners.containsKey(user)){
            this.coaches = coaches;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setManagers(HashMap<String,TeamManager> managers, String user) {

        if(pageOwners.containsKey(user)){
            this.managers = managers;
            return true;
        }
        else{
            return false;
        }

    }
}
