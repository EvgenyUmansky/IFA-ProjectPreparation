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

    // 3.5 -
    //TODO See all history. We need UI for this? Evgeny

    // UC 3.6 Envelope function for getProfileDetails - get and set fan info (fields)
    public String getFanProfileDetails(String userName) {
//        Subscriber fan = (Subscriber) (users.get(userName));
//        if (!(fan instanceof Fan)) {
        User fanUser = users.get(userName);
        if (!fanUser.getRoles().containsKey("Fan")) {
            System.out.println("Not fan instance");
            return "";
        }

        return getProfileDetails(fanUser, fanUser.getRoles().get("Fan"));
    }

    // Envelope function for setProfileDetails
    public boolean setFanProfileDetails(String userName, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {
        User fanUser = users.get(userName);
        if (!fanUser.getRoles().containsKey("Fan")) {
            System.out.println("Not fan instance");
            return false;
        }

        return setProfileDetails(fanUser, fanUser.getRoles().get("Fan"), newUserName, newPassword, newName, newMail, isEmptyMail);
    }

}
