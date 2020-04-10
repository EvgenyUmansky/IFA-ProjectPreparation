package domain;


public class TeamCoach extends TeamMember {

    private String role;
    private String validation;

    // Constructor
    public TeamCoach(String userName, String password, String name, String mail) {
        super(userName, password, name, mail);
    }


    public void setRole(String role){
        this.role = role;
    }

    public boolean updateDetails(String name, String validation, String role){
        super.setName(name);
        setValidation(validation);
        setRole(role);
        return true;
    }

    private void setValidation(String validation) {
        this.validation = validation;
    }
}
