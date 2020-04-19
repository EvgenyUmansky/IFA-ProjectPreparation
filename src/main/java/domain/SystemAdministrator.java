package domain;

import java.util.HashMap;

public class SystemAdministrator extends Subscriber {
    Alert alert;


/////////// Constructor ///////////

    public SystemAdministrator(String userName, String mail) {
        super(userName, mail);
    }



    public void closeUser(User user) {
        // TODO: 19/04/2020 add relevant subscribers to mailSet
        Alert alert = new Alert();
        //alert.addToMailSet();
        if(user.getRoles().containsKey(Role.SYSTEM_ADMIN) && User.getUsersByRole(Role.SYSTEM_ADMIN).size() == 1){
            alert.sendAlert(new AlertNotification("Cant remove System Administer Account","System needs at least one system administer"));
        }
        else if (!user.isClosed()) {
            user.setClosed(true);
            alert.sendAlert(new AlertNotification("close user permanently", "you user close permanently"));
        }
    }

}
