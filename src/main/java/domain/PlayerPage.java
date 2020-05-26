package domain;

import java.util.Date;

/**
 * This class represents a player's profile page in the system
 */
public class PlayerPage extends PersonalPage {

    private Date birthDate;
    private String position;
    private String squadNumber;
    private String currentTeam;

    /**
     * Constructor
     * @param player the owner of the page
     * @param name the name of the player
     */
    public PlayerPage(TeamPlayer player, String name) {
        //get name using controller while creating page
        super(name, player.getMail());
        this.birthDate = player.getBirthDate();
        this.squadNumber = player.getSquadNumber();
        this.position = player.getSquadNumber();
        this.currentTeam = player.getCurrentTeam();
        addPermissions(player);
    }


    //Getters and Setters

    /**
     * UC 3.2
     * Updates the birth date of the player on the page
     * @param birthDate the player's birth date
     * @return the updated page
     */
    public PlayerPage setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        getAlert().sendAlert(new Notification("Birthday date of the user of page " + getName() + "  has changed", "The new birthday is " + birthDate));

        return this;
    }

    /**
     * UC 3.2
     * Updates the shirt number of the player on the page
     * @param squadNumber the shirt number of the player
     * @return the updated page
     */
    public PlayerPage setSquadNumber(String squadNumber) {
        this.squadNumber = squadNumber;
        getAlert().sendAlert(new Notification("Squad number of the user of page " + getName() + "  has changed", "The new squad number is " + squadNumber));

        return this;
    }

    /**
     * UC 3.2
     * Updates the position of the player on the page
     * @param position the position of the player
     * @return the updated page
     */
    public PlayerPage setPosition(String position) {
        this.position = position;
        getAlert().sendAlert(new Notification("Position of the user of page " + getName() + "  has changed", "Position is " + position));

        return this;
    }


    /**
     * UC 3.2
     * Updates the team the player plays for on the page
     * @param currentTeam the team the player plays for
     * @return the updated page
     */
    public PlayerPage setCurrentTeam(String currentTeam) {
        this.currentTeam = currentTeam;
        getAlert().sendAlert(new Notification("Team of the user of page " + getName() + "  has changed", "The new team is " + currentTeam));

        return this;
    }

    /**
     * Returns the player's birth date
     * @return the player's birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the player's position
     * @return the player's position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Returns the player's shirt number
     * @return the player's shirt number
     */
    public String getSquadNumber() {
        return squadNumber;
    }

    /**
     * Returns the team the player plays for
     * @return the team the player plays for
     */
    public String getCurrentTeam() {
        return currentTeam;
    }
}
