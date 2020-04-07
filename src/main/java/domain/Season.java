package domain;

import java.util.ArrayList;
import java.util.List;

public class Season {
    List<Game> seasonGames;
    List<Team> seasonTeams;
    int year;

    public Season(List<Game> seasonGames, List<Team> seasonTeams, int year) {
        this.seasonGames = seasonGames;
        this.seasonTeams = seasonTeams;
        this.year = year;
    }

    // Constructor
    public Season() {
        this.seasonGames = new ArrayList<Game>();
        this.seasonTeams = new ArrayList<Team>();
    }
}
