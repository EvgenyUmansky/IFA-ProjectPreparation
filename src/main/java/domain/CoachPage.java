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
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
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
