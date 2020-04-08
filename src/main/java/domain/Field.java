package domain;
import java.util.HashSet;
import java.util.Set;

public class Field {

    private static int staticFieldId = 0;
    private int fieldId;

    Team teamOwns;
    double propertyCost;
    Set<Integer> gamesOnField;

/////////// Constructors ///////////
    public Field(Team teamOwns, double propertyCost) {
        staticFieldId++;
        this.fieldId = staticFieldId;
        this.teamOwns = teamOwns;
        this.propertyCost = propertyCost;
        gamesOnField = new HashSet<>();
    }

    public void addGame(int gameId){
        this.gamesOnField.add(gameId);
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

    public Set<Integer> getGamesOnField() {
        return gamesOnField;
    }
}
