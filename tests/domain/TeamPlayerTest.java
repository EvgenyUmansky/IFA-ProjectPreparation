package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamPlayerTest {

    TeamPlayer player;

    @BeforeEach
    public void init(){
        player = new TeamPlayer("playeruser","player@gmail.com");
    }

    @AfterEach
    public void finish(){
        player = null;
    }

    @Test
    void updateDetails() {
        Date date = new Date(1998,02,02);
        assertNull(player.getBirthDate());
        assertNull(player.getPosition());
        assertNull(player.getSquadNumber());
        player.updateDetails(date,"player","5");
        assertNotNull(player.getBirthDate());
        assertNotNull(player.getPosition());
        assertNotNull(player.getSquadNumber());
    }

    @Test
    void setBirthDate() {
        Date date = new Date(1998,02,02);
        assertNull(player.getBirthDate());
        player.setBirthDate(date);
        assertNotNull(player.getBirthDate());
    }

    @Test
    void setPosition() {
        assertNull(player.getPosition());
        player.setPosition("player");
        assertNotNull(player.getPosition());
    }


    @Test
    void getPlayerByName () {

       TeamPlayer teamPlayer = TeamPlayer.getPlayerByName("playeruser");
        assertNotNull(teamPlayer);
    }

    @Test
    void setSquadNumber() {
        assertNull(player.getSquadNumber());
        player.setSquadNumber("5");
        assertNotNull(player.getSquadNumber());
    }

    @Test
    void setCurrentTeam() {
        Field field = new Field("test",100);
        TeamOwner owner = new TeamOwner("ownerUser","owner@gmail.com");
        Team team = new Team("test",field,owner);

        assertNull(player.getCurrentTeam());
        player.setCurrentTeam(team.getTeamName());
        assertNotNull(player.getCurrentTeam());
    }


}