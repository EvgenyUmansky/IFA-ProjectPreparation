package ExternalSystemsAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountingSystem implements AccountingSystemAccess {

    private static HashMap<String, ArrayList<Payment>> payments = new HashMap<>();

    public AccountingSystem() {
    }

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        if(amount < 0 || date == null || teamName == null || teamName.equals("")){
            return false;
        }

        if(payments.containsKey(teamName)){
            payments.get(teamName).add(new Payment(date,amount));
        }
        else{
            ArrayList<Payment> teamPayments = new ArrayList<>();
            teamPayments.add(new Payment(date,amount));
            payments.put(teamName,teamPayments);
        }

        return true;
    }

    /**
     * Removes a payment from the accounting system
     * @param teamName the team's name
     * @param date the date of the payment
     * @param amount the amount of the payment
     * @return true if the payment removal was successful, false otherwise
     */
    public boolean removePayment(String teamName, String date, double amount){
        Payment removedPayment = new Payment(date,amount);
        if(amount < 0 || date == null || teamName == null || teamName.equals("")){
            return false;
        }

        //in case the team doesn't exist in the system
        if(!(payments.containsKey(teamName))){
            return false;
        }

        ArrayList<Payment> teamPayments = payments.get(teamName);
        for(Payment payment : teamPayments){
            if(removedPayment.equals(payment)){
                teamPayments.remove(payment);
                return true;
            }
        }

        //in case the team never payed such payment
        return false;
    }

    /**
     * Calculates the total amount of payments made by the team
     * @param teamName the team's name
     * @return the total amount of payments made by the team
     */
    public double calculateTotalPayment(String teamName){
       ArrayList<Payment> teamPayments = payments.get(teamName);
       double sum = 0;
       for(Payment payment : teamPayments){
           sum += payment.getAmount();
       }
       return sum;
    }
}
