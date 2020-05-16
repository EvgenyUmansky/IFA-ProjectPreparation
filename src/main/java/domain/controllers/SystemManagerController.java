package domain.controllers;

import domain.User;

public class SystemManagerController {


    // =================== System Manager functions ====================
    // =================================================================


    /**
     * UC 8.2
     * Removes a user from the system
     * @param userName the removed user's username
     */
    public void removeUserFromSystem(String userName) {
        User.getUserByID(userName).closeUser();
    }


    /**
     * UC 8.3
     *
     */
    public void showComplain() {
        // UC 8.3A - show Complaint
    }


    /**
     * UC 8.3
     *
     */
    public void commentToComplaint() {
        // UC 8.3B - add comment to complaint
    }


    /**
     * UC 8.4
     *
     */
    public void showLogDocument() {
        // UC 8.4 - show log document
    }


    /**
     * UC 8.5
     *
     */
    public void startModelRecommendationSystem() {
        // UC 8.5 - start model of recommendation Systems
    }
}
