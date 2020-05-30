package service.pojos;

public class LeagueDTO {
    String leagueName;
    String season;
    String scheduling;
    String winPoints;
    String losePoints;
    String drawPoints;

    public LeagueDTO(String leagueName, String season, String scheduling, String winPoints, String losePoints, String drawPoints) {
        this.leagueName = leagueName;
        this.season = season;
        this.scheduling = scheduling;
        this.winPoints = winPoints;
        this.losePoints = losePoints;
        this.drawPoints = drawPoints;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getScheduling() {
        return scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling = scheduling;
    }

    public String getWinPoints() {
        return winPoints;
    }

    public void setWinPoints(String winPoints) {
        this.winPoints = winPoints;
    }

    public String getLosePoints() {
        return losePoints;
    }

    public void setLosePoints(String losePoints) {
        this.losePoints = losePoints;
    }

    public String getDrawPoints() {
        return drawPoints;
    }

    public void setDrawPoints(String drawPoints) {
        this.drawPoints = drawPoints;
    }
}
