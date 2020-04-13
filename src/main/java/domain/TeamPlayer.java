package domain;

import java.util.Date;

public class TeamPlayer extends TeamMember {

    private Date birthDate;
    private String position;
    private String squadNumber;

    // Constructor
    public TeamPlayer(String userName, String password, String name, String mail) {
        super(userName, password, name, mail);
    }

    public boolean updateDetails(String name, Date birthDate, String position, String squadNumber){
        super.setName(name);
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
