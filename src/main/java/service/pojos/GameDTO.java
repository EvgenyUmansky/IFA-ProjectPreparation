package service.pojos;

public class GameDTO {
    private String gameId;
    private String hostTeam;
    private String guestTeam;
    private String stadium; //The team's main stadium, where they play official matches
    private String[] fields; //All the team's fields, including the stadium and training fields
    private String[] players;
    private String[] coaches;
    private String[] managers;
    private String[] owners;
    private String teamStatus;
}
