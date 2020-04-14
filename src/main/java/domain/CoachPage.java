package domain;


public class CoachPage extends PersonalPage {

    private String role;
    private String qualification;

    public CoachPage(TeamCoach coach) {
        super(coach.getName(), coach.getMail());
        this.role = coach.getRole();
        this.qualification = coach.getQualification();
        addPermissions(coach);
    }


    protected void setRole(String role, String user) {
        this.role = role;

    }

    protected void setQualification(String qualification, String user) {
        this.qualification = qualification;

    }
}
