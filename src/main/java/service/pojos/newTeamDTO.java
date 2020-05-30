package service.pojos;

import domain.*;

import java.util.ArrayList;

public class newTeamDTO {
    private String teamName;
    private String stadium;
    private String[] players;
    private String coach;
    private String owner;
    public newTeamDTO(String teamName, String stadium, String[] players, String coach, String owner) {
        this.teamName = teamName;
        this.stadium = stadium;
        this.players = players;
        this.coach = coach;
        this.owner = owner;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getStadium() {
        return stadium;
    }

    public String[] getPlayers() {
        return players;
    }

    public String getCoach() {
        return coach;
    }

    public String getOwner() {
        return owner;
    }
}
