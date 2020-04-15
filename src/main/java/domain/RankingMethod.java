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
    public boolean setWinPoints(int winPoints) {
        if(winPoints > 0 && winPoints > drawPoints && winPoints> loosPoints){
            this.winPoints = winPoints;
            return true;
        }
        return false;
    }

    public boolean setLoosPoints(int loosPoints) {
        if(loosPoints < drawPoints && loosPoints < winPoints){
            this.loosPoints = loosPoints;
            return true;
        }
        return false;
    }

    public boolean setDrawPoints(int drawPoints) {
        if(drawPoints > loosPoints && drawPoints <winPoints){
            this.drawPoints = drawPoints;
            return true;
        }
        return false;
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
