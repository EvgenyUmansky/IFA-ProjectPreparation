package domain;

import java.security.Security;
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
    public void addToMailSet(Subscriber user){
        this.mailAlertList.add(user);
    }

    public void addToSystemSet(Subscriber user){
        this.inSystemAlertList.add(user);
    }

    public void removeFromMailSet(Subscriber user){
        this.mailAlertList.remove(user);
    }

    public void removeFromSystemSet(Subscriber user){
        this.inSystemAlertList.remove(user);
    }

    /**
     * public function to send alert to user. Chooses from list where to send the alert: mail or system
     * @param alertNotification
     */
    public void sendAlert(AlertNotification alertNotification) throws MessagingException {
        for(Subscriber user : this.mailAlertList){
            sendMailAlert(user.getMail(), alertNotification);
        }

        for(Subscriber user : this.inSystemAlertList){
            sendInSystemAlert(user, alertNotification);
        }
    }

    // Taken from https://www.tutorialspoint.com/java/java_sending_email.htm
    /**
     * send alert to mail
     * @param to
     * @param alertNotification
     * @return
     */
    public boolean sendMailAlert(String to, AlertNotification alertNotification) throws MessagingException {
       // Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        final String username = "euguman";
        final String password = "SeVeLa92";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // -- Create a new message --
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to, false));
        msg.setSubject(alertNotification.getTitle());
        msg.setText(alertNotification.getMessage());
        Transport.send(msg);

        System.out.println("Message sent.");
        return true;
    }

    /**
     * Send alert to user in system
     * @param user
     * @param alertNotification
     * @return
     */
    private boolean sendInSystemAlert(Subscriber user, AlertNotification alertNotification){
        user.addAlertMessage(alertNotification);
        return true;
    }


/////////// Getters and Setters ///////////
    public Set<Subscriber> getMailAlertList() {
        return mailAlertList;
    }

    public Set<Subscriber> getInSystemAlertList() {
        return inSystemAlertList;
    }
}
