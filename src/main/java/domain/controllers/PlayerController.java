package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.TeamPlayerDBAccess;
import DataAccess.UserDBAccess;
import domain.Role;
import domain.TeamPlayer;
import domain.User;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class PlayerController {
    static Logger logger = Logger.getLogger(PlayerController.class.getName());

    private DBAccess<TeamPlayer> pda = TeamPlayerDBAccess.getInstance();


    public ArrayList<TeamPlayer> getPlayers() {
        // TODO: DB arraylist of all players
        TeamPlayer player1 = new TeamPlayer("testPlayer1", "testPlayer1@gmail.com", "Lionel Messi");
        TeamPlayer player2 = new TeamPlayer("testPlayer2", "testPlayer2@gmail.com", "David Beckham");
        TeamPlayer player3 = new TeamPlayer("testPlayer3", "testPlayer3@gmail.com", "Cristiano Ronaldo");
        TeamPlayer player4 = new TeamPlayer("testPlayer4", "testPlayer4@gmail.com", "Zlatan Ibrahimović");
        TeamPlayer player5 = new TeamPlayer("testPlayer5", "testPlayer5@gmail.com", "Paolo Maldini");
        return new ArrayList<TeamPlayer>(Arrays.asList(player1,player2,player3));
    }

    public ArrayList<TeamPlayer> getAvailablePlayers() {
        // TODO: DB arraylist of all available players
       /* TeamPlayer player1 = new TeamPlayer("testPlayer1", "testPlayer1@gmail.com", "Diego Maradona");
        TeamPlayer player2 = new TeamPlayer("testPlayer2", "testPlayer2@gmail.com", "Neymar");
        TeamPlayer player3 = new TeamPlayer("testPlayer3", "testPlayer3@gmail.com", "Wayne Rooney");
        return new ArrayList<TeamPlayer>(Arrays.asList(player1,player2,player3));*/

       String[] conditions = new String[2];
       conditions[0] = "teamName";
       conditions[1] = "";
       HashMap<String,TeamPlayer> availablePlayers = pda.conditionedSelect(conditions);

       return new ArrayList<>(availablePlayers.values());
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
        logger.info(playerName + " got his details");
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

        logger.info(playerName + " updated his details");
    }

}
