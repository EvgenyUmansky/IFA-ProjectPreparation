package domain;


public class TeamCoach extends TeamMember {

    private String role;
    private String qualification;

    // Constructor
    public TeamCoach(String userName, String password, String name, String mail) {
        super(userName, password, name, mail);
    }


    public boolean updateDetails(String name, String qualification, String role){
        super.setName(name);
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
