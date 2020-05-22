package domain;

/**
 * This class represents a coach user in the system
 */
public class TeamCoach extends TeamMember {

    private String role;
    private String qualification;

    //========================= Constructor ==========================//

    /**
     * Constructor
     * @param userName the user's username
     * @param mail the user's name
     */
    public TeamCoach(String userName, String mail) {
        super(userName, mail);
    }
    public TeamCoach(String userName, String mail, String name) {
        super(userName, mail, name);
    }
    //========================= Getters and Setters ==========================//

    /**
     * Sets the coach's role
     * @param role the coach's role
     */
    public void setRole(String role){
        this.role = role;
    }

    /**
     * Sets the coach's qualification
     * @param qualification the coach's qualification
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * Returns the coach's role
     * @return the coach's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the coach's qualification
     * @return the coach's qualification
     */
    public String getQualification() {
        return qualification;
    }


    //========================= DB Access Functions ==========================//

    /**
     * Returns the coach object that matches the given name from the DB
     * @param coachName the coach's name
     * @return the coach object that matches the given name from the DB
     */
    public static TeamCoach getCoachByName (String coachName){
        //TODO: working with DB
        return new TeamCoach("userName",null);
    }


    //========================= Functionality ==========================//

    /**
     * UC 5.1
     * Updates the details of the coach
     * @param newQualification updated qualification
     * @param newRole updated role
     */
    public void updateDetails(String newQualification, String newRole){
        //TODO: update new details also in player's page
        String coachName = User.getUserByID(this.getUserName()).getName();
        //      CoachPage coachPage = (CoachPage)PersonalPage.getPage(coachName);

        if(newQualification != null) {
            setQualification(newQualification);
//            coachPage.setQualification(newQualification);
        }
        if(newRole != null) {
            setRole(newRole);
            //          coachPage.setRole(newRole);
        }
    }

}
