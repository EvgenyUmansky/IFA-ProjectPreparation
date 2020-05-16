package domain.controllers;

import domain.*;

import java.util.ArrayList;

public class FanController {
    // ========================= Fan functions ============================
    // ====================================================================


    /**
     * UC 3.4
     * Sends a complaint to the system administrators
     * @param username the user's username
     * @param sysAdmins the list of system administrators
     * @param message the complaint
     */
    public void sendComplaintToSysAdmin(String username, ArrayList<SystemAdministrator> sysAdmins, AlertNotification message) {
        ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).sendComplaintToSysAdmin(sysAdmins, message);
    }

    // UC 3.5 - get history of fans searches

    /**
     * UC 3.5
     * Gets a fan's history of searches
     * @param username the fan's username
     * @return the fan's history of searches
     */
    public String[] getFanHistory(String username) {
        //TODO - get from data base
        return ((Fan) User.getUserByID(username).getRoles().get(Role.FAN)).getSearchHistory();
    }

    /**
     * UC 3.6
     * Returns the info of a fan
     * @param username the fan's username
     * @return the fan's info
     */
    public String getFanProfileDetails(String username) {
        return User.getUserByID(username).getProfileDetails();
    }

    /**
     * UC 3.6
     * Updates a fan's info
     * @param username the fan's username
     * @param newPassword the new password
     * @param newName the new name
     * @param newMail the new mail
     */
    public void setFanProfileDetails(String username, String newPassword, String newName, String newMail) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
    }
}
