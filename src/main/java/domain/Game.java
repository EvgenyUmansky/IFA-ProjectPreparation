package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Game {

    private static int staticGameId = 0;

    private int gameId;
    private LeaguePerSeason leaguePerSeason;
    private Team hostTeam;
    private Team guestTeam;
    private Field field;
    private LocalDateTime gameDate;
    private ArrayList<Referee> referees;
    private InGameEvents inGameEvents;
    private int hostTeamScore;
    private int guestTeamScore;
    private int gameMinutes;
    private Alert alert;

/////////// Constructor ///////////
    public Game(LeaguePerSeason leaguePerSeason, Team hostTeam, Team guestTeam, Field field, String gameDateStr, ArrayList<Referee> referees) {
        staticGameId++;
        this.gameId = staticGameId;
        this.leaguePerSeason = leaguePerSeason;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.field = field;
        this.referees = referees;
        this.inGameEvents = new InGameEvents();
        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.gameMinutes = 0;
        this.alert = new Alert();

        // Game date string format: "2016-11-09 11:44"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.gameDate = LocalDateTime.parse(gameDateStr, formatter);
    }


/////////// Functionality ///////////

    // UC 3.3
    public void addSubscriber(Subscriber user, boolean isMail){
        if(isMail) {
            this.alert.addToMailSet(user);
        }
        else{
            this.alert.addToSystemSet(user);
        }
    }

    public void sendAlertScore(){

        // some logic with observer...

        alert.sendAlert("The score of the game between " +  "..." + "is " + getGameScore() );
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

    public InGameEvents getInGameEvents() {
        return inGameEvents;
    }

    public void setInGameEvents(InGameEvents inGameEvents) {
        this.inGameEvents = inGameEvents;
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
