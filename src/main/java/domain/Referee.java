package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Referee extends Subscriber{
    int qualification; // From 1 to 5 (5 is the best league....)
    RefereeType refereeType;
    Set<Game> games;
    boolean acceptedRequest;



    public boolean isAcceptedRequest() {
        return acceptedRequest;
    }

    public void setAcceptedRequest(boolean acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }

    /////////// Constructor ///////////
    public Referee(String userName, String mail, boolean isMail, int qualification, RefereeType refereeType) {
        super(userName, mail, isMail);
        this.qualification = qualification;
        this.refereeType = refereeType;
        games = new HashSet<>();
        this.acceptedRequest = false;
    }

/////////// Functionality ///////////

    // add game referee judge
    public void addGame(Game game){
        games.add(game);
    }

    // remove game referee judge
    public void removeGame(Game game){
        games.remove(game);
    }

    // remove all games referee judge
    public void clearGames(){
        games = new HashSet<>();
    }

    // UC 10.3 - create new game event and add it to list of game events of the game
    public boolean updateGameEvent(Game game, String dateTime, int gameMinutes, GameAlert eventName, String subscription){

        if(!this.games.contains(game)){
            System.out.println("The referee does not judge this game");
            return false;
        }

        // new GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription)
        game.addGameEvent(new GameEvent(dateTime, gameMinutes, eventName, subscription));
        return true;
    }

    // UC 10.4 - update/change game events by main referee
    public boolean changeGameEvent(Game game, int gameEventId, String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription ){
        if(!this.refereeType.equals(RefereeType.MAIN)){
            System.out.println("Not MAIN referee");
            return false;
        }

        long diffInHours = ChronoUnit.HOURS.between(game.getGameDate(), LocalDateTime.now());
        if(diffInHours > 5){
            System.out.println("Not allowed to change the game events: out of time");
            return false;
        }

        GameEvent gameEvent = game.getGameEvents().get(gameEventId);

        if(!dateTimeStr.isEmpty()){
            gameEvent.setGameDate(dateTimeStr);
        }

        if(gameMinutes > -1){
            gameEvent.setGameMinutes(gameMinutes);
        }

        if(eventName != null){
            gameEvent.setEventName(eventName);
        }

        if(!subscription.isEmpty()){
            gameEvent.setDescription(subscription);
        }

        return true;
    }


/////////// Getters and Setters ///////////

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        if(qualification < 1 || qualification > 5){
            System.out.println("Qualification must be between 1 to 5, the qualification is not changed");
            return;
        }
        this.qualification = qualification;
    }

    public RefereeType getRefereeType() {
        return refereeType;
    }

    public void setRefereeType(RefereeType refereeType) {
        if(refereeType == null){
            System.out.println("RefereeType is empty");
            return;
        }

        this.refereeType = refereeType;
    }

    public Set<Game> getGames() {
        return games;
    }
}
