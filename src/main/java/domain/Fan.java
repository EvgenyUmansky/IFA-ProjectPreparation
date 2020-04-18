package domain;

import java.util.ArrayList;
import java.util.Map;

public class Fan extends Subscriber{

/////////// Constructor ///////////
    public Fan(String userName, String mail) {
        super(userName, mail);
    }


/////////// Functionality ///////////

    // U.C 3.4
    public Map<String, Boolean> sendComplaintToSysAdmin(ArrayList<SystemAdministrator> sysAdmins, AlertNotification alertNotification) {
        Alert alert = new Alert();
        for(SystemAdministrator sysAdmin : sysAdmins){
            alert.addSubscriber(sysAdmin);
        }

        return alert.sendAlert(alertNotification);
    }

    // U.C 3.5
    public String[] getSearchHistory() {
        // TODO: Get from db
        return new String[]{"A", "B", "C", "D"};
    }
}
