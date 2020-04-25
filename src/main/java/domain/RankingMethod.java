package domain;

/**
 * This class represents a ranking method of the teams in a league according to matches results
 */
public class RankingMethod  {

    private int winPoints;
    private int loosPoints;
    private int drawPoints;


    // ========================= Constructor ==========================

    /**
     * Constructor
     */
    public RankingMethod() {
        this.winPoints = 3;
        this.loosPoints = 0;
        this.drawPoints = 1;
    }

    // ========================= Setters ==========================

    /**
     * Sets the amount of points each team receives according to the result of a match
     * @param winPoints the amount of points given for a win
     * @param drawPoints the amount of points given for a draw
     * @param loosPoints the amount of points given for a loss
     * @return true if the update was successful, false otherwise
     */
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

    /**
     * Returns the amount of points given for a win
     * @return the amount of points given for a win
     */
    public int getWinPoints() {
        return winPoints;
    }

    /**
     * Returns the amount of points given for a loss
     * @return the amount of points given for a loss
     */
    public int getLoosPoints() {
        return loosPoints;
    }

    /**
     * Returns the amount of points given for a draw
     * @return the amount of points given for a draw
     */
    public int getDrawPoints() {
        return drawPoints;
    }
}
