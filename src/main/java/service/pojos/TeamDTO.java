package service.pojos;

import java.util.HashMap;

public class TeamDTO {
    private String teamName;
    private String stadium; //The team's main stadium, where they play official matches
    private HashMap<String,String> fields; //All the team's fields, including the stadium and training fields
    private HashMap<String, String> players;
    private HashMap<String, String> coaches;
    private HashMap<String, String> managers;
    private HashMap<String, String> owners;
    private String teamStatus;

    public TeamDTO(String teamName, String stadium, HashMap<String,String> fields, HashMap<String, String> players, HashMap<String, String> coaches, HashMap<String, String> managers, HashMap<String, String> owners, String teamStatus) {
        this.teamName = teamName;
        this.stadium = stadium;
        this.fields = fields;
        this.players = players;
        this.coaches = coaches;
        this.managers = managers;
        this.owners = owners;
        this.teamStatus = teamStatus;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getStadium() {
        return stadium;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public HashMap<String, String> getPlayers() {
        return players;
    }

    public HashMap<String, String> getCoaches() {
        return coaches;
    }

    public HashMap<String, String> getManagers() {
        return managers;
    }

    public HashMap<String, String> getOwners() {
        return owners;
    }

    public String getTeamStatus() {
        return teamStatus;
    }



}
