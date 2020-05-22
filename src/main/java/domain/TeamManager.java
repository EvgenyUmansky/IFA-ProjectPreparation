package domain;

/**
 * This class represents a team manager user in the system
 */
public class TeamManager extends TeamMember {

    /**
     * Constructor
     * @param userName the username of the manager
     * @param mail the mail of the manager
     */
    public TeamManager(String userName, String mail) {
        super(userName, mail);
    }
    public TeamManager(String userName, String mail, String name) {
        super(userName, mail, name);
    }

}
