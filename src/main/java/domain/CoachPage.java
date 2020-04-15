package domain;


public class CoachPage extends PersonalPage {

    private String role;
    private String qualification;

    public CoachPage(TeamCoach coach, String name) {
        //get name using controller while creating page
        super(name, coach.getMail());
        this.role = coach.getRole();
        this.qualification = coach.getQualification();
        addPermissions(coach);
    }


    public void setRole(String role) {
        this.role = role;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
