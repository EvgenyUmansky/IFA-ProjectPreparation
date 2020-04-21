package domain;

import java.util.Date;

public class PlayerPage extends PersonalPage {

    private Date birthDate;
    private String position;
    private String squadNumber;
    private Team currentTeam;

    public PlayerPage(TeamPlayer player, String name) {
        //get name using controller while creating page
        super(name, player.getMail());
        this.birthDate = player.getBirthDate();
        this.squadNumber = player.getSquadNumber();
        this.position = player.getSquadNumber();
        this.currentTeam = player.getCurrentTeam();
        addPermissions(player);
    }

    public PlayerPage setBirthDate(Date birthDate) {
        this.birthDate = birthDate;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Birthday date of the user of page " + getName() + "  has changed", "The new birthday is " + birthDate));


        return this;
    }

    public PlayerPage setSquadNumber(String squadNumber) {
        this.squadNumber = squadNumber;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Squad number of the user of page " + getName() + "  has changed", "The new squad number is " + squadNumber));

        return this;
    }

    public PlayerPage setPosition(String position) {
        this.position = position;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Position of the user of page " + getName() + "  has changed", "Position is " + position));

        return this;
    }

    public PlayerPage setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;

        // UC 3.2
        getAlert().sendAlert(new AlertNotification("Team of the user of page " + getName() + "  has changed", "The new team is " + currentTeam));

        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPosition() {
        return position;
    }

    public String getSquadNumber() {
        return squadNumber;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }



}
