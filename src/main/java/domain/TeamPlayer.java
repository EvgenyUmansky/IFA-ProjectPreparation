package domain;

import java.util.Date;

public class TeamPlayer extends TeamMember {

    private Date birthDate;
    private String position;

    // Constructor


    public TeamPlayer(String userName, String mail, Date birthDate, String position) {
        super(userName, mail);
        this.birthDate = birthDate;
        this.position = position;
    }
    public TeamPlayer(String userName, String mail) {
        super(userName, mail);
        this.birthDate = birthDate;
        this.position = position;
    }

    public boolean updateDetails(Date birthDate, String position){
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
