package domain;

import java.util.HashMap;

/**
 * This class represents a team's profile page in the system
 */
public class TeamPage extends PersonalPage {

    private Field stadium;
    private HashMap<String,TeamPlayer> players;
    private HashMap<String,TeamCoach> coaches;
    private HashMap<String,TeamManager> managers;

    /**
     * Constructor
     * @param team the team that owns the page
     */
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


    // Getters and Setters

    /**
     * UC 3.2
     * Updates the stadium of the team on the page and notifies the subscribers
     * @param stadium the team's stadium
     */
    public void setStadium(Field stadium) {
        this.stadium = stadium;
        getAlert().sendAlert(new Notification("Stadium of the team " + getName() + "  has changed. The new stadium is " + stadium.getFieldName()));
    }


    /**
     * UC 3.2
     * Updates the set of players in the team on the page and notifies the subscribers
     * @param players the players that play for the team
     * @return the updated page
     */
    public TeamPage setPlayers(HashMap<String,TeamPlayer> players) {
        this.players = players;
        String playersStr = "";
        for(String player : players.keySet()){
            playersStr += player + ", ";
        }
        getAlert().sendAlert(new Notification("Players of the team " + getName() + "  have changed. The new players are " + playersStr.replaceFirst(", $", "")));
        return this;
    }


    /**
     * UC 3.2
     * Updates the set of coaches in the team on the page and notifies the subscribers
     * @param coaches the coaches that coach in the team
     * @return the updated page
     */
    public TeamPage setCoaches(HashMap<String,TeamCoach> coaches) {
        this.coaches = coaches;
        String coachesStr = "";
        for(String coach : coaches.keySet()){
            coachesStr += coach + ", ";
        }

        getAlert().sendAlert(new Notification("Coaches of the team " + getName() + "  have changed. The new coaches are " + coachesStr.replaceFirst(", $", "")));
        return this;
    }


    /**
     * UC 3.2
     * Updates the set of managers in the team on the page and notifies the subscribers
     * @param managers the managers that manage the team
     * @return the updated page
     */
    public TeamPage setManagers(HashMap<String,TeamManager> managers) {
        this.managers = managers;
        String managersStr = "";
        for(String manager : managers.keySet()){
            managersStr += manager + ", ";
        }

        getAlert().sendAlert(new Notification("Managers of the team " + getName() + "  have changed. The new managers are " + managersStr.replaceFirst(", $", "")));
        return this;
    }

    /**
     * Returns the team's stadium
     * @return the team's stadium
     */
    public Field getStadium() {
        return stadium;
    }

    /**
     * Returns a HashMap with the players in the team
     * @return a HashMap with the players in the team (Key - player's name, value - player object)
     */
    public HashMap<String, TeamPlayer> getPlayers() {
        return players;
    }

    /**
     * Returns a HashMap with the coaches in the team
     * @return a HashMap with the coaches in the team (Key - coach's name, value - coach object)
     */
    public HashMap<String, TeamCoach> getCoaches() {
        return coaches;
    }

    /**
     * Returns a HashMap with the managers in the team
     * @return a HashMap with the managers in the team (Key - manager's name, value - manager object)
     */
    public HashMap<String, TeamManager> getManagers() {
        return managers;
    }
}
