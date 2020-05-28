package service.pojos;

public class FollowDTO {
    private final String gameId;
    private final String username;

    public FollowDTO(String gameId, String username) {
        this.gameId = gameId;
        this.username = username;
    }

    public String getGameId() {
        return gameId;
    }

    public String getUserName() {
        return username;
    }
}
