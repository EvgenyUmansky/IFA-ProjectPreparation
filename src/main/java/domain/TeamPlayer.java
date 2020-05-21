package domain;

import java.util.Date;

/**
 * This class represents a player user in the system
 */
public class TeamPlayer extends TeamMember {

    private Date birthDate;
    private String position;
    private String squadNumber;


    // Constructors

    /**
     *
     * @param userName
     * @param mail
     * @param birthDate
     * @param position
     * @param squadNumber
     */
    public TeamPlayer(String userName, String mail, Date birthDate, String position, String squadNumber) {
        super(userName, mail);
        this.birthDate = birthDate;
        this.position = position;
        this.squadNumber = squadNumber;
    }

    /**
     * Constructor
     * @param userName the username of the player
     * @param mail the mail of the player
     */
    public TeamPlayer(String userName, String mail) {
        super(userName, mail);
    }
    public TeamPlayer(String userName, String mail, String name) {
        super(userName, mail, name);
    }

    // Functionality


    /**
     * UC 4.1
     * Updates the details of the player
     * @param newBirthDate the player's birth date
     * @param newPosition the player's position
     * @param newQuadNumber the player's shirt number
     */
    public void updateDetails(Date newBirthDate, String newPosition, String newQuadNumber){
        //TODO: update new details also in player's page
        String playerName = User.getUserByID(this.getUserName()).getName();
  //      PlayerPage playerPage = (PlayerPage)PersonalPage.getPage(playerName);

        if(newBirthDate != null) {
            setBirthDate(newBirthDate);
  //          playerPage.setBirthDate(newBirthDate);
        }
        if(newPosition != null) {
            setPosition(newPosition);
 //           playerPage.setPosition(newPosition);
        }
        if(newQuadNumber != null) {
            setSquadNumber(newQuadNumber);
//            playerPage.setSquadNumber(newQuadNumber);
        }
    }


    // Getters and Setters

    /**
     * Returns the player's birth date
     * @return the player's birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the player's shirt number
     * @return the player's shirt number
     */
    public String getSquadNumber() {
        return squadNumber;
    }

    /**
     * Returns the player's position
     * @return the player's position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Updates the player's birth date
     * @param birthDate the player's birth date
     */
    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    /**
     * Updates the player's position
     * @param position the player's position
     */
    public void setPosition(String position){
        this.position = position;
    }

    /**
     * Updates the player's shirt number
     * @param squadNumber the player's shirt number
     */
    public void setSquadNumber(String squadNumber){
        this.squadNumber = squadNumber;
    }


    // DB Access Functions

    /**
     *  Returns the player object that matches his name from the data base
     * @param playerName the player's name
     * @return the player object that matches his name from the data base
     */
    public static TeamPlayer getPlayerByName (String playerName){
        //TODO: working with DB
        return new TeamPlayer("userName",null);
    }
}
