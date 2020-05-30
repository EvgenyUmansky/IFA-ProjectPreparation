package service.pojos;

public class TeamStatusDTO {
    String teamName;
    String status;

    public TeamStatusDTO(String teamName, String status) {
        this.teamName = teamName;
        this.status = status;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
