package domain;

import java.util.Date;

public class PlayerPage extends PersonalPage {

    private Date birthDate;
    private String position;
    private String squadNumber;

    public PlayerPage(TeamPlayer player) {
        super(player.getName(), player.getMail());
        this.birthDate = player.getBirthDate();
        this.squadNumber = player.getSquadNumber();
        this.position = player.getSquadNumber();
        addPermissions(player);
    }

    protected void setBirthDate(Date birthDate, String user) {
        this.birthDate = birthDate;

    }

    protected void setSquadNumber(String squadNumber, String user) {
        this.squadNumber = squadNumber;

    }

    protected void setPosition(String position, String user) {
        this.position = position;

    }
}
