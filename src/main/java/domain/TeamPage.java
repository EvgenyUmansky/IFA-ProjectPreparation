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

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Stadium of the team " + getName() + "  has changed", "The new stadium is " + stadium.getFieldName()));
    }
    public TeamPage setPlayers(HashMap<String,TeamPlayer> players) {
        this.players = players;

        // UC 3.2
        String playersStr = "";
        for(String player : players.keySet()){
            playersStr += player + ", ";
        }
        getAlert().sendAlert(new AlertNotification("Players of the team " + getName() + "  have changed", "The new players are " + playersStr.replaceFirst(", $", "")));
        return this;
    }

    public TeamPage setCoaches(HashMap<String,TeamCoach> coaches) {
        this.coaches = coaches;

        // UC 3.2
        String coachesStr = "";
        for(String coach : coaches.keySet()){
            coachesStr += coach + ", ";
        }

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Coaches of the team " + getName() + "  have changed", "The new coaches are " + coachesStr.replaceFirst(", $", "")));

        return this;
    }

    public TeamPage setManagers(HashMap<String,TeamManager> managers) {
        this.managers = managers;

        // UC 3.2
        String managersStr = "";
        for(String manager : managers.keySet()){
            managersStr += manager + ", ";
        }

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Managers of the team " + getName() + "  have changed", "The new managers are " + managersStr.replaceFirst(", $", "")));


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
