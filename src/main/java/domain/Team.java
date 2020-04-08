package domain;

public class Team {

    private String teamName;
    private Field myField;
    // Constructor
    public Team(String name, Field field)
    {
        this.teamName = name;
        this.myField = field;
    }

    public Field getMyField() {
        return myField;
    }

    public String getTeamName() {
        return teamName;
    }
}
