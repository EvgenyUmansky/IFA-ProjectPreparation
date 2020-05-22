package domain.controllers;

import DataAccess.UserDBAccess;
import domain.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        System.out.println(ref.getName());
        return Game.getGamesByReferee(ref);
    }

    /**
     * UC 10.3
     * Adds an event that took place during a game to its events list
     * @param gameId the match
     *
     * @throws Exception in case the game is over
     */
    public void addGameEventToGame(String gameId, String eventName, String description) throws Exception {
//        GameDBAccess gameDBAccess = GameDBAccess.getInstance();
//        GameEventDBAccess gameEventDBAccess = GameEventDBAccess.getInstance();
//
//        Game game = gameDBAccess.select(gameId);
//
//        int minuteOfEvent = (int)ChronoUnit.MINUTES.between(LocalDateTime.now(), game.getGameDate());
//
//        if(minuteOfEvent >= 90){
//            throw new Exception("The game is over");
//        }
//
//        GameEvent newGameEvent = new GameEvent(minuteOfEvent, GameAlert.valueOf(eventName), description);
//        game.addEvent(newGameEvent);
//        gameEventDBAccess.insert(newGameEvent);

    }


    /**
     * UC 10.4
     * Updates an event that took place during a game
     * @param gameId the match
     * @param eventId the event
     * @param dateTimeStr the time the event took place
     * @param minuteOfEvent the minute of the game the event took place in
     * @param eventName the name of the event
     * @param description the description of the event
     * @throws Exception in case not event of the game
     */
    public void changeGameEvent(String gameId, String eventId, String minuteOfEvent, String dateTimeStr, String eventName, String description) throws Exception {
//        GameDBAccess gameDBAccess = GameDBAccess.getInstance();
//        GameEventDBAccess gameEventDBAccess = GameEventDBAccess.getInstance();
//
//        Game game = gameDBAccess.select(gameId);
//        GameEvent gameEvent = gameEventDBAccess.select(eventId);
//
//        if (!game.getGameEvents().containsKey(Integer.parseInt(eventId))) {
//            throw new Exception("Not event of this game");
//        }
//
//        long diffInHours = ChronoUnit.HOURS.between(game.getGameDate(), LocalDateTime.now());
//        if (diffInHours > 5) {
//            throw new Exception("Not allowed to change the game events: out of time");
//        }
//
//        // dateTimeStr == null - the dateTimeStr UI field is not filled
//        if (dateTimeStr != null) {
//            gameEvent.setGameDate(dateTimeStr);
//        }
//
//        // get minute manually from referee in UI
//        if(Integer.parseInt(minuteOfEvent) > -1 && Integer.parseInt(minuteOfEvent) < 90){
//            gameEvent.setGameMinutes(Integer.parseInt(minuteOfEvent));
//        }
//
//        // eventName == null - the eventName UI field is not filled
//        if (eventName != null) {
//            gameEvent.setEventName(GameAlert.valueOf(eventName));
//        }
//
//        // description == null - the description UI field is not filled
//        if (description != null) {
//            gameEvent.setDescription(description);
//        }
//
//        game.changeEvent(gameEvent);
//        gameEventDBAccess.update(gameEvent);
    }
}
