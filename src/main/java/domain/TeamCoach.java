package domain;


public class TeamCoach extends TeamMember {

    private String role;
    private String validation;

    // Constructor


    public TeamCoach(String userName, String mail, String role, String validation) {
        super(userName, mail);
        this.role = role;
        this.validation = validation;
    }
    public TeamCoach(String userName, String mail) {
        super(userName, mail);
        this.role = role;
        this.validation = validation;
    }

    public void setRole(String role){
        this.role = role;
    }

    public boolean updateDetails(String validation, String role){
        setValidation(validation);
        setRole(role);
        return true;
    }

    private void setValidation(String validation) {
        this.validation = validation;
    }
}
