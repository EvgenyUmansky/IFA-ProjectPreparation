package domain;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.HashSet;

public class Team {

    private String teamName;
    private Field stadium; //The team's main stadium, where they play official matches
    private HashSet<Field> fields; //All the team's fields, including the stadium and training fields
    private HashMap<String,TeamPlayer> players;
    private HashMap<String,TeamCoach> coaches;
    private HashMap<String,TeamManager> managers;
    private HashMap<String,TeamOwner> owners;
    private String teamEmail;
    private Budget budget;
    private PersonalPage teamPage;
    // Constructor
    public Team(String name, Field stadium, TeamOwner owner)
    {
        this.teamName = name;
        this.stadium = stadium;
        this.fields = new HashSet<>();
        this.owners = new HashMap<>();

        addField(stadium);
        addOwner(owner);
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

    public Field getStadium() {
        return stadium;
    }

    public String getTeamEmail() {
        return teamEmail;
    }

    public void setTeamEmail(String teamEmail) {
        this.teamEmail = teamEmail;
    }

    public Field getMyField() {
        return stadium;
    }

    public String getTeamName() {
        return teamName;
    }


    public void addProperty(Object property){
        if(property instanceof Field){
          addField((Field)property);
        }

        if(property instanceof TeamPlayer){
            addPlayer((TeamPlayer)property);
        }

        if(property instanceof TeamCoach){
            addCoach((TeamCoach)property);
        }

        if(property instanceof TeamManager){
            addManager((TeamManager)property);
        }
    }

    public void removeProperty(Object property){
        if(property instanceof Field){
            removeField((Field)property);
        }

        if(property instanceof TeamPlayer){
            removePlayer((TeamPlayer)property);
        }

        if(property instanceof TeamCoach){
            removeCoach((TeamCoach)property);
        }

        if(property instanceof TeamManager){
            removeManager((TeamManager)property);
        }
    }

    public void addField(Field field){
        this.fields.add(field);
    }

    public void addOwner(TeamOwner owner){
        if(owner.getTeam() == null){
            owner.setTeam(this);
        }

        this.owners.put(owner.getUserName(),owner);
    }

    public void addPlayer(TeamPlayer player){
        this.players.put(player.getUserName(),player);
    }

    public void addCoach(TeamCoach coach){
        this.coaches.put(coach.getUserName(),coach);
    }

    public void addManager(TeamManager manager){
        this.managers.put(manager.getUserName(),manager);
    }

    public void removeField(Field field){
        this.fields.remove(field);
    }


    public void removePlayer(TeamPlayer player){
        this.players.remove(player.getUserName());
    }

    public void removeCoach(TeamCoach coach){
        this.coaches.remove(coach.getUserName());
    }

    public void removeManager(TeamManager manager){
        this.managers.remove(manager.getUserName());
    }

    public TeamPlayer getPlayer(String userName){
        return players.get(userName);
    }

    public TeamCoach getCoach(String userName){
        return coaches.get(userName);
    }

}
