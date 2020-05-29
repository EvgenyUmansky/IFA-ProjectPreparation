package domain.controllers;

import DataAccess.*;
import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import service.pojos.GameDTO;
import service.pojos.GameEventDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    static Logger logger = Logger.getLogger(GameController.class.getName());

    private RefereeGamesDBAccess rgda = RefereeGamesDBAccess.getInstance();
    private GameRefereesDBAccess grda = GameRefereesDBAccess.getInstance();
    private GameFansDBAccess gfda = GameFansDBAccess.getInstance();
    private FanGamesDBAccess fgda = FanGamesDBAccess.getInstance();
    private AlertDBAccess ada = AlertDBAccess.getInstance();
    private GameEventDBAccess geda = GameEventDBAccess.getInstance();
    private GameDBAccess gda = GameDBAccess.getInstance();
    private NotificationDBAccess nda = NotificationDBAccess.getInstance();

    public ArrayList<GameDTO> getGames() {
        HashMap<String, Game> rawGames = gda.conditionedSelect(new String[0]);
        ArrayList<GameDTO> games = new ArrayList<>();

        for(Game game : rawGames.values()){
            games.add(getGame(String.valueOf(game.getId())));
        }

        return games;
    }

    public GameDTO getGame(String gameId){
        Game game = gda.select(gameId);

        // get referees to game from DB and save notification for them
        ArrayList<Referee> referees = grda.select(gameId).getValue();
        for(Referee referee : referees){
            game.addRefereeToGame(referee);
        }

        // get fans to game from DB and save notification for them
        ArrayList<Fan> fans = gfda.select(gameId).getValue();
        for(Fan fan : fans){
            game.addFanToAlerts(fan);
        }

        // get events of the game
        HashMap<String, GameEvent> eventsMap = geda.conditionedSelect(new String[]{"GameId", gameId});
        ArrayList<GameEvent> events = new ArrayList<>(eventsMap.values());
        game.addEvents(events);

        return  new GameDTO(
                game.getId(),
                game.getHostTeam().getTeamName(),
                game.getGuestTeam().getTeamName(),
                game.getField(),
                game.getGameDate(),
                game.getReferees(),
                new ArrayList<>(convertEventsToEventsDTO(game, new ArrayList<>(game.getGameEvents().values()))),
                game.getGameScore()
        );
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
        Game game = gda.select(gameId);
        fgda.save(new Pair<>(username, new ArrayList<Game>(){{add(game);}}));
        logger.info(username + ": subscription was added to game " + gameId);
    }

    // ========================= Referee functions ========================
    // ====================================================================
    private GameEventDTO convertEventToEventDTO(Game game, GameEvent event) {
        return new GameEventDTO(event.getDateTime().toString(),Integer.toString(game.getId()), game.getHostTeam().getTeamName() + " - " + game.getGuestTeam().getTeamName(), Integer.toString(event.getGameMinutes()), event.getEventName().name(), event.getDescription());
    }

    private ArrayList<GameEventDTO> convertEventsToEventsDTO(Game game, ArrayList<GameEvent> events) {
        ArrayList<GameEventDTO> dtoEvents = new ArrayList<>();
        for (GameEvent event : events) {
            dtoEvents.add(convertEventToEventDTO(game, event));
        }
        return dtoEvents;
    }

    /**
     * UC 10.2
     * Returns a list of games that the referee referees at
     * @param username the referee's username
     * @return the list of games that the referee referees at
     */
    public ArrayList<GameDTO> getRefereeGames(String username) {
        Pair<String, ArrayList<Game>> retrievedGamesReferee = rgda.select(username);
        ArrayList<Game> rawGames = retrievedGamesReferee.getValue();
        ArrayList<Game> games = new ArrayList<>();

        for(Game game : rawGames){
            ArrayList<Referee> referees = grda.select(String.valueOf(game.getId())).getValue();
            game.setReferees(referees);
            games.add(game);
        }

        ArrayList<GameDTO> gamesDTO = new ArrayList<>();

        for (Game game : games) {
            gamesDTO.add(new GameDTO(
                    game.getId(),
                    game.getHostTeam().getTeamName(),
                    game.getGuestTeam().getTeamName(),
                    game.getField(),
                    game.getGameDate(),
                    game.getReferees(),
                    new ArrayList<>(convertEventsToEventsDTO(game, new ArrayList<>(game.getGameEvents().values()))),
                    game.getGameScore()
            ));
        }
        return gamesDTO;
    }

    // ========================= Event functions ========================
    // ====================================================================

//    public GameEventDTO getEvent(String eventId){
//
//    }

    /**
     * UC 10.3
     * Adds an event that took place during a game to its events list
     * @param gameId the match
     *
     */
    public GameDTO addGameEventToGame(String gameId, String minute, String eventName, String description) {
    ////// Create new game event and save it to DB //////
        LocalDateTime eventDate = LocalDateTime.now().withNano(0).withSecond(0);
        GameEvent gameEvent = new GameEvent(Integer.parseInt(gameId), eventDate, Integer.parseInt(minute), eventName, description);
        geda.save(gameEvent);

    ////// Add event to game and save notification of the event to DB for each game subscriber //////
        Game game = gda.select(gameId);
        Notification notification = new Notification(eventDate + ":" + '\n' + eventName + " in " + game.getHostTeam().getTeamName() + " - " + game.getGuestTeam().getTeamName() + " game in minute " + minute + ": " + description);
        nda.save(notification);
        HashMap<String, Notification> notificationMap = nda.conditionedSelect(new String[]{"Subject", notification.getSubject()});
        int nId = Integer.parseInt(notificationMap.keySet().iterator().next());
        notification.setId(nId);

        // get referees to game from DB and save notification for them
        ArrayList<Referee> referees = grda.select(gameId).getValue();
        for(Referee referee : referees){
            game.addRefereeToGame(referee);
            ada.save(new Pair<>(referee.getUserName(), new ArrayList<Notification>(){{add(notification);}}));
        }

        // get fans to game from DB and save notification for them
        ArrayList<Fan> fans = gfda.select(gameId).getValue();
        for(Fan fan : fans){
            game.addFanToAlerts(fan);
            ada.save(new Pair<>(fan.getUserName(), new ArrayList<Notification>(){{add(notification);}}));
        }

        // get events of the game
        HashMap<String, GameEvent> eventsMap = geda.conditionedSelect(new String[]{"GameId", gameId});
        ArrayList<GameEvent> events = new ArrayList<>(eventsMap.values());
        game.addEvents(events);

        // add event to game and send mails
        game.addEvent(gameEvent);
        logger.info(eventName + ": event was added to game " + gameId);

        return new GameDTO(
                game.getId(),
                game.getHostTeam().getTeamName(),
                game.getGuestTeam().getTeamName(),
                game.getField(),
                game.getGameDate(),
                game.getReferees(),
                new ArrayList<>(convertEventsToEventsDTO(game, new ArrayList<>(game.getGameEvents().values()))),
                game.getGameScore()
        );
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
//        Game game = gda.select(gameId);
//        GameEvent gameEvent = geda.select(eventId);
//
////        if (!game.getGameEvents().containsKey(Integer.parseInt(eventId))) {
////            throw new Exception("Not event of this game");
////        }
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
//       // game.changeEvent(gameEvent);
//        geda.update(gameEvent);
//
//        logger.info(eventId + ": event was changed in game " + gameId);
    }
}
