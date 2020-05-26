package ExternalSystemsAccess;

public interface AccountingSystemAccess {

    boolean addPayment(String teamName, String date, double amount);
}
