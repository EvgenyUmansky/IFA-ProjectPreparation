package domain.controllers;

import domain.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {

    public ArrayList<Game> getGames() {
        League mockLeage = new League("testLeage");
        mockLeage.addReferee(new Referee("refereeTest", "ref@gmail.com"));
        Team teamA = new Team("teamA",new Field("FieldA", 100), new TeamOwner("ownerA", "ownerA@gmail.com"));
        Team teamB = new Team("teamB",new Field("FieldB", 100), new TeamOwner("ownerB", "ownerB@gmail.com"));
        Game mockGame = new Game(mockLeage, teamA, teamB, teamA.getStadium(), "2016-11-09 11:44", new ArrayList<Referee>(mockLeage.getReferees()));
        // TODO: return all games
        return new ArrayList<Game>(Arrays.asList(mockGame));
    }

    public Game getGame(String gameId){
        // TODO: get from DB
        // Game game = DB game
        // return game;
        return null;
    }

    // ========================= Fan functions ========================
    // ================================================================

    /**
     * UC 3.3
     * Adds a fan as a subscriber to a game
     * @param gameId the game
     * @param username the fan's username
     */
    public void addFanSubscriptionToGame(String gameId, String username) {
        // TODO: get from DB
        // Game game = DB game
        // game.addFanToAlerts((Fan)User.getUserByID(username).getRoles().get(Role.FAN));
    }




    // ========================= Referee functions ========================
    // ====================================================================

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
     * @param gameId the match
     *
     * @throws Exception in case the addition was unsuccessful
     */
    public void addGameEventToGame(String username, String gameId, String dateTimeStr, String gameMinutes, String eventName, String description) throws Exception {
        GameEvent newGameEvent = new GameEvent(dateTimeStr, Integer.parseInt(gameMinutes), GameAlert.valueOf(eventName), description);
        // TODO: add newGameEvent to DB
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
     * @param eventId the event
     * @param dateTimeStr the time the event took place
     * @param gameMinutes the minute of the game the event took place in
     * @param eventName the name of the event
     * @param description the description of the event
     * @throws Exception in case the update was unsuccessful
     */
    public void changeGameEvent(String username, String game, String eventId, String dateTimeStr, String gameMinutes, String eventName, String description) throws Exception {
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
