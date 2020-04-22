package domain;


import java.util.ArrayList;

public abstract class Subscriber {


    private String userName;
    private String mail;
    private boolean isMail;
    private ArrayList<AlertNotification> alertsMessages;

    public String getUserName() {
        return userName;
    }

/////////// Constructor ///////////

    // With mail
    public Subscriber(String userName, String mail){
        this.userName = userName;
        this.alertsMessages = new ArrayList<>();
        this.mail = mail;
        this.isMail = false;
    }
    // when the function activated - send message to screen
    public AlertNotification addAlertMessage(AlertNotification message){
        this.alertsMessages.add(message);

        return message;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(boolean mail) {
        isMail = mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isMail() {
        return isMail;
    }

    public ArrayList<AlertNotification> getAlertsMessages() {
        return alertsMessages;
    }

}
