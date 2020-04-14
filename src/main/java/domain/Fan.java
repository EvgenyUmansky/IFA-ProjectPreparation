package domain;

import java.util.ArrayList;

public class Fan extends Subscriber{

/////////// Constructor ///////////
    public Fan(String userName, String mail) {
        super(userName, mail);
    }

    public void sendComplaintToSysAdmin(ArrayList<SystemAdministrator> sysAdmins, AlertNotification alertNotification) {
        for(SystemAdministrator sysAdmin : sysAdmins){
            sysAdmin.addAlertMessage(alertNotification);
        }
    }
}
