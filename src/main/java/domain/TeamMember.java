package domain;

public abstract class TeamMember extends Subscriber {

    protected Team currentTeam;

    // Constructor

    public TeamMember(String userName, String mail) {
        super(userName, mail);
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }
}
