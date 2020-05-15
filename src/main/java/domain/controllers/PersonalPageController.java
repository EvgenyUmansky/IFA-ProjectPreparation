package domain.controllers;

import domain.PersonalPage;
import domain.User;

import java.util.ArrayList;

public class PersonalPageController {
    // =================== Page functions ===========================
    // ==============================================================

    /**
     * Returns all the profile pages that a user has permission to edit
     * @param username the user's username
     * @return the list of pages he has permissions to
     */
    public ArrayList<PersonalPage> getPagesByUsername(String username) {
        return User.getUserByID(username).getPages();
    }


    // =================== Personal Pages functions =================
    // ==============================================================


    /**
     * UC 4.2, 5.2
     * Updates the info in the profile page
     * @param pageName the profile page
     * @param info the updated info
     * @return the updated page
     */
    public PersonalPage updateInfo(String pageName, String info){
        // TODO: get from DB
        //PersonalPage page = get from DB
        //return page.setInfo(info);
        return null;
    }
}
