package domain;

/**
 * This class represents a personal page of a coach.
 */
public class CoachPage extends PersonalPage {

    private String role;
    private String qualification;
    private String currentTeam;


    //Constructor

    /**
     * Constructor
     * @param coach the coach that owns the page
     * @param name the coach's name
     */
    public CoachPage(TeamCoach coach, String name) {
        //get name using controller while creating page
        super(name, coach.getMail());
        this.role = coach.getRole();
        this.qualification = coach.getQualification();
        this.currentTeam = coach.getCurrentTeam();
        addPermissions(coach);
    }


    //Getters and Setters

    /**
     * UC 3.2
     * Updates the role of the coach in the team.
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
        getAlert().sendAlert(new Notification("Role of the user of page " + getName() + "  has changed", "The new role is " + role));
    }

    /**
     * UC 3.2
     * Updates the qualification of the coach.
     * @param qualification the qualification of the coach
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
        getAlert().sendAlert(new Notification("Qualification of the user of page " + getName() + "  has changed", "The new qualification is " + qualification));
    }

    /**
     * UC 3.2
     * Updates the coach's team to the given team
     * @param currentTeam to the given team
     */
    public void setCurrentTeam(String currentTeam) {
        this.currentTeam = currentTeam;
        getAlert().sendAlert(new Notification("Team of the user of page " + getName() + "  has changed", "The new team is " + currentTeam));
    }

    /**
     * Returns the role of the coach
     * @return the role of the coach
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the qualification of the coach
     * @return the qualification of the coach
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Returns the team of the coach
     * @return the team of the coach
     */
    public String getCurrentTeam() {
        return currentTeam;
    }
}
