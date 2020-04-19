package domain;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Alert {
    private Set<Subscriber> mailAlertList;
    private Set<Subscriber> inSystemAlertList;

/////////// Constructor ///////////
    public Alert() {
        this.mailAlertList = new HashSet<>();
        this.inSystemAlertList = new HashSet<>();
    }


/////////// Functionality ///////////

    public void addSubscriber(Subscriber user) {
        if(user.isMail()){
            this.mailAlertList.add(user);
        }
        else {
            this.inSystemAlertList.add(user);
        }
    }

    public void addToMailSet(Subscriber user){
        this.mailAlertList.add(user);
    }

    public void addToSystemSet(Subscriber user){
        this.inSystemAlertList.add(user);
    }

    public void removeSubscriber(Subscriber user){
        if(user.isMail()){
            this.mailAlertList.remove(user);
        }
        else {
            this.inSystemAlertList.remove(user);
        }
    }

    public void removeFromMailSet(Subscriber user){
        this.mailAlertList.remove(user);
    }

    public void removeFromSystemSet(Subscriber user){
        this.inSystemAlertList.remove(user);
    }


    public void clearSubscribers(){
        this.mailAlertList = new HashSet<>();
        this.inSystemAlertList = new HashSet<>();
    }


    public void clearMailList(){
        this.mailAlertList = new HashSet<>();
    }

    public void clearInSystemList(){
        this.inSystemAlertList = new HashSet<>();
    }

    /**
     * public function to send alert to user. Chooses from list where to send the alert: mail or system
     * @param alertNotification
     */
    public Map<String, Boolean> sendAlert(AlertNotification alertNotification)  {
        Map isSentMap = new HashMap<>();
        for(Subscriber user : this.mailAlertList){
            boolean isSent = sendMailAlert(user.getMail(), alertNotification);
            isSentMap.put(user.getUserName(), isSent);

        }

        for(Subscriber user : this.inSystemAlertList){
            sendInSystemAlert(user, alertNotification);
            isSentMap.put(user.getUserName(), true);
        }

        return isSentMap;
    }

    /**
     * send alert to mail
     * @param to
     * @param alertNotification
     * @return
     */
    private boolean sendMailAlert(String to, AlertNotification alertNotification) {

        // Get a Properties object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "465");                 //TLS Port 465
        props.put("mail.smtp.auth", "true");                //enable authentication
        props.put("mail.smtp.starttls.enable", "true");     //enable STARTTLS
        props.put("mail.smtp.ssl.enable", "true");          // enable ssl
        props.put("mail.smtp.auth", "true");

        final String username = "fantasticfiveifa@gmail.com";
        final String password = "naorevgenymohsenguyroy";
        try {
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // -- Create a new message --
            Message msg = new MimeMessage(session);


            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));
            msg.setSubject(alertNotification.getTitle());
            msg.setText(alertNotification.getMessage());
            Transport.send(msg);

            System.out.println("The mail sent successfully");
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Send alert to user in system
     * @param user
     * @param alertNotification
     */
    private void sendInSystemAlert(Subscriber user, AlertNotification alertNotification){
        user.addAlertMessage(alertNotification);
    }


/////////// Getters and Setters ///////////
    public Set<Subscriber> getMailAlertList() {
        return mailAlertList;
    }

    public Set<Subscriber> getInSystemAlertList() {
        return inSystemAlertList;
    }
}
