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

    public boolean setBirthDate(Date birthDate, String user) {
        if(pageOwners.containsKey(user)){
            this.birthDate = birthDate;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setSquadNumber(String squadNumber, String user) {
        if(pageOwners.containsKey(user)){
            this.squadNumber = squadNumber;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setPosition(String position, String user) {
        if(pageOwners.containsKey(user)){
            this.position = position;
            return true;
        }
        else{
            return false;
        }
    }
}
