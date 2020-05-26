package ExternalSystemsAccess;

public class TaxProxy implements TaxSystemAccess{

    @Override
    public double getTaxRate(double revenueAmount) {
       TaxSystem taxSystem = new TaxSystem();
       return taxSystem.getTaxRate(revenueAmount);
    }
}
