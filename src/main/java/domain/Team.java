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
        this.managers = new HashMap<>();
        this.coaches = new HashMap<>();
        this.players = new HashMap<>();
        this.fields.add(stadium);
        this.owners.put(owner.getUserName(),owner);
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
            this.fields.add((Field)property);
        }

        if(property instanceof TeamPlayer){
            ((TeamPlayer)property).setCurrentTeam(this);
            this.players.put(((TeamPlayer)property).getUserName(),(TeamPlayer)property);
        }

        if(property instanceof TeamCoach){
            ((TeamCoach)property).setCurrentTeam(this);
            this.coaches.put(((TeamCoach)property).getUserName(),(TeamCoach)property);
        }

        if(property instanceof TeamManager){
            ((TeamManager)property).setCurrentTeam(this);
            this.managers.put(((TeamManager)property).getUserName(),(TeamManager)property);
        }
    }

    public void removeProperty(Object property){
        if(property instanceof Field){
            this.fields.remove(property);
        }

        if(property instanceof TeamPlayer){
            this.players.remove(((TeamPlayer)property).getUserName());
        }

        if(property instanceof TeamCoach){
            this.coaches.remove(((TeamCoach)property).getUserName());
        }

        if(property instanceof TeamManager){
            this.managers.remove(((TeamManager)property).getUserName());
        }
    }

    public void addOwner(TeamOwner owner){
        if(owner.getTeam() == null){
            owner.setTeam(this);
        }

        this.owners.put(owner.getUserName(),owner);
    }

    public boolean removeOwner(TeamOwner owner){
        //Impossible to leave the Team without an Owner
        if(this.owners.size() == 1){
            return false;
        }else{
            this.owners.remove(owner.getUserName());
            owner.setTeam(null);
            return true;
        }
    }

    public TeamPlayer getPlayer(String userName){
        return players.get(userName);
    }

    public TeamCoach getCoach(String userName){
        return coaches.get(userName);
    }

    public HashSet<Field> getFields() {
        return fields;
    }

    public HashMap<String, TeamOwner> getOwners() {
        return owners;
    }

    public Budget getBudget() {
        return budget;
    }

    public PersonalPage getTeamPage() {
        return teamPage;
    }


}
