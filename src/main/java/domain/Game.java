package domain;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private League league;
    private Team hostTeam;
    private Team guestTeam;
    private Field field;
    private LocalDateTime gameDate;
    private ArrayList<Referee> referees;
    private ArrayList<GameEvent> gameEvents;
    private int hostTeamScore;
    private int guestTeamScore;
    private int gameMinutes;
    private Alert alertFans;
    private Alert alertReferees;

/////////// Constructor ///////////
    public Game(League league, Team hostTeam, Team guestTeam, Field field, String gameDateStr, ArrayList<Referee> referees) {
        this.league = league;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.referees = referees;
        this.gameEvents = new ArrayList<>();
        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.gameMinutes = 0;
        this.alertFans = new Alert();
        this.alertReferees = new Alert();

        // referees of the game automatically receives alerts
        addRefereesOfGameToAlerts();

        // Game date string format: "2016-11-09 11:44"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.gameDate = LocalDateTime.parse(gameDateStr, formatter);
    }

    public Game(League league, Team hostTeam, Team guestTeam, Field field, LocalDateTime gameDate, ArrayList<Referee> referees) {
        this.league = league;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.referees = referees;
        this.gameEvents = new ArrayList<>();
        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.gameMinutes = 0;
        this.alertFans = new Alert();
        this.alertReferees = new Alert();

        // referees of the game automatically receives alerts
        addRefereesOfGameToAlerts();

        this.gameDate = gameDate;
    }


/////////// Functionality ///////////
    public void addRefereeToGame(Referee referee){
        this.referees.add(referee);
        addRefereeToAlerts(referee);
    }

    public void removeRefereeFromGame(Referee referee){
        this.referees.remove(referee);
        deleteRefereeFromAlerts(referee);
    }


    // UC 3.3

    /**
     * Add fan to list of subscribers on a game
     * @param fan - want to get news about a game
     */
    public void addFanToAlerts(Fan fan){
        alertFans.addSubscriber(fan);
    }

    public void removeFanFromAlerts(Fan fan){
        alertFans.removeSubscriber(fan);
    }

    /**
     * Add referee to list of subscribers on a game
     * @param referee - want to get news about a game
     */
    public void addRefereeToAlerts(Referee referee){
        alertReferees.addSubscriber(referee);
    }

    public void deleteRefereeFromAlerts(Referee referee){
        alertReferees.removeSubscriber(referee);
    }

    private void addRefereesOfGameToAlerts(){
        for(Referee user : this.referees){
            addRefereeToAlerts(user);
        }
    }

    /**
     * Send score to fans when game ends
     * @return dictionary: userName, isSent
     */
    public Map<String, Boolean> sendAlertScoreToFan()  {

        //TODO some logic with observer: when the game ends

        String title = "Score between " + this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName();
        String message = "The score of the game between " +  this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName() + " is " + getGameScore();
        AlertNotification alertNotification = new AlertNotification(title, message);

        return alertFans.sendAlert(alertNotification);
    }


    /**
     * Send alert to fans and referees when remains one day before a game
     * @return dictionary: userName, isSent
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
     * Send alert to fans and referees when date of the game changed
     * @return dictionary: userName, isSent
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
     * add game event to list of game events
     * @param event - Referee creates the event: game.addGameEvent(new GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription))
     * @return true if success, false if not
     */
    // U.C 10.3
    public void addEvent(GameEvent event) throws Exception {
        // date time of event earlier than game
        if(event.getDateTime().compareTo(this.gameDate) <= 0){
            throw new Exception("The date of the game is invalid");
        }

        this.gameEvents.add(event);

        // send alerts
        AlertNotification alertNotification = new AlertNotification("New event: " + hostTeam.getTeamName() + " vs " + guestTeam.getTeamName(), event.toString());
        alertFans.sendAlert(alertNotification);
        alertReferees.sendAlert(alertNotification);
    }

    // UC 10.4 - update/change game events by main referee
    public void changeEvent(GameEvent event, String dateTimeStr, int gameMinutes, GameAlert eventName, String description) throws Exception {

        if (!this.getGameEvents().contains(event)) {
            throw new Exception("Not event of this game");
        }

        long diffInHours = ChronoUnit.HOURS.between(this.getGameDate(), LocalDateTime.now());
        if (diffInHours > 5) {
            throw new Exception("Not allowed to change the game events: out of time");
        }

        // dateTimeStr == null - the dateTimeStr UI field is not filled
        if (dateTimeStr != null) {
            event.setGameDate(dateTimeStr);
        }

        // gameMinutes == -1 - the gameMinutes UI field is not filled
        if (gameMinutes > -1) {
            event.setGameMinutes(gameMinutes);
        }

        // eventName == null - the eventName UI field is not filled
        if (eventName != null) {
            event.setEventName(eventName);
        }

        // description == null - the description UI field is not filled
        if (description != null) {
            event.setDescription(description);
        }

        // send alerts
        AlertNotification alertNotification = new AlertNotification("Changed event: " + hostTeam.getTeamName() + " vs " + guestTeam.getTeamName(), event.toString());
        alertFans.sendAlert(alertNotification);
        alertReferees.sendAlert(alertNotification);
    }

    public boolean isEqualGame(Game game){
        return this.getHostTeam().getTeamName().equals(game.getHostTeam().getTeamName())
                && this.getGuestTeam().getTeamName().equals(game.getGuestTeam().getTeamName())
                && this.getGameDate().compareTo(game.getGameDate()) == 0;
    }

    // UC 10.2 - get all games the referee judge
    public static ArrayList<Game> getGamesByReferee(Referee referee){
        //TODO: Get data from DB (like SELECT * FROM GAMES WHERE Referee=username)
        return new ArrayList<Game>() {{
            add(new Game(new League("Test league"), new Team("Test guest team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Team("Test team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Field("Test field", 500), LocalDateTime.now().withNano(0).withSecond(0), new ArrayList<Referee>() {{
                add(referee);
            }}));
        }};
    }

/////////// Getters and Setters ///////////

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Team getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(Team hostTeam) {
        this.hostTeam = hostTeam;
    }

    public Team getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(Team guestTeam) {
        this.guestTeam = guestTeam;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public ArrayList<Referee> getReferees() {
        return referees;
    }

    public ArrayList<GameEvent> getGameEvents() {
        return gameEvents;
    }

    public int getHostTeamScore() {
        return hostTeamScore;
    }

    public void setHostTeamScore(int hostTeamScore) {
        this.hostTeamScore = hostTeamScore;
    }

    public int getGuestTeamScore() {
        return guestTeamScore;
    }

    public void setGuestTeamScore(int guestTeamScore) {
        this.guestTeamScore = guestTeamScore;
    }

    public int getGameMinutes() {
        return gameMinutes;
    }

    public void setGameMinutes(int gameMinutes) {
        this.gameMinutes = gameMinutes;
    }

    public Alert getAlertFans() {
        return alertFans;
    }

    public Alert getAlertReferees() {
        return alertReferees;
    }

    /**
     *
     * @return string in format: "0:0"
     */
    public String getGameScore(){
        return this.hostTeamScore + ":" + this.guestTeamScore;
    }

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

    public Map<String, Boolean> setGameDate(LocalDateTime gameDate)  {
        this.gameDate = gameDate;
        return sendAlertChangeDateGame();
    }

}
