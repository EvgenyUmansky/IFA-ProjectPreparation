package domain;

public class RankingMethod  {
    private int winPoints;
    private int loosPoints;
    private int drawPoints;


    // Constructor
    public RankingMethod() {
        this.winPoints = 3;
        this.loosPoints = 0;
        this.drawPoints = 1;
    }

    //Setters
    public void setWinPoints(int winPoints) {
        this.winPoints = winPoints;
    }

    public void setLoosPoints(int loosPoints) {
        this.loosPoints = loosPoints;
    }

    public void setDrawPoints(int drawPoints) {
        this.drawPoints = drawPoints;
    }

    //Getters
    public int getWinPoints() {
        return winPoints;
    }

    public int getLoosPoints() {
        return loosPoints;
    }

    public int getDrawPoints() {
        return drawPoints;
    }
}
