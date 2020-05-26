package domain.controllers;

import domain.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class RefereeController {
    static Logger logger = Logger.getLogger(RefereeController.class.getName());

    // =================== Association Agent functions ====================
    // ====================================================================

    /**
     * UC 9.3
     * Registers a new referee in the system
     * @param username the referee's username
     * @param password the referee's password
     * @param name the referee's name
     * @param mail the referee's mail
     * @throws Exception if the creation was unsuccessful
     */
    public void createReferee(String username, String password, String name, String mail) throws Exception {
        // this.register(username, password, name, mail).addRoleToUser(Role.REFEREE);
        // TODO: Send invitation to referee
        logger.info(username + " referee was created");
    }

    /**
     * UC 9.3
     * Removes the referee role from a referee
     * @param username the referee's username
     */
    public void removeReferee(String username) {
        logger.info(username + " referee was removed");
        User.getUserByID(username).removeRoleFromUser(Role.REFEREE);
    }




    // ========================= Referee functions ============================
    // ====================================================================


    /**
     * UC 10.1
     * Returns the details about a referee
     * @param username the referee's username
     * @return the info about the referee
     */
    public String getRefereeDetails(String username) {
        logger.info(username + " got his details");
        return User.getUserByID(username).getProfileDetails() + "\n" + ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).getRefereeDetails();
    }

    /**
     * UC 10.1
     * Updates the details about a referee
     * @param username the referee's username
     * @param newPassword the new password
     * @param newName the new name
     * @param newMail the new mail
     * @param qualification the updated qualification
     * @param refereeType the updated type
     */
    public void setRefereeProfileDetails(String username, String newPassword, String newName, String newMail, String qualification, String refereeType) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
        ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).setRefereeDetails(newMail, Integer.parseInt(qualification), RefereeType.valueOf(refereeType));

        logger.info(username + " set his details");
    }

}
