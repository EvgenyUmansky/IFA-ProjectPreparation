package domain.controllers;

import domain.Role;
import domain.TeamPlayer;
import domain.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerController {
    // =================== Team Player functions ====================
    // ==============================================================

    /**
     * UC 4.1
     * Updates the player's details
     * @param username the player's username
     * @param playerName the player's name
     * @param birthDate the player's birth date
     * @param position the player's position
     * @param squadNumber the player's shirt number
     */
    public void updatePlayerDetails(String username, String playerName, String birthDate, String position, String squadNumber) throws ParseException {
        User playerUser = User.getUserByID(username);
        if(playerName!=null){
            playerUser.setName(playerName);
        }
        ((TeamPlayer)User.getUserByID(username).getRoles().get(Role.TEAM_PLAYER)).updateDetails(new SimpleDateFormat("dd/MM/yyyy").parse(birthDate),position,squadNumber);
    }
}
