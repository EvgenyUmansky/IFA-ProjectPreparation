package DataAccess;

public interface ExternalSystemsAccess {

    boolean addPayment(String teamName, String date, double amount);

    double revenueAmount(double getTaxRate);
}
