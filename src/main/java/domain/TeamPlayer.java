package domain;

import java.util.Date;

public class TeamPlayer extends TeamMember {

    private Date birthDate;
    private String position;
    private String squadNumber;


    // Constructor


    public TeamPlayer(String userName, String mail, Date birthDate, String position, String squadNumber) {
        super(userName, mail);
        this.birthDate = birthDate;
        this.position = position;
        this.squadNumber = squadNumber;
    }
    public TeamPlayer(String userName, String mail) {
        super(userName, mail);
    }


    // U.C 4.1
    public PlayerPage updateDetails(Date newBirthDate, String newPosition, String newQuadNumber){
        //update new details also in player's page
        String playerName = User.getUserByID(this.getUserName()).getName();
        PlayerPage playerPage = (PlayerPage)PersonalPage.getPage(playerName);

        if(newBirthDate != null) {
            setBirthDate(newBirthDate);
            playerPage.setBirthDate(newBirthDate);
        }
        if(newPosition != null) {
            setPosition(newPosition);
            playerPage.setPosition(newPosition);
        }
        if(newQuadNumber != null) {
            setSquadNumber(newQuadNumber);
            playerPage.setSquadNumber(newQuadNumber);
        }
        return playerPage;

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

    public static TeamPlayer getPlayerByName (String playerName){
        //TODO: working with DB
        return new TeamPlayer("userName",null);
    }
}
