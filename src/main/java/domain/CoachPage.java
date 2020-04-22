package domain;


public class CoachPage extends PersonalPage {

    private String role;
    private String qualification;
    private Team currentTeam;

    public CoachPage(TeamCoach coach, String name) {
        //get name using controller while creating page
        super(name, coach.getMail());
        this.role = coach.getRole();
        this.qualification = coach.getQualification();
        this.currentTeam = coach.getCurrentTeam();
        addPermissions(coach);
    }


    public void setRole(String role) {
        this.role = role;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Role of the user of page " + getName() + "  has changed", "The new role is " + role));
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Qualification of the user of page " + getName() + "  has changed", "The new qualification is " + qualification));
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Team of the user of page " + getName() + "  has changed", "The new team is " + currentTeam.getTeamName()));
    }

    public String getRole() {
        return role;
    }

    public String getQualification() {
        return qualification;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}
