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

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setSquadNumber(String squadNumber) {
        this.squadNumber = squadNumber;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }
}
