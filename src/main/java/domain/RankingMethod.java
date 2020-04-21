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

    public boolean setRankingMethod(int winPoints, int drawPoints, int loosPoints){
        if(winPoints > drawPoints && winPoints > loosPoints && drawPoints > loosPoints){
            this.winPoints = winPoints;
            this.loosPoints = loosPoints;
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
