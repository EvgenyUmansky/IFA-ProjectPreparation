package domain.controllers;

import domain.Role;
import domain.TeamCoach;
import domain.User;

import java.util.ArrayList;
import java.util.Arrays;

public class CoachController {
    // ========================= Guest functions ========================
    // ==================================================================

    /**
     * UC 2.4
     * Returns the coach instance by his name
     * @param coachName the player's name
     * @return the coach instance by his name
     */
    public TeamCoach getCoachDetails(String coachName) {
        return TeamCoach.getCoachByName(coachName);
    }


    // ========================= Coach functions ========================
    // ==================================================================

    /**
     * UC 5.1
     * Updates the coach's details
     * @param username the coach's username
     * @param coachName the coach's name
     * @param qualification the coach's qualification
     * @param role the coach's role
     */
    public void updateCoachDetails(String username, String coachName, String qualification, String role) {
        User coachUser = User.getUserByID(username);
        if(coachName!=null){
            coachUser.setName(coachName);
        }
        ((TeamCoach)User.getUserByID(username).getRoles().get(Role.COACH)).updateDetails(qualification,role);
    }

    public ArrayList<TeamCoach> getCoaches() {
        // TODO: DB arraylist of all Coaches
        TeamCoach coach1 = new TeamCoach("testCoach1", "testCoach1@gmail.com", "Pep Guardiola");
        TeamCoach coach2 = new TeamCoach("testCoach2", "testCoach2@gmail.com", "Massimiliano Allegri");
        TeamCoach coach3 = new TeamCoach("testCoach3", "testCoach3@gmail.com", "Zinedine Zidane");
        TeamCoach coach4 = new TeamCoach("testCoach4", "testCoach4@gmail.com", "Antonio Conte");
        return new ArrayList<TeamCoach>(Arrays.asList(coach1,coach2,coach3,coach4));
    }

    public ArrayList<TeamCoach> getAvailableCoaches() {
        // TODO: DB arraylist of all available Coaches
        TeamCoach coach1 = new TeamCoach("testCoach1", "testCoach1@gmail.com", "Diego Simeone");
        TeamCoach coach2 = new TeamCoach("testCoach2", "testCoach2@gmail.com", "José Mourinho");
        TeamCoach coach3 = new TeamCoach("testCoach3", "testCoach3@gmail.com", "Unai Emery");
        return new ArrayList<TeamCoach>(Arrays.asList(coach1,coach2,coach3));
    }
}
