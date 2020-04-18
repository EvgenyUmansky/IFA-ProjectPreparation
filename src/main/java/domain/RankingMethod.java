package domain;

public class RankingMethod  {
    private int winPoints;
    private int loosPoints;
    private int drawPoints;


    // ========================= Constructor ==========================

    public RankingMethod() {
        this.winPoints = 3;
        this.loosPoints = 0;
        this.drawPoints = 1;
    }

    // ========================= Setters ==========================

    public boolean setWinPoints(int winPoints) {
        if(winPoints > this.loosPoints && winPoints > this.drawPoints){
            this.winPoints = winPoints;
            return true;
        }
       return false;

    }

    public boolean setLoosPoints(int loosPoints) {
        if(loosPoints < this.drawPoints && loosPoints < this.winPoints){
            this.loosPoints = loosPoints;
            return true;
        }
        return false;
    }

    public boolean setDrawPoints(int drawPoints) {
        if(drawPoints > this.loosPoints && drawPoints < this.winPoints){
            this.drawPoints = drawPoints;
            return true;
        }

        return false;
    }

    // ========================= Getters ==========================

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
