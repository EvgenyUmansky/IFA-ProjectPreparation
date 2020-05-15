package domain.controllers;

import domain.*;

import java.util.ArrayList;

public class RefereeController {
    // ========================= Referee functions ============================
    // ====================================================================


    /**
     * UC 10.1
     * Returns the details about a referee
     * @param username the referee's username
     * @return the info about the referee
     */
    public String getRefereeDetails(String username) {
        return User.getUserByID(username).getProfileDetails() + "\n" + ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).getRefereeDetails();
    }

    /**
     * UC 10.1
     * Updates the details about a referee
     * @param username the referee's username
     * @param newPassword the new password
     * @param newName the new name
     * @param newMail the new mail
     * @param qualification the updated qualification
     * @param refereeType the updated type
     */
    public void setRefereeProfileDetails(String username, String newPassword, String newName, String newMail, String qualification, String refereeType) {
        User.getUserByID(username).setProfileDetails(newPassword, newName, newMail);
        ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE)).setRefereeDetails(newMail, Integer.parseInt(qualification), RefereeType.valueOf(refereeType));
    }


    /**
     * UC 10.2
     * Returns a list of games that the referee referees at
     * @param username the referee's username
     * @return the list of games that the referee referees at
     */
    public ArrayList<Game> getRefereeGames(String username) {
        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
        return Game.getGamesByReferee(ref);
    }


    /**
     * UC 10.3
     * Adds an event that took place during a game to its events list
     * @param username the referee's username
     * @param game the match
     * @param gameEvent the event
     * @throws Exception in case the addition was unsuccessful
     */
    public void addGameEventToGame(String username, String game, String gameEvent) throws Exception {
        // TODO: DB Strings
//        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
//
//        // TODO: compare with id from DB
//        if(isEqualGameInList(Game.getGamesByReferee(ref), game)){
//            try {
//                game.addEvent(gameEvent);
//            }
//            catch (Exception e){
//                e.printStackTrace(); // not valid date exception
//                // TODO: logger
//            }
//        }
//        else {
//            throw new Exception("This referee doesn't judge in this game");
//        }
    }


    /**
     * UC 10.4
     * Updates an event that took place during a game
     * @param username the referee's username
     * @param game the match
     * @param gameEvent the event
     * @param dateTimeStr the time the event took place
     * @param gameMinutes the minute of the game the event took place in
     * @param eventName the name of the event
     * @param description the description of the event
     * @throws Exception in case the update was unsuccessful
     */
    public void changeGameEvent(String username, String game, String gameEvent, String dateTimeStr, String gameMinutes, String eventName, String description) throws Exception {
        // TODO: DB Strings
// TODO: check the referee is MAIN in UI
//        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
//        // TODO: compare with id from DB
//        if(isEqualGameInList(Game.getGamesByReferee(ref), game)) {
//            try {
//                game.changeEvent(gameEvent, dateTimeStr, gameMinutes, eventName,  description);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//                // TODO: logger
//            }
//        }
//        else {
//            throw new Exception("This referee doesn't judge in this game");
//        }
    }

    /**
     * Checks if a game already exists in the referee's games list
     * @param games the list of games
     * @param game the checked game
     * @return true if the list contains the game, false if not
     */
    private boolean isEqualGameInList(ArrayList<Game> games, Game game){
        // TODO: change this function to ids from DB
        // Check if game in ArrayList of games
        for(Game refGame : games){
            if(game.isEqualGame(refGame)){
                return true;
            }
        }
        return false;
    }

}
