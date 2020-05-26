package ExternalSystemsAccess;

public class TaxSystem implements TaxSystemAccess {

    private double lowTaxRate;
    private double mediumTaxRate;
    private double highTaxRate;

    public TaxSystem() {
        lowTaxRate = 0.1;
        mediumTaxRate = 0.25;
        highTaxRate = 0.5;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        if (revenueAmount > 0 && revenueAmount <= 10000)
            return revenueAmount * lowTaxRate;

        if (revenueAmount > 10000 && revenueAmount <= 50000)
            return revenueAmount * mediumTaxRate;

        if (revenueAmount > 50000)
            return revenueAmount * highTaxRate;

        return 0;
    }


    public double getLowTaxRate() {
        return lowTaxRate;
    }

    public void setLowTaxRate(double lowTaxRate) {
        this.lowTaxRate = lowTaxRate;
    }

    public double getMediumTaxRate() {
        return mediumTaxRate;
    }

    public void setMediumTaxRate(double mediumTaxRate) {
        this.mediumTaxRate = mediumTaxRate;
    }

    public double getHighTaxRate() {
        return highTaxRate;
    }

    public void setHighTaxRate(double highTaxRate) {
        this.highTaxRate = highTaxRate;
    }
}
