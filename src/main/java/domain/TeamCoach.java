package domain;


public class TeamCoach extends TeamMember {

    private String role;
    private String qualification;

    // Constructor
    public TeamCoach(String userName, String mail) {
        super(userName, mail);
    }


    //U.C 5.1
    public void updateDetails(String newQualification, String newRole){
        //update new details also in coach's page
        String coachName = User.getUserByID(this.getUserName()).getName();
        CoachPage coachPage = (CoachPage)PersonalPage.getPage(coachName);

        if(newQualification != null) {
            setQualification(newQualification);
            coachPage.setQualification(newQualification);
        }
        if(newRole != null) {
            setRole(newRole);
            coachPage.setRole(newRole);
        }

    }

    public void setRole(String role){
        this.role = role;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRole() {
        return role;
    }

    public String getQualification() {
        return qualification;
    }

    public static TeamCoach getCoachByName (String coachName){
        //TODO: working with DB
        return new TeamCoach("userName",null);
    }

}
