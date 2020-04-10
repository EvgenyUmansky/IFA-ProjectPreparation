package domain;

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
    public Referee(String userName, String password, String name, String mail, int qualification, RefereeType refereeType) {
        super(userName, password, name, mail);
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

/////////// Getters and Setters ///////////

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public RefereeType getRefereeType() {
        return refereeType;
    }

    public void setRefereeType(RefereeType refereeType) {
        this.refereeType = refereeType;
    }

    public Set<Game> getGames() {
        return games;
    }
}
