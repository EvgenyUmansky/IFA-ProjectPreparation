package domain.controllers;

import DataAccess.*;
import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import service.pojos.GameDTO;

import javax.security.auth.login.CredentialNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    static Logger logger = Logger.getLogger(GameController.class.getName());

    private RefereeGamesDBAccess rgda = RefereeGamesDBAccess.getInstance();
    private FanGamesDBAccess fgda = FanGamesDBAccess.getInstance();
    private AlertDBAccess ada = AlertDBAccess.getInstance();
    private DBAccess<GameEvent> geda = GameEventDBAccess.getInstance();
    private GameDBAccess gda = GameDBAccess.getInstance();
    private DBAccess<League> lda = LeagueDBAccess.getInstance();

//    private DBAccess<User> uda = UserDBAccess.getInstance();
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
         return gda.select(gameId);

//        Game game = gda.select(gameId);
//        String[] conditions = new String[] {"leagueName", game.getLeague().getLeagueName(),"year",Integer.toString(game.getLeague().getSeason())};
//        League league = game.getLeague();
//        league = lda.conditionedSelect(conditions).get(game.getLeague().getLeagueName());
//
//        return game;
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
    public GameDTO addGameEventToGame(String gameId, String eventName, String description) {
    ////// Create new game event and save it to DB //////
        LocalDateTime gameDate = LocalDateTime.now().withNano(0).withSecond(0);
        GameEvent gameEvent = new GameEvent(Integer.parseInt(gameId), gameDate, eventName, description);
        geda.save(gameEvent);

    ////// Add event to game and save notification of the event to DB for each game subscriber //////
        Game game = gda.select(gameId);
        Notification notification = new Notification("New event: " + game.getHostTeam().getTeamName() + " vs " + game.getGuestTeam().getTeamName() + ": " + gameEvent.toString());

        // get referees to game from DB and save notification for them
        ArrayList<Referee> referees = rgda.selectRefereesOfGame(gameId).getValue();
        for(Referee referee : referees){
            game.addRefereeToGame(referee);
            ada.save(new Pair<>(referee.getUserName(), new ArrayList<Notification>(){{add(notification);}}));
        }

        // get fans to game from DB and save notification for them
        ArrayList<Fan> fans = fgda.selectFansOfGame(gameId).getValue();
        for(Fan fan : fans){
            game.addFanToAlerts(fan);
            ada.save(new Pair<>(fan.getUserName(), new ArrayList<Notification>(){{add(notification);}}));
        }

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
                new ArrayList<>(game.getGameEvents().values()),
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
        Game game = gda.select(gameId);
        GameEvent gameEvent = geda.select(eventId);

//        if (!game.getGameEvents().containsKey(Integer.parseInt(eventId))) {
//            throw new Exception("Not event of this game");
//        }

        long diffInHours = ChronoUnit.HOURS.between(game.getGameDate(), LocalDateTime.now());
        if (diffInHours > 5) {
            throw new Exception("Not allowed to change the game events: out of time");
        }

        // dateTimeStr == null - the dateTimeStr UI field is not filled
        if (dateTimeStr != null) {
            gameEvent.setGameDate(dateTimeStr);
        }

        // get minute manually from referee in UI
        if(Integer.parseInt(minuteOfEvent) > -1 && Integer.parseInt(minuteOfEvent) < 90){
            gameEvent.setGameMinutes(Integer.parseInt(minuteOfEvent));
        }

        // eventName == null - the eventName UI field is not filled
        if (eventName != null) {
            gameEvent.setEventName(GameAlert.valueOf(eventName));
        }

        // description == null - the description UI field is not filled
        if (description != null) {
            gameEvent.setDescription(description);
        }

       // game.changeEvent(gameEvent);
        geda.update(gameEvent);

        logger.info(eventId + ": event was changed in game " + gameId);
    }
}
