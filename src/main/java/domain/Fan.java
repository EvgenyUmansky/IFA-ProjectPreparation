package domain;

import java.util.ArrayList;
import java.util.Map;

public class Fan extends Subscriber{

/////////// Constructor ///////////
    public Fan(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }


/////////// Functionality ///////////
    public Map<String, Boolean> sendComplaintToSysAdmin(ArrayList<SystemAdministrator> sysAdmins, AlertNotification alertNotification) {
        Alert alert = new Alert();
        for(SystemAdministrator sysAdmin : sysAdmins){
            alert.addSubscriber(sysAdmin);
        }

        return alert.sendAlert(alertNotification);
    }
}
