package domain;


import java.util.Calendar;

public class Budget {

    /**
     * quarter of year budget from 1 to 4
     */
    private int quarterOfYear;
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
        this.budget = 1000000;
    }




    //Setters
    public void setTotalProfit(double addToProfit) {
        this.totalProfit = this.totalProfit + addToProfit;
        this.budget = this.budget + addToProfit;
    }

    public void setTotalLoss(double addToLoss) {
        this.totalLoss = this.totalLoss - addToLoss;
        this.budget = this.budget - addToLoss;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setQuarterOfYear(int quarterOfYear) {
        this.quarterOfYear = quarterOfYear;
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
