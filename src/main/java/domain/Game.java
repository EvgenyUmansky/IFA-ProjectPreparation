package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private static int staticGameId = 0;

    private int gameId;
    private LeaguePerSeason leaguePerSeason;
    private Team hostTeam;
    private Team guestTeam;
    private Field field;
    private LocalDateTime gameDate;
    private ArrayList<Referee> referees;
    private Map<Integer, GameEvent> gameEvents;
    private int hostTeamScore;
    private int guestTeamScore;
    private int gameMinutes;
    private Alert alertFans;
    private Alert alertReferees;

/////////// Constructor ///////////
    public Game(LeaguePerSeason leaguePerSeason, Team hostTeam, Team guestTeam, Field field, String gameDateStr, ArrayList<Referee> referees) {
        staticGameId++;
        this.gameId = staticGameId;
        this.leaguePerSeason = leaguePerSeason;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.referees = referees;
        this.gameEvents = new HashMap<>();
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


/////////// Functionality ///////////

    // UC 3.3

    /**
     * Add fan to list of subscribers on a game
     * @param fan - want to get news about a game
     */
    public void addFanToAlerts(Subscriber fan){
        if(fan.isMail()) {
            this.alertFans.addToMailSet(fan);
        }
        else{
            this.alertFans.addToSystemSet(fan);
        }
    }

    /**
     * Add referee to list of subscribers on a game
     * @param referee - want to get news about a game
     */
    public void addRefereeToAlerts(Subscriber referee){
        if(referee.isMail()) {
            this.alertFans.addToMailSet(referee);
        }
        else{
            this.alertFans.addToSystemSet(referee);
        }
    }

    private void addRefereesOfGameToAlerts(){
        for(Subscriber user : this.referees){
            addRefereeToAlerts(user);
        }
    }

    /**
     * Send score to subscribers when game ends
     */
    public void sendAlertScoreToFan(){

        // some logic with observer...

        String title = "Score between " + this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName();
        String message = "The score of the game between " +  this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName() + "is " + getGameScore();
        AlertNotification alertNotification = new AlertNotification(title, message);

        alertFans.sendAlert(alertNotification);
    }

    public void sendAlertCloseGame(){

        // some logic with observer...

        String title =  "It's close! " + this.hostTeam.getTeamName() + " vs. " + this.guestTeam.getTeamName();
        String message = "Before the game between " +  this.hostTeam.getTeamName() + " and " + this.guestTeam.getTeamName() + "remains " + "...";
        AlertNotification alertNotification = new AlertNotification(title, message);

        alertFans.sendAlert(alertNotification);
        alertReferees.sendAlert(alertNotification);
    }


    /**
     * add game event to list of game events
     * @param event - Referee creates the event: game.addGameEvent(new GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription))
     * @return true if success, false if not
     */
    //
    public boolean addGameEvent(GameEvent event){
        // date time of event earlier than game
        if(event.getDateTime().compareTo(this.gameDate) <= 0){
            return false;
        }

        this.gameEvents.put(event.getGameEventId(), event);
        return true;
    }



/////////// Getters and Setters ///////////
    public int getGameId() {
        return gameId;
    }

    public LeaguePerSeason getLeaguePerSeason() {
        return leaguePerSeason;
    }

    public void setLeaguePerSeason(LeaguePerSeason leaguePerSeason) {
        this.leaguePerSeason = leaguePerSeason;
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

    public void setReferees(ArrayList<Referee> referees) {
        this.referees = referees;
    }

    public Map<Integer, GameEvent> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(Map<Integer, GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
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
     * @param gameDateStr
     */
    public void setGameDate(String gameDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.gameDate = LocalDateTime.parse(gameDateStr, formatter);
    }
}
