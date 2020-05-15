package domain.controllers;

import domain.Role;
import domain.TeamCoach;
import domain.User;

public class CoachController {
    // ======================= Coach functions ============================
    // ====================================================================


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
}
