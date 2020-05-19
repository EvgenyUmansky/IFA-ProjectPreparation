package domain;

import java.util.Calendar;

/**
 * This class represents the budget of a team per quarter.
 */
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


    //Constructors

    /**
     * Constructor
     * @param year a year
     * @param budget the amount of money that is the budget
     * @param totalProfit the total income of the team in the quarter
     * @param totalLoss the total outcome of the team in the quarter
     * @param quarterOfYear the quarter of the year
     */
    public Budget(int year,  double budget, double totalProfit, double totalLoss, int quarterOfYear) {
        this.year = year;
        this.totalProfit = totalProfit;
        this.totalLoss = totalLoss;
        this.quarterOfYear = quarterOfYear;
        this.budget = budget;
    }


    /**
     * Default constructor
     */
    public Budget() {
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.totalProfit = 0;
        this.totalLoss = 0;
        this.quarterOfYear = 1;
        this.budget = 1000000; // default value of budget
    }


    //Setters and Getters

    /**
     * Updates the totalProfit and budget values
     * @param addToProfit money credited to the budget and added to the incomes
     * @return true if the update was successful, false otherwise
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
     * Updates the totalLoss and budget values
     * @param addToLoss money debited from the budget and added to the outcomes
     * @return true if the update was successful, false otherwise
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
    /**
     * Sets the budget to the given amount
     * @param budget the given amount
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Sets the quarter of the year to the given quarter
     * @param quarterOfYear the given quarter of the year
     * @return true if the update was successful, false otherwise
     */
    public boolean setQuarterOfYear(int quarterOfYear) {
        if(quarterOfYear >=1 && quarterOfYear <=4){
            this.quarterOfYear = quarterOfYear;
            return true;
        }
        return false;
    }

    /**
     * Sets the year to the given year
     * @param year the given year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the year
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the total amount of incomes
     * @return the total amount of incomes
     */
    public double getTotalProfit() {
        return totalProfit;
    }

    /**
     * Returns the total amount of outcomes
     * @return the total amount of outcomes
     */
    public double getTotalLoss() {
        return totalLoss;
    }

    /**
     * Returns the quarter of the year
     * @return the quarter of the year
     */
    public int getQuarterOfYear() {
        return quarterOfYear;
    }

    /**
     * Returns the budget
     * @return the budget
     */
    public double getBudget() {
        return budget;
    }

    // TODO: Implement 6.7
}
