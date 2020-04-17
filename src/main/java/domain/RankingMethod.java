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

    public RankingMethod setWinPoints(int winPoints) {
        this.winPoints = winPoints;
        return this;
    }

    public RankingMethod setLoosPoints(int loosPoints) {
        this.loosPoints = loosPoints;
        return this;
    }

    public RankingMethod setDrawPoints(int drawPoints) {
        this.drawPoints = drawPoints;
        return this;
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
