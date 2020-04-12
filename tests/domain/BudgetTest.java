package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {
    Budget budgetQ1 = new Budget(1998, 5000, 0, 0, 1);
    Budget budgetQ2 = new Budget();
    @Test
    void setTotalProfit() {
        assertEquals(true,budgetQ1.setTotalProfit(200));
        assertEquals(false,budgetQ1.setTotalProfit(-800));
    }

    @Test
    void setTotalLoss() {
        assertEquals(true,budgetQ1.setTotalLoss(200));
        assertEquals(false,budgetQ1.setTotalLoss(-800));

    }

    @Test
    void setBudget() {
        budgetQ1.setBudget(1000000);
        assertEquals(budgetQ2.getBudget(), budgetQ1.getBudget());

    }

    @Test
    void setQuarterOfYear() {
        assertEquals(true,budgetQ1.setQuarterOfYear(2));
        assertEquals(false, budgetQ2.setQuarterOfYear(5));

    }

    @Test
    void setYear() {
        assertEquals(1998,budgetQ1.getYear());
        budgetQ1.setYear(2000);
        assertEquals(2000,budgetQ1.getYear());

    }

    @Test
    void getYear() {
        assertEquals(1998,budgetQ1.getYear());
        assertEquals(2020,budgetQ2.getYear());

    }

    @Test
    void getTotalProfit() {
        assertEquals(0,budgetQ1.getTotalProfit());

    }

    @Test
    void getTotalLoss() {
        assertEquals(0,budgetQ1.getTotalLoss());

    }

    @Test
    void getQuarterOfYear() {
        assertEquals(1,budgetQ1.getQuarterOfYear());

    }

    @Test
    void getBudget() {
        assertEquals(5000,budgetQ1.getBudget());
        assertEquals(1000000,budgetQ2.getBudget());

    }
}