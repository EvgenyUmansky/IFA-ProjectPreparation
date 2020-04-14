package domain;


public class TeamCoach extends TeamMember {

    private String role;
    private String qualification;

    // Constructor
    public TeamCoach(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }


    public boolean updateDetails(String qualification, String role){
        setQualification(qualification);
        setRole(role);
        return true;
    }

    public void setRole(String role){
        this.role = role;
    }
    private void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRole() {
        return role;
    }

    public String getQualification() {
        return qualification;
    }
}
