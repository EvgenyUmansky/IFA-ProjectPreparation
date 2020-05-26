package domain;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a football match
 */
public class Game {
    static AtomicInteger nextId = new AtomicInteger();
    private final int id;

    private League league;
    private Team hostTeam;
    private Team guestTeam;
    private Field field;
    private LocalDateTime gameDate;
    private final ArrayList<Referee> referees;
    private final HashMap<Integer, GameEvent> gameEvents;
    private int hostTeamScore;
    private int guestTeamScore;
    private final Alert alertFans;
    private final Alert alertReferees;

/////////// Constructors ///////////

    /**
     * Constructor
     * @param league the league in which the match is played
     * @param hostTeam the team that hosts the match in its stadium
     * @param guestTeam the visiting team
     * @param field the field in which the match is played
     * @param gameDateStr a String that represents the date of the match
     * @param referees a list of referees that referee the match
     */
    public Game(League league, Team hostTeam, Team guestTeam, Field field, String gameDateStr, ArrayList<Referee> referees) {
        // set id
        this.id = nextId.incrementAndGet();

        this.league = league;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.referees = referees;
        this.gameEvents = new HashMap<>();
        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.alertFans = new Alert();
        this.alertReferees = new Alert();

        // referees of the game automatically receives alerts
        addRefereesOfGameToAlerts();

        // Game date string format: "2016-11-09 11:44"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.gameDate = LocalDateTime.parse(gameDateStr, formatter);
    }

    /**
     * Constructor
     * @param league the league in which the match is played
     * @param hostTeam the team that hosts the match in its stadium
     * @param guestTeam the visiting team
     * @param field the field in which the match is played
     * @param gameDate the date of the match
     * @param referees a list of referees that referee the match
     */
    public Game(League league, Team hostTeam, Team guestTeam, Field field, LocalDateTime gameDate, ArrayList<Referee> referees) {
        // set id
        this.id = nextId.incrementAndGet();

        this.league = league;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.referees = referees;
        this.gameEvents = new HashMap<>();
        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.alertFans = new Alert();
        this.alertReferees = new Alert();

        // referees of the game automatically receives alerts
        addRefereesOfGameToAlerts();
        this.gameDate = gameDate;
    }

    public Game(int gameID, String hostTeam, String guestTeam, String fieldName, LocalDateTime gameDate, int hostTeamScore, int guestTeamScore){
        this.id = gameID;
        this.field = new Field(fieldName,0);
        this.hostTeam = new Team(hostTeam,field,null);
        this.guestTeam = new Team(guestTeam,null,null);
        this.gameDate = gameDate;
        this.hostTeamScore = hostTeamScore;
        this.guestTeamScore = guestTeamScore;

        this.gameEvents = new HashMap<>();
        this.referees = new ArrayList<>();
        this.alertFans = new Alert();
        this.alertReferees = new Alert();
    }

    public Game(int gameID, String hostTeam, String guestTeam, String fieldName, LocalDateTime gameDate, int hostTeamScore, int guestTeamScore, String leagueName, int season){
        this.id = gameID;
        this.field = new Field(fieldName,0);
        this.hostTeam = new Team(hostTeam,field,null);
        this.guestTeam = new Team(guestTeam,null,null);
        this.gameDate = gameDate;
        this.hostTeamScore = hostTeamScore;
        this.guestTeamScore = guestTeamScore;

        this.gameEvents = new HashMap<>();
        this.referees = new ArrayList<>();
        this.alertFans = new Alert();
        this.alertReferees = new Alert();
        this.league = new League(leagueName,season,true,0,0,0);
    }


/////////// Functionality ///////////

    /**
     * Adds a referee to the list of referees in this match
     * @param referee the added referee
     */
    public void addRefereeToGame(Referee referee){
        this.referees.add(referee);
        addRefereeToAlerts(referee);
    }

    /**
     * Removes a referee from the list of referees in this match
     * @param referee the removed referee
     */
    public void removeRefereeFromGame(Referee referee){
        this.referees.remove(referee);
        deleteRefereeFromAlerts(referee);
    }


    /**
     * UC 3.3
     * Adds a fan to the list of subscribers of the match
     * @param fan the added fan
     */
    public void addFanToAlerts(Fan fan){
        alertFans.addSubscriber(fan);
    }

    /**
     * UC 3.3
     * Removes a fan from the list of subscribers of the match
     * @param fan the removed fan
     */
    public void removeFanFromAlerts(Fan fan){
        alertFans.removeSubscriber(fan);
    }

    /**
     * Adds a referee to the list of subscribers of the match
     * @param referee the added referee
     */
    public void addRefereeToAlerts(Referee referee){
        alertReferees.addSubscriber(referee);
    }

    /**
     * Removes a referee from the list of subscribers of the match
     * @param referee the removed referee
     */
    public void deleteRefereeFromAlerts(Referee referee){
        alertReferees.removeSubscriber(referee);
    }

    /**
     * Adds all the referees of this match to its list of subscribers
     */
    private void addRefereesOfGameToAlerts(){
        for(Referee user : this.referees){
            addRefereeToAlerts(user);
        }
    }

    /**
     * Sends a notification with the score to the fan subscribers at full time
     * @return a Map that holds a Subscriber's username as key and a boolean value of true whether he has received the notification or false otherwise
     */
    public Map<String, Boolean> sendAlertScoreToFan()  {
        //TODO some logic with observer: when the game ends

        String title = "Score between " + this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName();
        String message = "The score of the game between " +  this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName() + " is " + getGameScore();
        AlertNotification alertNotification = new AlertNotification(title, message);

        return alertFans.sendAlert(alertNotification);
    }


    /**
     * Sends a reminder to the subscribers a day before the match
     * @return a Map that holds a Subscriber's username as key and a boolean value of true whether he has received the notification or false otherwise
     */
    public Map<String, Boolean> sendAlertCloseGame()  {
        //TODO some logic with observer: when remains one day

        String title =  "It's close! " + this.hostTeam.getTeamName() + " vs. " + this.guestTeam.getTeamName();
        String message = "Before the game between " +  this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName() + " remains " + "one day!";
        AlertNotification alertNotification = new AlertNotification(title, message);

        Map<String, Boolean> isSentMap = new HashMap<>();
        isSentMap.putAll(alertFans.sendAlert(alertNotification));
        isSentMap.putAll(alertReferees.sendAlert(alertNotification));

        return isSentMap;
    }


    /**
     * Sends a notification about a change in the date of the match to the subscribers
     * @return a Map that holds a Subscriber's username as key and a boolean value of true whether he has received the notification or false otherwise
     */
    public Map<String, Boolean> sendAlertChangeDateGame() {
        String title =  "The date has changed! " + this.hostTeam.getTeamName() + " vs. " + this.guestTeam.getTeamName();
        String message = "The new date of the game between " +  this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName() + " is " + this.gameDate.withNano(0).withSecond(0).toString();
        AlertNotification alertNotification = new AlertNotification(title, message);

        Map<String, Boolean> isSentMap = new HashMap<>();
        isSentMap.putAll(alertFans.sendAlert(alertNotification));
        isSentMap.putAll(alertReferees.sendAlert(alertNotification));

        return isSentMap;
    }


    /**
     * UC 10.3
     * Adds a game event to the list of game events in this match
     * @param event - Referee creates the event: game.addGameEvent(new GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription))
     */
    public void addEvent(GameEvent event) {
        this.gameEvents.put(event.getId(), event);

        // send alerts
        AlertNotification alertNotification = new AlertNotification("New event: " + hostTeam.getTeamName() + " vs " + guestTeam.getTeamName(), event.toString());
        alertFans.sendAlert(alertNotification);
        alertReferees.sendAlert(alertNotification);
    }


    /**
     * UC 10.4
     * Updates a game event in this match - executed by the main referee
     * @param event the game event
     */
    public void changeEvent(GameEvent event) {
        this.gameEvents.put(event.getId(), event);

        // send alerts
        AlertNotification alertNotification = new AlertNotification("Changed event: " + hostTeam.getTeamName() + " vs " + guestTeam.getTeamName(), event.toString());
        alertFans.sendAlert(alertNotification);
        alertReferees.sendAlert(alertNotification);
    }

    /**
     * Indicates if two games are the same
     * @param game the compared game
     * @return true it the games are the same one, false otherwise
     */
    public boolean isEqualGame(Game game){
        return this.getHostTeam().getTeamName().equals(game.getHostTeam().getTeamName())
                && this.getGuestTeam().getTeamName().equals(game.getGuestTeam().getTeamName())
                && this.getGameDate().compareTo(game.getGameDate()) == 0;
    }


    /**
     * UC 10.2
     * Retrieves all the matches that a given referee has refereed
     * @param referee the given referee
     * @return all the matches the referee has refereed
     */
    public static ArrayList<Game> getGamesByReferee(Referee referee){
        //TODO: Get data from DB (like SELECT * FROM GAMES WHERE Referee=username)
        Game mockGame = new Game(new League("Test league"), new Team("Test guest team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Team("Test team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Field("Test field", 500), LocalDateTime.now().withNano(0).withSecond(0), new ArrayList<Referee>() {{
            add(referee);
        }});
        mockGame.addEvent(new GameEvent(60, GameAlert.GOAL, "Messi did goal"));
        return new ArrayList<Game>() {{
            add(mockGame);
        }};
    }

/////////// Getters and Setters ///////////

    /**
     * Returns the id of game
     * @return the id of game
     */
    public int getId() {
        return this.id;
    }


    /**
     * Returns the league in which the match is played
     * @return the league in which the match is played
     */
    public League getLeague() {
        return league;
    }

    /**
     * Updates the league the match is played in
     * @param league the league the match is played in
     */
    public void setLeague(League league) {
        this.league = league;
    }

    /**
     * Returns the home team of this match
     * @return the home team of this match
     */
    public Team getHostTeam() {
        return hostTeam;
    }

    /**
     * Sets the home team to the given team
     * @param hostTeam the home team
     */
    public void setHostTeam(Team hostTeam) {
        this.hostTeam = hostTeam;
    }

    /**
     * Returns the visiting team of this match
     * @return the visiting team of this match
     */
    public Team getGuestTeam() {
        return guestTeam;
    }

    /**
     * Sets the visiting team to the given team
     * @param guestTeam the visiting team
     */
    public void setGuestTeam(Team guestTeam) {
        this.guestTeam = guestTeam;
    }

    /**
     * Returns the field this match is played at
     * @return the field this match is played at
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the field the match is played at to the given field
     * @param field the field the match is played at
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Returns the list of referees in this match
     * @return the list of referees in this match
     */
    public ArrayList<Referee> getReferees() {
        return referees;
    }

    /**
     * Returns the list of game events that took place in this match
     * @return the list of game events that took place in this match
     */
    public HashMap<Integer,GameEvent> getGameEvents() {
        return gameEvents;
    }

    /**
     * Returns the number of goals the home team scored
     * @return the number of goals the home team scored
     */
    public int getHostTeamScore() {
        return hostTeamScore;
    }

    /**
     * Updates the number of goals the home team scored
     * @param hostTeamScore the number of goals the home team scored
     */
    public void setHostTeamScore(int hostTeamScore) {
        this.hostTeamScore = hostTeamScore;
    }

    /**
     * Returns the number of goals the visiting team scored
     * @return the number of goals the visiting team scored
     */
    public int getGuestTeamScore() {
        return guestTeamScore;
    }

    /**
     * Updates the number of goals the visiting team scored
     * @param guestTeamScore the number of goals the visiting team scored
     */
    public void setGuestTeamScore(int guestTeamScore) {
        this.guestTeamScore = guestTeamScore;
    }

    /**
     * Returns the list of fan subscribers of this match
     * @return the list of fan subscribers of this match
     */
    public Alert getAlertFans() {
        return alertFans;
    }

    /**
     * Returns the list of referee subscribers of this match
     * @return the list of referee subscribers of this match
     */
    public Alert getAlertReferees() {
        return alertReferees;
    }

    /**
     * Returns the score of this match
     * @return the score at string in format: "0:0"
     */
    public String getGameScore(){
        return this.hostTeamScore + ":" + this.guestTeamScore;
    }

    /**
     * Returns the date of the match
     * @return the date of the match
     */
    public LocalDateTime getGameDate() {
        return gameDate;
    }

    /**
     * string format: "2016-11-09 11:44"
     * send alerts to all subscribers to the game
     * @param gameDateStr
     * @return dict: user, isSent
     */
    public Map<String, Boolean> setGameDate(String gameDateStr)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.gameDate = LocalDateTime.parse(gameDateStr, formatter);
        return sendAlertChangeDateGame();
    }

    /**
     * Sets the date of the game to the given date and notifies the subscribers
     * @param gameDate the new date of the game
     * @return a Map that holds a Subscriber's username as key and a boolean value of true whether he has received the message or false otherwise
     */
    public Map<String, Boolean> setGameDate(LocalDateTime gameDate)  {
        this.gameDate = gameDate;
        return sendAlertChangeDateGame();
    }

}
