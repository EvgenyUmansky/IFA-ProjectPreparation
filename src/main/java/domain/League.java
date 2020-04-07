package domain;

import service.Controller;

import java.util.ArrayList;
import java.util.List;

public class League {
    private Controller controller;
    private List<Season> leagueSeasons;
    //TODO: check for other variables

    public League(Controller controller, List<Season> leagueSeasons) {
        this.controller = controller;
        this.leagueSeasons = leagueSeasons;
    }

    // Constructor
    public League() {
        this.controller = new Controller();
        this.leagueSeasons = new ArrayList<Season>();

    }
}
