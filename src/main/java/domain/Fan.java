package domain;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents a Fan user in the system. When a new user is registered in the system, he's automatically defined as a Fan.
 */
public class Fan extends Subscriber{


/////////// Constructor ///////////

    /**
     * Constructor
     * @param userName the fan's username
     * @param mail the fan's mail address
     */
    public Fan(String userName, String mail) {
        super(userName, mail);
    }

    public Fan(String userName, String mail, String name) {
        super(userName, mail, name);
    }



/////////// Functionality ///////////

    /**
     * UC 3.4
     * Sends a user's complaint to the admins of the system
     * @param sysAdmins the system administrators
     * @param notification the complaint
     * @return a Map that holds a system administrator username as key and a boolean value of true whether he has received the message or false otherwise
     */
    public Map<String, Boolean> sendComplaintToSysAdmin(ArrayList<SystemAdministrator> sysAdmins, Notification notification) {
        Alert alert = new Alert();
        for(SystemAdministrator sysAdmin : sysAdmins){
            alert.addSubscriber(sysAdmin);
        }
        return alert.sendAlert(notification);
    }


    /**
     * UC 3.5
     * Returns the history of searches that the user has made
     * @return an array with the searches
     */
    public String[] getSearchHistory() {
        // TODO: Get from db
        return new String[]{"A", "B", "C", "D"};
    }
}
