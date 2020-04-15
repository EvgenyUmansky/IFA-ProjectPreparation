package domain;

import java.util.Date;

public class TeamPlayer extends TeamMember {

    private Date birthDate;
    private String position;
    private String squadNumber;

    // Constructor


    public TeamPlayer(String userName, String mail, boolean isMail, Date birthDate, String position) {
        super(userName, mail, isMail);
        this.birthDate = birthDate;
        this.position = position;
    }
    public TeamPlayer(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
        this.birthDate = birthDate;
        this.position = position;
    }

    public boolean updateDetails(Date birthDate, String position, String squadNumber){
        setSquadNumber(squadNumber);
        setBirthDate(birthDate);
        setPosition(position);
        return true;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getSquadNumber() {
        return squadNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public void setSquadNumber(String squadNumber){
        this.squadNumber = squadNumber;
    }

}
