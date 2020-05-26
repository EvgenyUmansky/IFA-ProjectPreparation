package domain.controllers;

import domain.PersonalPage;
import domain.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class PersonalPageController {
    static Logger logger = Logger.getLogger(PersonalPageController.class.getName());

    public ArrayList<PersonalPage> getPages() {
        // TODO: all personal pages
        return null;
    }

    // =================== Fan functions =================
    // ===================================================
    /**
     * UC 3.2
     * Adds a fan as a subscriber to the page
     * @param page the profile page
     * @param username the fan's username
     */
    public void addFanSubscriptionToPersonalPage(String page, String username) {
        // TODO: get from DB
        // PersonalPage page = DB pageName
        // page.addSubscriber((Fan) User.getUserByID(username).getRoles().get(Role.FAN));
        logger.info(username + " was added to " + page);
    }



    // =================== Personal Pages functions =================
    // ==============================================================

    /**
     * Returns all the profile pages that a user has permission to edit
     * @param username the user's username
     * @return the list of pages he has permissions to
     */
    public ArrayList<PersonalPage> getPagesByUsername(String username) {
        return User.getUserByID(username).getPages();
    }


    /**
     * UC 4.2, 5.2
     * Updates the info in the profile page
     * @param pageName the profile page
     * @param info the updated info
     * @return the updated page
     */
    public PersonalPage updatePageInfo(String pageName, String info){
        // TODO: get from DB
        //PersonalPage page = get from DB
        //return page.setInfo(info);
        logger.info(info + " was updated in " + pageName);
        return null;
    }

}
