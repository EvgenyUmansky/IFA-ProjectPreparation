package domain;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a football team in the system
 */
public class Team {

    private String teamName;
    private Field stadium; //The team's main stadium, where they play official matches
    private HashMap<String, Field> fields; //All the team's fields, including the stadium and training fields
    private HashMap<String, TeamPlayer> players;
    private HashMap<String, TeamCoach> coaches;
    private HashMap<String, TeamManager> managers;
    private HashMap<String, TeamOwner> owners;
    private String teamEmail;
    private Budget budget;
    private PersonalPage teamPage;
    private TeamStatus teamStatus;
    private Alert alert;


    //========================= Constructor ========================//

    /**
     * Constructor
     *
     * @param name    the team's name
     * @param stadium the team's stadium
     * @param owner   the team's owner
     */
    public Team(String name, Field stadium, TeamOwner owner) {
        this.teamName = name;
        this.stadium = stadium;
        this.fields = new HashMap<>();
        this.owners = new HashMap<>();
        this.managers = new HashMap<>();
        this.coaches = new HashMap<>();
        this.players = new HashMap<>();
        this.alert = new Alert();
        this.alert.addSubscriber(owner);
        this.fields.put(stadium.getFieldName(), stadium);
        this.teamStatus = TeamStatus.Open;
        this.owners.put(owner.getUserName(), owner);
        owner.setTeam(this.teamName);
    }

    public Team(String teamName, String mail, String fieldName, String status) {
        this.teamName = teamName;
        this.teamEmail = mail;
        setTeamStatus(status);

        this.fields = new HashMap<>();
        fields.put(fieldName, null);

        this.owners = new HashMap<>();
        this.managers = new HashMap<>();
        this.coaches = new HashMap<>();
        this.players = new HashMap<>();
        this.alert = new Alert();
    }

    //========================= Getters and Setters ========================//

    /**
     * Returns a HashMap with the players in the team
     *
     * @return a HashMap with the players in the team (Key - player's name, value - player object)
     */
    public HashMap<String, TeamPlayer> getPlayers() {
        return players;
    }

    /**
     * Returns a HashMap with the coaches in the team
     *
     * @return a HashMap with the coaches in the team (Key - coach's name, value - coach object)
     */
    public HashMap<String, TeamCoach> getCoaches() {
        return coaches;
    }

    /**
     * Returns a HashMap with the managers in the team
     *
     * @return a HashMap with the managers in the team (Key - managers's name, value - manager object)
     */
    public HashMap<String, TeamManager> getManagers() {
        return managers;
    }

    /**
     * Returns the team's stadium
     *
     * @return the team's stadium
     */
    public Field getStadium() {
        return stadium;
    }

    /**
     * Returns the team's mail
     *
     * @return the team's mail
     */
    public String getTeamEmail() {
        return teamEmail;
    }

    /**
     * Updates the team's mail
     *
     * @param teamEmail the new team's mail
     */
    public void setTeamEmail(String teamEmail) {
        this.teamEmail = teamEmail;
    }

    /**
     * Returns the team's name
     *
     * @return the team's name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the player instance that matches the username
     *
     * @param userName the player's username
     * @return the player instance that matches the username
     */
    public TeamPlayer getPlayer(String userName) {
        return players.get(userName);
    }

    /**
     * Returns the coach instance that matches the username
     *
     * @param userName the player's username
     * @return the coach instance that matches the username
     */
    public TeamCoach getCoach(String userName) {
        return coaches.get(userName);
    }

    /**
     * Returns a HashMap with the fields of the team.
     * The key in the map is the field's name, and the field instance is the value
     *
     * @return a HashMap with the fields of the team
     */
    public HashMap<String, Field> getFields() {
        return fields;
    }

    /**
     * Returns a HashMap with the owners of the team.
     * The key in the map is the owner's username, and the owner instance is the value
     *
     * @return a HashMap with the owners of the team
     */
    public HashMap<String, TeamOwner> getOwners() {
        return owners;
    }

    /**
     * Returns the budget of the team
     *
     * @return the budget of the team
     */
    public Budget getBudget() {
        return budget;
    }

    /**
     * Returns the team's profile page
     *
     * @return the team's profile page
     */
    public PersonalPage getTeamPage() {
        return teamPage;
    }


    /**
     * Returns the team's status (open, temporarily closed, permanently closed)
     *
     * @return the team's status
     */
    public TeamStatus getTeamStatus() {
        return this.teamStatus;
    }


    public void setTeamStatus(String status) {
        switch (status) {
            case "Open":
                setTeamStatus(TeamStatus.Open);
                break;

            case "Temporarily Closed":
                setTeamStatus(TeamStatus.TempClose);
                break;

            case "Permanently Closed":
                setTeamStatus(TeamStatus.PermanentlyClose);
                break;

            default:
                break;
        }
    }

    public void setTeamStatus(TeamStatus status) {
        this.teamStatus = status;
    }

    /**
     * Updates the team's budget
     *
     * @param budget the team's budget
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Returns the notification system of the team
     *
     * @return the notification system of the team
     */
    public Alert getAlert() {
        return alert;
    }

    //========================= Functionality ========================//


    /**
     * UC 6.1
     * Adds a player to the team
     *
     * @param player the new player
     */
    public void addPlayer(TeamPlayer player) {
        player.setCurrentTeam(teamName);
        this.players.put(player.getUserName(), player);
        addSubscriber(player);
    }

    /**
     * UC 6.1
     * Adds a coach to the team
     *
     * @param coach the new coach
     */
    public void addCoach(TeamCoach coach) {
        coach.setCurrentTeam(teamName);
        this.coaches.put(coach.getUserName(), coach);
        addSubscriber(coach);
    }

    /**
     * UC 6.1
     * Adds a field to the team
     *
     * @param field the new field
     */
    public void addField(Field field) {
        this.fields.put(field.getFieldName(), field);
    }

    /**
     * UC 6.1
     * Removes a player from the team
     *
     * @param player the removed player
     */
    public void removePlayer(TeamPlayer player) {
        player.setCurrentTeam(null);
        this.players.remove(player.getUserName());
        removeSubscriber(player);
    }

    /**
     * UC 6.1
     * Removes a coach from the team
     *
     * @param coach the removed coach
     */
    public void removeCoach(TeamCoach coach) {
        coach.setCurrentTeam(null);
        this.coaches.remove(coach.getUserName());
        removeSubscriber(coach);
    }

    /**
     * UC 6.1
     * Removes a field from the team
     *
     * @param field the removed field
     */
    public void removeField(Field field) {
        this.fields.remove(field.getFieldName());
    }

    /**
     * UC 6.6
     * Closes a team temporarily or permanently, depends on the user
     *
     * @param user the user that closed the team
     */
    public void closeTeam(User user) {
        if (teamStatus == TeamStatus.Open || teamStatus == TeamStatus.TempClose) {
            if (user.getRoles().containsKey(Role.SYSTEM_ADMIN)) {
                teamStatus = TeamStatus.PermanentlyClose;
                alert.sendAlert(new AlertNotification("close team permanently", "you team close permanently"));
                return;
            }
        }
        if (teamStatus == TeamStatus.Open) {
            if (user.getRoles().containsKey(Role.TEAM_OWNER)) {
                teamStatus = TeamStatus.TempClose;
                alert.sendAlert(new AlertNotification("close team temporary", "you team close temporary"));
            }
        } else {
            throw new Error("This team is already closed");
        }
    }

    /**
     * UC 6.6
     * Reopens a team that was closed
     */
    public void openTeam() {
        if (teamStatus == TeamStatus.TempClose) {
            teamStatus = TeamStatus.Open;
            alert.sendAlert(new AlertNotification("open your team", "your team open again"));
        } else {
            throw new Error("This team can't be reopened");
        }
    }


    /**
     * UC 6.2
     * Adds an owner to the team
     *
     * @param owner the new owner
     */
    protected void addOwner(TeamOwner owner) {
        if (owner.getTeam() == null) {
            owner.setTeam(teamName);
        }
        this.owners.put(owner.getUserName(), owner);
        addSubscriber(owner);
    }

    /**
     * UC 6.2
     * Adds an owner to the team
     *
     * @param currentOwner the owner that adds the new owner
     * @param newOwner     the new owner
     */
    public void addOwner(User currentOwner, User newOwner) {
        if (teamStatus != TeamStatus.Open) {
            return;
        }
        if (this.owners.containsKey(currentOwner.getUserName())) {
            TeamOwner newTeamOwner = (TeamOwner) User.getUserByID(newOwner.getUserName()).getRoles().get(Role.TEAM_OWNER);
            this.addOwner(newTeamOwner);
        }
    }

    /**
     * UC 6.3
     * Removes an owner from the team
     *
     * @param user the removed owner
     */
    public void removeOwner(User user) {
        //Impossible to leave the Team without an Owner
        if (this.owners.size() <= 1) {
            throw new Error("The team cannot be left without an owner");
        } else {
            TeamOwner owner = (TeamOwner) user.getRoles().get(Role.TEAM_OWNER);
            this.owners.remove(user.getUserName());
            removeSubscriber(owner);
            HashSet<TeamOwner> ownerAppointments = owner.getOwnerAppointments();
            HashSet<TeamManager> managerAppointments = owner.getManagerAppointments();
            for (TeamOwner appointment : ownerAppointments) {
                removeOwner(User.getUserByID(appointment.getUserName()));
            }
            for (TeamManager appointment : managerAppointments) {
                removeManager(User.getUserByID(appointment.getUserName()));
            }
        }
    }


    /**
     * UC 6.4
     * Adds a manager to the team
     *
     * @param currentOwner the owner that adds the new manager
     * @param newManager   the new manager
     */
    public void addManager(User currentOwner, User newManager) {
        if (this.owners.containsKey(currentOwner.getUserName())) {
            TeamManager newTeamManager = (TeamManager) newManager.getRoles().get(Role.TEAM_MANAGER);
            newTeamManager.setCurrentTeam(teamName);
            this.managers.put(newTeamManager.getUserName(), newTeamManager);
            addSubscriber(newTeamManager);
        }
    }

    /*/
    This addManager function is for adding manager for mock UI tests
     */
    public void addManager(TeamManager newManager) {
        newManager.setCurrentTeam(teamName);
        this.managers.put(newManager.getUserName(), newManager);
        addSubscriber(newManager);
    }

    /**
     * UC 6.5
     * Removes a manager from the team
     *
     * @param managerUser the removed manager
     */
    public void removeManager(User managerUser) {
        TeamManager manager = (TeamManager) managerUser.getRoles().get(Role.TEAM_MANAGER);
        this.managers.remove(managerUser.getUserName());
        removeSubscriber(manager);
    }

    /**
     * Adds a user to the team's subscribers list
     *
     * @param user the new subscriber
     */
    public void addSubscriber(Subscriber user) {
        alert.addSubscriber(user);
    }

    /**
     * Removes a user from the team's subscribers list
     *
     * @param user the removed subscriber
     */
    public void removeSubscriber(Subscriber user) {
        alert.removeSubscriber(user);
    }


    //========================= DB Access Functions ========================//

    /**
     * Returns a team object that matches the given team name from the DB
     *
     * @param teamName the given team name
     * @return a team object that matches the given team name from the DB
     */
    public static Team getTeamByName(String teamName) {
        //TODO: change the mock to DB
        return new Team(teamName, new Field("fieldName", 100), new TeamOwner("userName", "mail"));
    }

}
