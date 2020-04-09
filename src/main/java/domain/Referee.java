package domain;

import java.util.HashSet;
import java.util.Set;

public class Referee extends Subscriber{
    int qualification; // From 1 to 5 (5 is the best league....)
    Set<Game> games;

/////////// Constructor ///////////
    public Referee(String userName, String password, String name, String mail, int qualification) {
        super(userName, password, name, mail);
        this.qualification = qualification;
        games = new HashSet<>();
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

    public Set<Game> getGames() {
        return games;
    }
}
