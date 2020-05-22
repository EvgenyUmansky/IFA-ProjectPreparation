package domain;

import java.util.HashMap;

/**
 * This class represents a user of an administrator of the system
 */
public class SystemAdministrator extends Subscriber {
    Alert alert;


/////////// Constructor ///////////

    /**
     * Constructor
     * @param userName the username of the system administrator
     * @param mail the mail of the system administrator
     */
    public SystemAdministrator(String userName, String mail) {
        super(userName, mail);
    }
    public SystemAdministrator(String userName, String mail, String name) {
        super(userName, mail, name);
    }




}
