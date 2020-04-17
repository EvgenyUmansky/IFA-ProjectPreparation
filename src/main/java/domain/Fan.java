package domain;

import java.util.ArrayList;
import java.util.Map;

public class Fan extends Subscriber{

/////////// Constructor ///////////
    public Fan(String userName, String mail) {
        super(userName, mail);
    }


/////////// Functionality ///////////
    public Map<String, Boolean> sendComplaintToSysAdmin(ArrayList<SystemAdministrator> sysAdmins, AlertNotification alertNotification) {
        Alert alert = new Alert();
        for(SystemAdministrator sysAdmin : sysAdmins){
            alert.addSubscriber(sysAdmin);
        }

        return alert.sendAlert(alertNotification);
    }

    public String getFanDetails(){
        return "User name: " + this.getUserName() + "\n" +
               "Mail: " + this.getMail();
    }

    public void setFanDetails(String newMail){
        this.setMail(newMail);
    }
}
