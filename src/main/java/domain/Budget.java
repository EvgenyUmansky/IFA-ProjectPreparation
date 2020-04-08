package domain;


import java.util.Calendar;

public class Budget {

    //TODO: רבעון
    private int year;
    private double totalProfit;

    public Budget(int year, double totalProfit, double totalLoss) {
        this.year = year;
        this.totalProfit = totalProfit;
        this.totalLoss = totalLoss;
    }

    private double totalLoss;

    // Constructor
    public Budget() {
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.totalProfit = 0;
        this.totalLoss = 0;
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
}
