package domain;

public abstract class TeamMember extends Subscriber {

    // Constructor

    public TeamMember(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }
}
