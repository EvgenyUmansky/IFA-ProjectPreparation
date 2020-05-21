package service.pojos;

import java.util.HashMap;

public class TeamDTO {
    private String teamName;
    private String stadium; //The team's main stadium, where they play official matches
    private String[] fields; //All the team's fields, including the stadium and training fields
    private String[] players;
    private String[] coaches;
    private String[] managers;
    private String[] owners;
    private String teamStatus;

    public TeamDTO(String teamName, String stadium, String[] fields, String[] players, String[] coaches, String[] managers, String[] owners, String teamStatus) {
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

    public String[] getFields() {
        return fields;
    }

    public String[] getPlayers() {
        return players;
    }

    public String[] getCoaches() {
        return coaches;
    }

    public String[] getManagers() {
        return managers;
    }

    public String[] getOwners() {
        return owners;
    }

    public String getTeamStatus() {
        return teamStatus;
    }



}
