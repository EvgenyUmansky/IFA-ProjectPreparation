package domain;

/**
 * This class represents an Association Agent user in the system
 */
public class AssociationAgent extends Subscriber {


    // ========================= constructor ==========================

    /**
     * Constructor
     * @param userName the username of the user that is defined as Association Agent
     * @param mail the mail of the user
     */
    public AssociationAgent(String userName, String mail) {
        super(userName, mail);
    }

}
