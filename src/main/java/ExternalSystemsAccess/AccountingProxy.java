package ExternalSystemsAccess;

public class AccountingProxy implements AccountingSystemAccess {


    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        AccountingSystem accountingSystem = new AccountingSystem();
        return accountingSystem.addPayment(teamName, date, amount);
    }
}
