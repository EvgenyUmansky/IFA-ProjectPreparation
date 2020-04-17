package domain;

import java.util.ArrayList;

public class Fan extends Subscriber {

    /////////// Constructor ///////////
    public Fan(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }

    public void sendComplaintToSysAdmin(ArrayList<SystemAdministrator> sysAdmins, AlertNotification alertNotification) {
        for (SystemAdministrator sysAdmin : sysAdmins) {
            sysAdmin.addAlertMessage(alertNotification);
        }
    }

    /////////// Use Case 3 - Fan ///////////
    // UC 3.4 - send complaint (by fan) to System Administrator
/*
    public boolean sendAlertToSysAdmin(String userName, AlertNotification message) {
//        Subscriber fan = (Subscriber) (users.get(userName));
//        if (!(fan instanceof Fan)) {
        User fanUser = users.get(userName);
        if (!fanUser.getRoles().containsKey(Role.FAN)) {
            System.out.println("Not fan instance");
            return false;
        }

        sysAdminsAlert.sendAlert(message);

        return true;
    }
*/

}
