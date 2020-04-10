package domain;

import java.util.Date;

public class TeamPlayer extends TeamMember {

    private Date birthDate;
    private String position;

    // Constructor
    public TeamPlayer(String userName, String password, String name, String mail) {
        super(userName, password, name, mail);
    }

    public boolean updateDetails(String name, Date birthDate, String position){
        super.setName(name);
        setBirthDate(birthDate);
        setPosition(position);
        return true;
    }


    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public void setPosition(String position){
        this.position = position;
    }
}
