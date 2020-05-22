package service.pojos;

import domain.*;

import java.util.ArrayList;

public class TeamDTO {
    private String teamName;
    private Field stadium; //The team's main stadium, where they play official matches
    private ArrayList<Field> fields; //All the team's fields, including the stadium and training fields
    private ArrayList<TeamPlayer> players;
    private ArrayList<TeamCoach> coaches;
    private ArrayList<TeamManager> managers;
    private ArrayList<TeamOwner> owners;
    private String teamStatus;

    public TeamDTO(String teamName, Field stadium, ArrayList<Field> fields, ArrayList<TeamPlayer> players, ArrayList<TeamCoach> coaches, ArrayList<TeamManager> managers, ArrayList<TeamOwner> owners, String teamStatus) {
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

    public Field getStadium() {
        return stadium;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public ArrayList<TeamPlayer> getPlayers() {
        return players;
    }

    public ArrayList<TeamCoach> getCoaches() {
        return coaches;
    }

    public ArrayList<TeamManager> getManagers() {
        return managers;
    }

    public ArrayList<TeamOwner> getOwners() {
        return owners;
    }

    public String getTeamStatus() {
        return teamStatus;
    }



}
