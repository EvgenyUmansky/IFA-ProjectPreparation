package service.pojos;

import domain.*;

import java.util.ArrayList;

public class newTeamDTO {
    private String teamName;
    private String stadium;
    private String[] players;
    private String coach;

    public newTeamDTO(String teamName, String stadium, String[] players, String coach) {
        this.teamName = teamName;
        this.stadium = stadium;
        this.players = players;
        this.coach = coach;
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
}
