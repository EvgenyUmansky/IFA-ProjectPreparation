package domain;
import java.util.HashSet;
import java.util.Set;

public class Field {

    private static int staticFieldId = 0;
    private int fieldId;

    Team teamOwns;
    double propertyCost;
    Set<Game> gamesOnField;

/////////// Constructors ///////////
    public Field(Team teamOwns, double propertyCost) {
        staticFieldId++;
        this.fieldId = staticFieldId;
        this.teamOwns = teamOwns;
        this.propertyCost = propertyCost;
        gamesOnField = new HashSet<>();
    }


/////////// Functionality ///////////
    public void addGame(Game game){
        this.gamesOnField.add(game);
    }
    public void removeGame(Game game){
        this.gamesOnField.remove(game);
    }


/////////// Getters and Setters ///////////
    public int getFieldId() {
        return fieldId;
    }

    public Team getTeamOwns() {
        return teamOwns;
    }

    public void setTeamOwns(Team teamOwns) {
        this.teamOwns = teamOwns;
    }

    public Set<Game> getGamesOnField() {
        return gamesOnField;
    }
}
