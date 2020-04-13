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


    public boolean setRole(String role, String user) {
        if(pageOwners.containsKey(user)){
            this.role = role;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setQualification(String qualification, String user) {
        if(pageOwners.containsKey(user)){
            this.qualification = qualification;
            return true;
        }
        else{
            return false;
        }
    }
}
