package domain.controllers;

import domain.Role;
import domain.TeamPlayer;
import domain.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayerController {

    public ArrayList<TeamPlayer> getPlayers() {
        // TODO: DB arraylist of all players
        return null;
    }

    // ========================= Guest functions ==========================
    // ====================================================================

    /**
     * UC 2.4
     * Returns the player instance by his name
     * @param playerName the player's name
     * @return the player instance by his name
     */
    public TeamPlayer getPlayersDetails(String playerName) {
        return TeamPlayer.getPlayerByName(playerName);
    }



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
