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
        return this;
    }

    public PlayerPage setSquadNumber(String squadNumber) {
        this.squadNumber = squadNumber;
        return this;
    }

    public PlayerPage setPosition(String position) {
        this.position = position;
        return this;
    }

    public PlayerPage setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
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
