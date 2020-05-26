package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.GameEventDBAccess;
import DataAccess.RefereeGamesDBAccess;
import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import service.pojos.GameDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    static Logger logger = Logger.getLogger(GameController.class.getName());

    private DBAccess<Pair<String, ArrayList<Game>>> rgda = RefereeGamesDBAccess.getInstance();
    private DBAccess<GameEvent> geda = GameEventDBAccess.getInstance();

//    private DBAccess<User> uda = UserDBAccess.getInstance();
//    private GameDBAccess gameDBAccess = GameDBAccess.getInstance();
//    private GameEventDBAccess gameEventDBAccess = GameEventDBAccess.getInstance();

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
        // Game game = gameDBAccess.select(gameId);
        // game.addFanToAlerts((Fan)User.getUserByID(username).getRoles().get(Role.FAN));
        logger.info(username + ": subscription was added to game " + gameId);
    }




    // ========================= Referee functions ========================
    // ====================================================================

    /**
     * UC 10.2
     * Returns a list of games that the referee referees at
     * @param username the referee's username
     * @return the list of games that the referee referees at
     */
    public ArrayList<GameDTO> getRefereeGames(String username) {
        // TODO: get all games for this referee from DB
//        Referee ref = ((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
//        ArrayList<Game> ans  = Game.getGamesByReferee(ref);



        /*
        ArrayList<Game> array = new ArrayList<>();
        League mockLeague = new League("testLeage");
        mockLeague.addReferee((Referee) User.getUserByID(username).getRoles().get(Role.REFEREE));
        Team teamA = new Team("teamA",new Field("FieldA", 100), new TeamOwner("ownerA", "ownerA@gmail.com"));
        Team teamB = new Team("teamB",new Field("FieldB", 100), new TeamOwner("ownerB", "ownerB@gmail.com"));
        Game mockGame = new Game(mockLeague, teamA, teamB, teamA.getStadium(), "2016-11-09 11:44", new ArrayList<Referee>(mockLeague.getReferees()));
        mockGame.addEvent(new GameEvent(60, GameAlert.GOAL, "Messi did goal"));
        mockGame.addEvent(new GameEvent(75, GameAlert.INJURY, "Yossi Benayoun got injured"));
        array.add(mockGame);
        ArrayList<GameDTO> response = new ArrayList<>();
        for (Game game : array) {
            response.add(new GameDTO(
                    game.getId(),
                    game.getHostTeam().getTeamName(),
                    game.getGuestTeam().getTeamName(),
                    game.getField(),
                    game.getGameDate(),
                    game.getReferees(),
                    new ArrayList<>(game.getGameEvents().values()),
                    game.getGameScore()
            ));
        }
        return response;
*/

        Pair<String, ArrayList<Game>> retrievedGamesReferee = rgda.select(username);
        ArrayList<Game> games = retrievedGamesReferee.getValue();

        ArrayList<GameDTO> gamesDTO = new ArrayList<>();

        for (Game game : games) {
            gamesDTO.add(new GameDTO(
                    game.getId(),
                    game.getHostTeam().getTeamName(),
                    game.getGuestTeam().getTeamName(),
                    game.getField(),
                    game.getGameDate(),
                    game.getReferees(),
                    new ArrayList<>(game.getGameEvents().values()),
                    game.getGameScore()
            ));
        }

    return gamesDTO;

    }

    /**
     * UC 10.3
     * Adds an event that took place during a game to its events list
     * @param gameId the match
     *
     * @throws Exception in case the game is over
     */
    public void addGameEventToGame(String gameId, String eventName, String description) throws Exception {
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

        logger.info(eventName + ": event was added to game " + gameId);
        LocalDateTime gameDate = LocalDateTime.now().withNano(0).withSecond(0);
        GameEvent gameEvent = new GameEvent(Integer.parseInt(gameId),gameDate,eventName,description);
        geda.save(gameEvent);
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

        logger.info(eventId + ": event was changed in game " + gameId);
    }
}
