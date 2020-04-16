package domain;


import java.util.Calendar;

public class Budget {

    /**
     * quarter of year budget from 1 to 4
     */
    private int quarterOfYear;

    /**
     * year of the season
     */
    private int year;

    /**
     * toto, spongers, gameWinner
     */
    private double totalProfit;

    /**
     * salaries and registration fee every season
     */
    private double totalLoss;
    /**
     * default budget is one million
     */
    private double budget;


    public Budget(int year,  double budget, double totalProfit, double totalLoss, int quarterOfYear) {
        this.year = year;
        this.totalProfit = totalProfit;
        this.totalLoss = totalLoss;
        this.quarterOfYear = quarterOfYear;
        this.budget = budget;
    }


    // Constructor
    public Budget() {
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.totalProfit = 0;
        this.totalLoss = 0;
        this.quarterOfYear = 1;
        this.budget = 1000000; // default value of budget
    }




    //Setters

    /**
     * update the totalProfit and budget values
     * @param addToProfit
     * @return true if "addToProfit" POSITIVE INTEGER
     */
    public boolean setTotalProfit(double addToProfit) {
        if(addToProfit >= 0){
            this.totalProfit = this.totalProfit + addToProfit;
            this.budget = this.budget + addToProfit;
            return true;
        }
        return false;

    }

    /**
     * update the totalLoos and budget values
     * @param addToLoss
     * @return true if "addToLoss" POSITIVE INTEGER
     */
    public boolean setTotalLoss(double addToLoss) {
        if(addToLoss >= 0){
            this.totalLoss = this.totalLoss - addToLoss;
            this.budget = this.budget - addToLoss;
            return true;
        }
        return false;

    }

    //TODO: min budget to each league
    public void setBudget(double budget) {
        this.budget = budget;
    }

    public boolean setQuarterOfYear(int quarterOfYear) {
        if(quarterOfYear >=1 && quarterOfYear <=4){
            this.quarterOfYear = quarterOfYear;
            return true;
        }
        return false;


    }

    public void setYear(int year) {
        this.year = year;
    }

    //Getters

    public int getYear() {
        return year;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public double getTotalLoss() {
        return totalLoss;
    }

    public int getQuarterOfYear() {
        return quarterOfYear;
    }

    public double getBudget() {
        return budget;
    }
}
