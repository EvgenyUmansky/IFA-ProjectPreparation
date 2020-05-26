package domain.controllers;

import domain.League;
import domain.Team;
import domain.TeamCoach;
import domain.TeamPlayer;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class GuestController {
    static Logger logger = Logger.getLogger(GuestController.class.getName());
    // ========================= Guest functions ========================
    // ==================================================================

    /**
     * UC 2.5
     *
     * @param term
     */
    public void searchByKeyTerm(String term) {

        logger.info("Term " + term + " was searched");
    }
}
